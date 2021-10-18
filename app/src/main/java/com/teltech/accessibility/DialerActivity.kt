package com.teltech.accessibility

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DialerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)

        findViewById<MaterialButton>(R.id.leadToPermissionsActivity).setOnClickListener {
            startActivity(Intent(this, PermissionsActivity::class.java))
        }

        if (!checkIfDialerEnabled(this)) {
            checkDefaultPhoneApp()
        } else {
            startActivity(Intent(this, PermissionsActivity::class.java))
        }
    }

    private fun checkIfDialerEnabled(context: Context): Boolean =
        (context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager).defaultDialerPackage == context.packageName

    private fun checkDefaultPhoneApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            if (roleManager.isRoleAvailable(RoleManager.ROLE_DIALER)) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                startActivityForResult(intent, 1000)
            }
        } else {
            val intent =
                Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            startActivityForResult(intent, 1000)
        }
    }
}
