package com.teltech.accessibility

import android.app.role.RoleManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.view.WindowManager

class CallReceiver : BroadcastReceiver() {

    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var stateStr: String? = ""

    companion object {
        var overlay: View? = null
        var mWindowManager: WindowManager? = null
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
                    context.sendBroadcast(Intent().apply {
                        action = Constants.CALL_ANSWERED
                        putExtra(Constants.IS_CALL_ACTIVE, true)
                    })
                }
            TelephonyManager.CALL_STATE_IDLE -> {
                context.sendBroadcast(Intent().apply {
                    action = Constants.CALL_ANSWERED
                    putExtra(Constants.IS_CALL_ACTIVE, false)
                })
            }
        }
        SharePrefUtil.setInt(Constants.LAST_CALL_STATE, state)
    }
}
