package com.example.mfuel

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.EncryptionMethod
import java.io.File

class MainActivity : FlutterActivity() {

    private val CHANNEL = "mfuel/encrypted_zip"

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
