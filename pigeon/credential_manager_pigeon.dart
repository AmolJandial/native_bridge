import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(
  PigeonOptions(
    dartOut: 'lib/src/credential_manager_pigeon.g.dart',
    kotlinOut:
        'android/src/main/kotlin/com/hitagi/native_bridge/CredentialManagerPigeon.g.kt',
    kotlinOptions: KotlinOptions(package: 'com.hitagi.native_bridge'),
    dartPackageName: 'native_bridge',
  ),
)
@HostApi()
abstract class CredentialManagerPigeon {
  @async
  String fetchSimNumberHint();
}
