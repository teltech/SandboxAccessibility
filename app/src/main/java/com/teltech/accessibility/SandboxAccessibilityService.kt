package com.teltech.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.*
import android.media.AudioManager
import android.media.MediaRecorder
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import java.io.FileDescriptor
import java.io.IOException

const val LOG_TAG_S = "MyService:"

class SandboxAccessibilityService : AccessibilityService() {

    var windowManager: WindowManager? = null

    var recorder: MediaRecorder? = null
    var isRecording = false
    private lateinit var catchingCallReceiver: BroadcastReceiver

    @SuppressLint("RtlHardcoded")
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager?

        catchingCallReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.getBooleanExtra(Constants.IS_CALL_ACTIVE, false) == true) {
                    isRecording = true
                    startRecording()
                } else {
                    stopRecording()
                    isRecording = false
                }
            }

        }
        registerReceiver(catchingCallReceiver, IntentFilter(Constants.CALL_ANSWERED))
        Log.i("start Myservice", "MyService");
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.e(LOG_TAG_S, "Event :" + event?.eventType)
    }

    override fun onInterrupt() = Unit

    @SuppressLint("ClickableViewAccessibility")
    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            notificationTimeout = 100
        }

        serviceInfo = info
    }

    /**
     * Setting up MediaRecorder and starting to record
     */
    private fun startRecording() {
        try {
            val manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?

            var inCall = false

            manager?.let {
                inCall = it.mode == AudioManager.MODE_IN_CALL || it.mode == AudioManager.MODE_IN_COMMUNICATION
            }
            Log.w(LOG_TAG_S, "In call is $inCall")

            recorder = buildMediaRecorder()

            try {
                recorder?.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(LOG_TAG_S, "prepare() failed")
            }
            recorder?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Stopping the recording and resetting the recorder when call is finished
     */
    private fun stopRecording() {
        Log.e(LOG_TAG_S, "stop recording")
        if (isRecording) {
            recorder?.stop()
            recorder?.release()
            recorder = null
        }
    }

    /**
     * Media recorder builder
     */
    private fun buildMediaRecorder(): MediaRecorder =
        MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
            setOutputFile(getFileDescriptor())
        }

    /**
     * File descriptor that positions saving location to Music of Internal Storage
     */
    private fun getFileDescriptor(): FileDescriptor? {
        val values = ContentValues(4).apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, "recording.mp3")
            put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000).toInt())
            put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3")
        }

        val audioURI = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)

        var file: ParcelFileDescriptor? = null
        if (audioURI != null) {
            file = contentResolver.openFileDescriptor(audioURI, "w")
        }
        return file?.fileDescriptor
    }

    override fun onDestroy() {
        unregisterReceiver(catchingCallReceiver)
        super.onDestroy()
    }

}

