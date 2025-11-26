import 'package:flutter/services.dart';

class EnoughZip {
  static const MethodChannel _channel = MethodChannel("mfuel/encrypted_zip");

  static Future<bool> createEncryptedZip({
    required List<String> files,
    required String outputPath,
    required String password,
  }) async {
    try {
      final result = await _channel.invokeMethod("createZip", {
        "files": files,
        "outputPath": outputPath,
        "password": password,
      });
      return result == true;
    } catch (e) {
      print("ZIP ERROR: $e");
      return false;
    }
  }
}
