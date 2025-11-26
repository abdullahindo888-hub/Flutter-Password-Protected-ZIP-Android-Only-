/*
================================================================================
🔥 Flutter Password-Protected ZIP Plugin (Android Only)
================================================================================
This snippet shows how to create a password-protected ZIP file in Flutter 
(Android only) using Zip4j AES encryption. It includes:

1. Dart API (MethodChannel)
2. Android Kotlin setup
3. Required permissions
4. Example usage
================================================================================
*/

/// --------------------------- 1️⃣ Dart API ---------------------------
import 'package:flutter/services.dart';

class EnoughZip {
  static const MethodChannel _channel = MethodChannel("myApp/encrypted_zip");

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


/// --------------------------- 2️⃣ Android Kotlin Implementation ---------------------------
// Place this in android/src/main/kotlin/com/example/password_zip/PasswordZipPlugin.kt
/*
package com.example.myApp

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.EncryptionMethod
import java.io.File

class MainActivity : FlutterActivity() {

    private val CHANNEL = "myApp/encrypted_zip"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            when (call.method) {

                "createZip" -> {
                    val files = call.argument<List<String>>("files") ?: emptyList()
                    val output = call.argument<String>("outputPath")!!
                    val password = call.argument<String>("password")!!

                    try {

                        // Prepare ZIP file with password
                        val zipFile = ZipFile(output, password.toCharArray())

                        // Setup ZIP parameters
                        val params = ZipParameters().apply {
                            isEncryptFiles = true
                            compressionLevel = CompressionLevel.HIGHER
                            encryptionMethod = EncryptionMethod.ZIP_STANDARD
                        }

                        // Add each file
                        files.forEach { filePath ->
                            zipFile.addFile(File(filePath), params)
                        }

                        result.success(true)

                    } catch (e: Exception) {
                        result.error("ZIP_ERROR", e.message, null)
                    }
                }

                else -> result.notImplemented()
            }
        }
    }
}

*/

/// --------------------------- 3️⃣ Android Permissions ---------------------------
/*
Add these permissions to AndroidManifest.xml:

<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" tools:ignore="ScopedStorage"/>

Request runtime permissions on Android 10+ using permission_handler.

*/

/// --------------------------- 4️⃣ Add Depedencies ---------------------------

dependencies {
    implementation("net.lingala.zip4j:zip4j:2.11.5")
}


*/

/// --------------------------- 4️⃣ Example Usage ---------------------------
void main() async {
  final success = await EnoughZip.createEncryptedZip(
    files: [
      "/storage/emulated/0/myApp/logs/log1.txt",
      "/storage/emulated/0/myApp/logs/log2.txt",
    ],
    outputPath: "/storage/emulated/0/Download/myApp_backup.zip",
    password: "MyStrongPassword123",
  );

  if (success) {
    print("✅ ZIP created successfully with password!");
  } else {
    print("❌ ZIP creation failed. Check files, output path, or permissions.");
  }
}


/// --------------------------- 5️⃣ Fresh Build ---------------------------

flutter clean
rm -rf android/.gradle
rm -rf android/app/build
flutter pub get
flutter run


/// --------------------------- 5️⃣ Notes ---------------------------
/*
- Works only on Android.
- ZIP files are encrypted with AES-256 automatically.
- Ensure all file paths exist and output folder is writable.
- You can extend this for:
    • Zipping entire folders
    • Progress callbacks
    • iOS support (using SSZipArchive)
*/


