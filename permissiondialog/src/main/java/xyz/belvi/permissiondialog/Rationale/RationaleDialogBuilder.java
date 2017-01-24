package xyz.belvi.permissiondialog.Rationale;

import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.PermissionDetails;
import xyz.belvi.permissiondialog.Permission.PermissionState;
import xyz.belvi.permissiondialog.Permission.PermissionTracker;
import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

class RationaleDialogBuilder implements CallbackReceiver.Receiver {

    private static ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
    private static AppCompatActivity mActivity;
    private PermissionResolveListener permissionResolveListener;
    private CallbackReceiver callbackReceiver;


    public RationaleDialogBuilder(AppCompatActivity activity) {
        mActivity = activity;
        smoothPermissions = new ArrayList<>();
        callbackReceiver = new CallbackReceiver(new Handler());
        callbackReceiver.setReceiver(this);

    }

    public RationaleDialogBuilder addSmoothPermission(SmoothPermission... smoothPermissions) {
        for (SmoothPermission smoothPermission : smoothPermissions) {
            this.smoothPermissions.add(smoothPermission);
        }
        return this;
    }

    public RationaleDialogBuilder addSmoothPermission(ArrayList<SmoothPermission> smoothPermissions) {
        for (SmoothPermission smoothPermission : smoothPermissions) {
            this.smoothPermissions.add(smoothPermission);
        }
        return this;
    }

    public RationaleDialogBuilder resolvePermission(PermissionResolveListener permissionResolver) {
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


    public void build(int styleRes, boolean buildAnyway) {
//        RationaleBase.startTransperentBase(mActivity);
//        mActivity.startActivity(new Intent(mActivity,RationaleBase.class));
        ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
        boolean showSettings = showSettings(smoothPermissions, buildAnyway);
        if (smoothPermissions.size() > 0) {
            new RationaleDialog().initialise(smoothPermissions, callbackReceiver, styleRes, showSettings, buildAnyway).show(mActivity.getSupportFragmentManager(), "");
        } else {
//            RationaleDialog.returnCallback(permissionResolveListener, smoothPermissions, buildAnyway);
        }
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


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        ArrayList<SmoothPermission> smoothPermissions = resultData.getParcelableArrayList(RationaleDialog.SMOOTH_PERMISSIONS);
        if (resultCode == RationaleDialog.PERMISSION_RESOLVE) {
            permissionResolveListener.onResolved(smoothPermissions);
        } else {
            permissionResolveListener.possiblePermissionUpdate(smoothPermissions);
        }
    }
}
