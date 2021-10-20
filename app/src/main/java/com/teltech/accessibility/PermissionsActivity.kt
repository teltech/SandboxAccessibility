package com.teltech.accessibility

import android.Manifest
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

const val PERMISSIONS_REQUEST_CONTACTS = 77
const val REQUEST_CALL_BLOCKING_PERMISSION = 78
const val REQUEST_CALL_SCREENING_ROLE = 80
const val REQUEST_RECORDING = 81

class PermissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        val btnActivateCallService = findViewById<MaterialButton>(R.id.btnActivateCallService)
        val tvNotNow = findViewById<TextView>(R.id.tvNotNow)

        btnActivateCallService.setOnClickListener {
            activateCallService()
        }

        tvNotNow.setOnClickListener {
        }
    }

    private fun activateCallService() {
        checkAllPermissionsAndActivateCallService()
    }

    private fun checkAllPermissionsAndActivateCallService() {
        if (PermissionsUtility.checkIfContactsGranted(this)) {
            if (PermissionsUtility.checkIfCallServiceStateGranted(this)) {
                if (PermissionsUtility.checkIfRecordAudioGranted(this)) {
                    if (Settings.canDrawOverlays(this)) {
                        if (roleManagerGranted()) {
                            SharePrefUtil.setBoolean(Constants.CALL_SERVICE_STATUS, true)
                        } else {
                            requestCallScreeningServiceRole()
                        }
                    } else {
                        requestOverlayPermission()
                    }
                } else {
                    requestRecordAudioPermission()
                }
            } else {
                requestCallPermission()
            }
        } else {
            requestContactsPermission()
        }
    }

    private fun requestRecordAudioPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_RECORDING
        )
    }

    private fun roleManagerGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
        } else {
            true
        }
    }

    private fun requestCallScreeningServiceRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
                startActivityForResult(intent, REQUEST_CALL_SCREENING_ROLE)
            }
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    private fun requestContactsPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
            PERMISSIONS_REQUEST_CONTACTS
        )
    }

    private fun requestCallPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.ANSWER_PHONE_CALLS,
                Manifest.permission.MODIFY_PHONE_STATE
            ), REQUEST_CALL_BLOCKING_PERMISSION
        )
    }

}
