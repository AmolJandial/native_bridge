import 'package:flutter/cupertino.dart';
import 'package:native_bridge/src/credential_manager_pigeon.g.dart';

class NativeBridge {
  final CredentialManagerPigeon _credentialManagerPigeon =
      CredentialManagerPigeon();

  Future<String> getPhoneHint() async {
    try {
      return await _credentialManagerPigeon.fetchSimNumberHint();
    } catch (e) {
      debugPrint('error in getPhoneHint: $e');
      return 'Unexpected issue!';
    }
  }

  Future<String?> getPlatformVersion() async {
    return 'NativeBridgePlatform.instance.getPlatformVersion()';
  }
}
