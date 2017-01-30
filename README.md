[![](https://jitpack.io/v/KingsMentor/Rationale.svg)](https://jitpack.io/#KingsMentor/Rationale) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Rationale-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5189)
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

### Response from Rationale
Rationale returns response via onActivityResult. Here is a snippet on handling response from Rationale.
``` java
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Rationale.isResultFromRationale(requestCode, PERM)) {
            RationaleResponse rationaleResponse = Rationale.getRationaleResponse(data);
            if (rationaleResponse.shouldRequestForPermissions()) {
                // a list of permission to be requested for
                ArrayList<SmoothPermission> smoothPermissions = rationaleResponse.getSmoothPermissions();

                // request for permissions
            } else if (rationaleResponse.userDecline()) {
                Toast.makeText(this, "user does not want to do this now", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "permission is accepted", Toast.LENGTH_LONG).show();
            }
        }

    }
```

### Styling Rationale
Rationale dialog can be placing this in `styles.xml` changing the values to suite your need and including it when initialising Rationale with `includeStyle(styleRes)`
```xml
 <style name="Beliv.RationaleStyle" parent="Theme.AppCompat">
        <!-- Customize your theme here. -->
        <item name="bv_primary">@color/primary_color</item>
        <item name="bv_secondary">@color/secondary_color</item>
        <item name="bv_rationale_state_visibility">true</item>
        <item name="bv_rationale_state_ignore_color">@color/ignore_state</item>
        <item name="bv_rationale_state_deny_color">@color/deny_state</item>
        <item name="bv_rationale_text_color_deny">@color/grey</item>
        <item name="bv_rationale_text_color_ignore">@color/ignore_state</item>
        <item name="bv_card_corner_radius">@dimen/card_corner</item>
        <item name="bv_rationale_text_size">@dimen/rationale_text_size</item>
        <item name="bv_rationale_text_font">@string/rationale_font</item>

    </style>
```

#License
The MIT License (MIT). Please see the [License File](https://github.com/KingsMentor/MobileVisionBarcodeScanner/blob/master/license) for more information.

