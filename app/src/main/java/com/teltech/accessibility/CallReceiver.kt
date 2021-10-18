package com.teltech.accessibility

import android.annotation.SuppressLint
import android.app.Service
import android.app.role.RoleManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import java.io.IOException
import kotlin.math.roundToInt

class CallReceiver : BroadcastReceiver() {

    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var stateStr: String? = ""

    companion object {
        var overlay: View? = null
        var mWindowManager: WindowManager? = null
        var recorder: AudioRecord? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {

            SharePrefUtil.initialize(it)

            //check Call permissions
            if (!PermissionsUtility.checkIfCallServiceStateGranted(it)) {
                return
            }

            //check Manager role
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val roleManager = it.getSystemService(Context.ROLE_SERVICE) as RoleManager
                if (!roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                    return
                }
            }

            //check Overlay permission
            if (!Settings.canDrawOverlays(it)) {
                return
            }

            try {
                stateStr = intent?.extras?.getString(TelephonyManager.EXTRA_STATE)
            } catch (securityEx: SecurityException) {
                return
            } catch (except: Exception) {

            }

            var state = TelephonyManager.CALL_STATE_IDLE
            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> state = TelephonyManager.CALL_STATE_IDLE
                TelephonyManager.EXTRA_STATE_OFFHOOK -> state =
                    TelephonyManager.CALL_STATE_OFFHOOK
                TelephonyManager.EXTRA_STATE_RINGING -> state =
                    TelephonyManager.CALL_STATE_RINGING
            }

            onCallStateChanged(it, state)
        }
    }

    private fun onCallStateChanged(context: Context, state: Int) {
        lastState = SharePrefUtil.getInt(Constants.LAST_CALL_STATE)
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                //Toast.makeText(context, "Incoming Call Ringing", Toast.LENGTH_SHORT).show()
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    //Toast.makeText(context, "Outgoing Call Started", Toast.LENGTH_SHORT).show()

                } else if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Toast.makeText(context, "Incoming call answered", Toast.LENGTH_SHORT).show()
                    displayOverlay(context)

                }
            TelephonyManager.CALL_STATE_IDLE -> {
                removeOverlay()
            }
        }
        SharePrefUtil.setInt(Constants.LAST_CALL_STATE, state)
    }

    private fun getWindowManagerLayoutParams(): WindowManager.LayoutParams {
        val positionX = SharePrefUtil.getInt(Constants.CALL_SERVICE_OVERLAY_X_POSITION)
        val positionY = SharePrefUtil.getInt(Constants.CALL_SERVICE_OVERLAY_Y_POSITION)
        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, positionX, positionY,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.LEFT
        return params
    }

    private fun setOverlayPosition(view: View, params: WindowManager.LayoutParams) {
        view.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.0f
            private var initialTouchY: Float = 0.0f

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                event?.let {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            initialX = params.x
                            initialY = params.y

                            initialTouchX = it.rawX
                            initialTouchY = it.rawY
                            return true
                        }
                        MotionEvent.ACTION_UP -> {
                            return true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val xDiff = (it.rawX - initialTouchX).roundToInt()
                            val yDiff = (it.rawY - initialTouchY).roundToInt()

                            params.x = initialX + xDiff
                            params.y = initialY + yDiff
                            SharePrefUtil.setInt(
                                Constants.CALL_SERVICE_OVERLAY_X_POSITION,
                                params.x
                            )
                            SharePrefUtil.setInt(
                                Constants.CALL_SERVICE_OVERLAY_Y_POSITION,
                                params.y
                            )
                            mWindowManager?.updateViewLayout(view, params)
                            return true
                        }
                        else -> return false
                    }
                }
                return false
            }
        })
    }

    private fun removeOverlay() {
        overlay?.let {
            if (it.isAttachedToWindow)
                mWindowManager?.removeView(it)
        }
    }

    private fun displayOverlay(context: Context) {
        Handler(Looper.getMainLooper()).postDelayed({
            overlay?.let {
                if (it.isAttachedToWindow)
                    mWindowManager?.removeView(it)
            }

            mWindowManager = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager
            overlay = LayoutInflater.from(context).inflate(R.layout.overlay_call_assistant, null)
            overlay?.let { layout ->
                val action = layout.findViewById<ImageView>(R.id.imWidgetRecordingAction)
                val close = layout.findViewById<ImageView>(R.id.imWidgetClose)
                val llRoot = layout.findViewById<LinearLayout>(R.id.llRoot)

                llRoot?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right))

                action.setOnClickListener {
                    try {
                        startRecording(context)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

                val params = getWindowManagerLayoutParams()
                mWindowManager?.addView(layout, params)
                close?.setOnClickListener {
                    removeOverlay()
                }

                setOverlayPosition(layout, params)
            }
        }, 500)
    }

    @SuppressLint("MissingPermission")
    private fun startRecording(context: Context) {
        recorder = AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK)
            .setAudioFormat(
                AudioFormat.Builder().setChannelMask(AudioFormat.CHANNEL_IN_MONO).build()
            )
            .setBufferSizeInBytes(100000)
            .build()

        // This must be needed source

        try {
            recorder!!.startRecording()
        } catch (exception: IOException) {
        }
    }
}
