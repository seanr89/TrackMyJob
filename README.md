keytool -exportcert -list -v \
-alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore

The keytool utility prompts you to enter a password for the keystore. 
The default password for the debug keystore is android. The keytool then prints the fingerprint to the terminal. For example:

process above executed:
Alias name: androiddebugkey
Creation date: 09-Sep-2018
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: C=US, O=Android, CN=Android Debug
Issuer: C=US, O=Android, CN=Android Debug
Serial number: 1
Valid from: Sun Sep 09 16:01:46 BST 2018 until: Tue Sep 01 16:01:46 BST 2048
Certificate fingerprints:
         MD5:  74:9E:BB:D6:74:0C:FC:81:FD:CA:6E:3B:EB:78:D5:E7
         SHA1: 75:4D:5F:EB:9F:6C:FC:71:DE:45:F2:FC:CB:30:02:63:4D:2A:7F:E6
         SHA256: F9:7E:A4:FA:86:F5:27:BB:5F:F3:BC:F4:50:F5:47:22:1E:15:6F:29:2E:30:B9:D5:2E:71:B8:2E:4C:8D:BD:F9
Signature algorithm name: SHA1withRSA
Subject Public Key Algorithm: 1024-bit RSA key
Version: 1
