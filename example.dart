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