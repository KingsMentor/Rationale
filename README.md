[![](https://jitpack.io/v/KingsMentor/Rationale.svg)](https://jitpack.io/#KingsMentor/Rationale)
# Rationale
Android permission Rationale Helper.
Similar to WhatsApp Permission Rationale Dialog that manages permission request .

![advisible flow to use on handling permissions](http://share.gifyoutube.com/g5K0Y9.gif "The New Flow")

#### How it Works.

1. Init with a list of Permission called SmoothPermissions
2. Rationale runs a check on all SmoothPermissions to determine which Permission to show a message for
3. if the list is empty, it returns success with empty list
4. if there is any permission to be granted, it shows the appropraite rationale to the user 
5. returns permissions to be requested for based on user interaction

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

- requestCode - request code to track response from onActivityResult
- includeStyle - styling the Rationale Dialog. more details on styling is found below
- SmoothPermissions - List of Permission to run Rationale Dialog on
- build - takes `true` for Rationale Dialog to show Permission that has not been granted. `false` to only show rationale for permission permanently denied.
##### PermissionDetails
Build permission with :
- Permission name
- Permission Icon
- rationaleMessage
- deniedMessage - message to display when Permission is permanently denied by the user
- description - System Permission Description
- protectionLevel - Protection level of permission

