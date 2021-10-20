package com.teltech.accessibility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionsUtility {

    fun checkIfContactsGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfWriteContactsGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfCallPhoneGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfRecordAudioGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
                && checkIfWriteExternalStorageGranted(context)
                && checkIfReadExternalStorageGranted(context)
    }

    fun checkIfWriteExternalStorageGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfReadExternalStorageGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfWriteAndReadExternalStorageGranted(context: Context): Boolean {
        return checkIfReadExternalStorageGranted(context) && checkIfWriteExternalStorageGranted(
            context
        )
    }

    fun checkIfAllAudioRecordingPermissionsGranted(context: Context): Boolean {
        return checkIfWriteAndReadExternalStorageGranted(context) && checkIfRecordAudioGranted(
            context
        )
    }

    fun checkIfReadPhoneStateGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfReadCallLogGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfCallServiceStateGranted(context: Context): Boolean {
        return checkIfCallPhoneGranted(context) && checkIfReadPhoneStateGranted(context) /*&& checkIfReadCallLogGranted(context)*/
    }
}
