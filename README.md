# Rationale
Android permission Rationale Helper.
Similar to WhatsApp Permission Rationale Dialog that manages permission request .

![advisible flow to use on handling permissions](http://share.gifyoutube.com/g5K0Y9.gif "The New Flow")
# Adding Rationale to your Project

**Step 1** Add it in your root build.gradle at the end of repositories:

```gradle

allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

```

**Step 2** Add the dependency

```gradle
dependencies {
            compile 'com.github.KingsMentor:Rationale:v1.0'
    }
```
# Using Rationale

**Using in an Activity**

```java
private final int PERM = 2017;

final PermissionDetails smsPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.READ_SMS, R.drawable.ic_sms_white_24dp);
        Rationale.withActivity(this)
                .requestCode(PERM)
                .addSmoothPermission(new SmoothPermission(smsPermissionDetails))
                .includeStyle(R.style.Beliv_RationaleStyle).build(true);
```
**Using in a Fragment***
```java
        Rationale.withFragment(this)
                .requestCode(PERM)
                .addSmoothPermission(new SmoothPermission(smsPermissionDetails))
                .includeStyle(R.style.Beliv_RationaleStyle).build(true);
```
