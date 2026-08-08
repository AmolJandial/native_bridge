package com.hitagi.native_bridge

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** NativeBridgePlugin */
class NativeBridgePlugin :
    FlutterPlugin, ActivityAware {

    private lateinit var credentialManagerImpl: CredentialManagerImpl
    private var activityBinding: ActivityPluginBinding? = null


    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        credentialManagerImpl = CredentialManagerImpl()
        CredentialManagerPigeon.setUp(flutterPluginBinding.binaryMessenger, credentialManagerImpl)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        CredentialManagerPigeon.setUp(binding.binaryMessenger, null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        attachActivity(binding)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        attachActivity(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        detachActivity()
    }

    override fun onDetachedFromActivity() {
        detachActivity()
    }

    private fun attachActivity(binding: ActivityPluginBinding) {
        activityBinding = binding
        binding.addActivityResultListener(credentialManagerImpl)
        credentialManagerImpl.onActivityAvailable(binding.activity)
    }

    private fun detachActivity() {
        activityBinding?.removeActivityResultListener(credentialManagerImpl)
        credentialManagerImpl.onActivityUnavailable()
        activityBinding = null
    }


}
