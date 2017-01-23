package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.PermissionDetails;
import xyz.belvi.permissiondialog.Permission.PermissionState;
import xyz.belvi.permissiondialog.Permission.PermissionTracker;
import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

class PermissionDialogBuilder {

    private static ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
    private static Activity mActivity;
    private PermissionResolveListener permissionResolveListener;

    public PermissionDialogBuilder(Activity activity) {
        mActivity = activity;
        smoothPermissions = new ArrayList<>();
    }

    public PermissionDialogBuilder addSmoothPermission(SmoothPermission... smoothPermissions) {
        for (SmoothPermission smoothPermission : smoothPermissions) {
            this.smoothPermissions.add(smoothPermission);
        }
        return this;
    }

    public PermissionDialogBuilder addSmoothPermission(ArrayList<SmoothPermission> smoothPermissions) {
        for (SmoothPermission smoothPermission : smoothPermissions) {
            this.smoothPermissions.add(smoothPermission);
        }
        return this;
    }

    public PermissionDialogBuilder resolvePermission(PermissionResolveListener permissionResolver) {
        permissionResolveListener = permissionResolver;
        return this;
    }

    public void setSmoothPermission(SmoothPermission... smoothPermissions) {
        this.smoothPermissions = new ArrayList<>();
        for (SmoothPermission smoothPermission : smoothPermissions) {
            this.smoothPermissions.add(smoothPermission);
        }
    }

    public void setSmoothPermission(ArrayList<SmoothPermission> smoothPermissions) {
        this.smoothPermissions = smoothPermissions;
    }


    public PermissionDialog build(int styleRes, boolean buildAnyway) {
        ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
        boolean showSettings = showSettings(smoothPermissions, buildAnyway);
        if (smoothPermissions.size() > 0) {
            return new PermissionDialog().initialise(smoothPermissions, permissionResolveListener, styleRes, showSettings, buildAnyway);
        } else {
            PermissionDialog.returnCallback(permissionResolveListener, smoothPermissions, buildAnyway);
        }
        return null;
    }


    public static boolean showSettings(ArrayList<SmoothPermission> sp, boolean buildAnyway) {
        boolean showSettings = false;

        for (SmoothPermission smoothPermission : smoothPermissions) {
            if (isDangerous(smoothPermission.getPermission())) {
                if (isPermissionGranted(smoothPermission.getPermission())) {
                    smoothPermission.setState(PermissionState.GRANTED);
                    continue;
                } else if (isPermissionPermanentlyDenied(smoothPermission.getPermission())) {
                    smoothPermission.setState(PermissionState.PERMANENTLY_DENIED);
                    showSettings = true;
                } else if (isPermissionDenied(smoothPermission.getPermission())) {
                    smoothPermission.setState(PermissionState.DENIED);
                    if (!buildAnyway)
                        continue;
                }
                sp.add(smoothPermission);
            }
        }
        return showSettings;
    }


    private static boolean isDangerous(String permission) {
        PermissionDetails permissionDetails = new PermissionDetails().getPermissionDetails(mActivity, permission, -1);
        boolean isDangerous = permissionDetails.getProtectionLevel() != PermissionInfo.PROTECTION_NORMAL;
        return isDangerous;

    }

    private static boolean isPermissionGranted(String permission) {
        return (ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED);
    }

    private static boolean isPermissionPermanentlyDenied(String permission) {
        if (!PermissionTracker.hasRequired(mActivity, permission)) {
            return false;
        }
        return !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
    }

    private static boolean isPermissionDenied(String permission) {
        return (ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_DENIED);
    }
}
