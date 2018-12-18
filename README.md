Job Work and Holiday Tracker
- Application to handle personal monitoring of hours worked at work due to lack of visibility to company system

Phase 1 95% Complete
Feature Set
- Login/Authentication
- Clock Event Saving (In + Out)
- Location Monitoring and clock event automation
- Notification Handling
- Basic Firebase Analytics Logs
- Helper Unit Testing
- Stats Generation

Phase 2
- Serious Code Refactoring
- UI Design
    1. Nav Drawer
    2. Colour Scheme
    3. Custom Buttons
- Unit Test Coverage
- Limited 3rd Party Testing
- Org/Office Data Storage
- Holidays
- Stats Archival

Phase 3 
- Unknown

New Branch Added : phasetwo

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


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
THIS WORKED FOR DEV


The behavior for java.util.Date objects stored in Firestore is going to change AND YOUR APP MAY BREAK.
    To hide this warning and ensure your app does not break, you need to add the following code to your app before calling any other Cloud Firestore methods:
    
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build();
    firestore.setFirestoreSettings(settings);
    
    With this change, timestamps stored in Cloud Firestore will be read back as com.google.firebase.Timestamp objects instead of as system java.util.Date objects. So you will also need to update code expecting a java.util.Date to instead expect a Timestamp. For example:
    
    // Old:
    java.util.Date date = snapshot.getDate("created_at");
    // New:
    Timestamp timestamp = snapshot.getTimestamp("created_at");
    java.util.Date date = timestamp.toDate();
    
    Please audit all existing usages of java.util.Date when you enable the new behavior. In a future release, the behavior will be changed to the new behavior, so if you do not follow these steps, YOUR APP MAY BREAK.


xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx