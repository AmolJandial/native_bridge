package com.hitagi.native_bridge

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import io.flutter.plugin.common.PluginRegistry


class CredentialManagerImpl : CredentialManagerPigeon, PluginRegistry.ActivityResultListener,
    ActivityAwareComponent {
    private var activity: Activity? = null
    private var pendingCallback: ((Result<String>) -> Unit)? = null


    companion object {
        private const val REQUEST_CODE_PHONE_HINT = 5902
    }

    override fun onActivityAvailable(activity: Activity) {
        this.activity = activity
    }

    override fun onActivityUnavailable() {
        activity = null
        pendingCallback?.invoke(Result.failure(IllegalStateException("Activity not ready")))
        pendingCallback = null
    }


    override fun fetchSimNumberHint(callback: (Result<String>) -> Unit) {
        val act = activity
            ?: return callback(Result.failure(IllegalStateException("Activity not available")))

        val request = GetPhoneNumberHintIntentRequest.builder().build()

        Identity.getSignInClient(act).getPhoneNumberHintIntent(request)
            .addOnSuccessListener { pendingIntent: PendingIntent ->
                pendingCallback = callback
                try {
                    act.startIntentSenderForResult(
                        pendingIntent.intentSender,
                        REQUEST_CODE_PHONE_HINT,
                        null,
                        0,
                        0,
                        0
                    )
                } catch (e: Exception) {
                    pendingCallback = null
                    callback(Result.failure(e))
                }
            }
            .addOnFailureListener { e -> callback(Result.failure(e)) }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ): Boolean {
        if (requestCode != REQUEST_CODE_PHONE_HINT) return false

        val callback = pendingCallback
        pendingCallback = null

        val act = activity

        if (act == null) {
            callback?.invoke((Result.failure(Exception("Activity unavailable"))))
            return true
        }

        try {
            val phoneNumber = Identity.getSignInClient(act).getPhoneNumberFromIntent(data)
            callback?.invoke(Result.success(phoneNumber))
            return true
        } catch (e: Exception) {
            callback?.invoke(Result.failure(e))
            return true
        }

    }
}