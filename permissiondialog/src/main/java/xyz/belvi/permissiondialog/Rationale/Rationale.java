package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

public class Rationale implements RationaleBuilder.PermissionBuild, RationaleBuilder.PermissionInit {


    private RationaleDialogBuilder permissionDialogBuilder;
    private int styleRes;

    private Rationale(Activity activity) {

        permissionDialogBuilder = new RationaleDialogBuilder(activity);
    }

    public static RationaleBuilder.PermissionInit withActivity(Activity activity) {
        return new Rationale(activity);
    }

    @Override
    public RationaleBuilder.PermissionInit addSmoothPermission(SmoothPermission... smoothPermission) {
        permissionDialogBuilder.addSmoothPermission(smoothPermission);
        return this;
    }

    @Override
    public RationaleBuilder.PermissionInit addSmoothPermission(ArrayList<SmoothPermission> smoothPermission) {
        permissionDialogBuilder.addSmoothPermission(smoothPermission);
        return this;
    }


    @Override
    public RationaleBuilder.PermissionBuild includeStyle(int styleRes) {
        this.styleRes = styleRes;
        return this;
    }


    @Override
    public RationaleBuilder.PermissionInit setPermission(SmoothPermission... smoothPermission) {
        permissionDialogBuilder.setSmoothPermission(smoothPermission);
        return this;
    }

    @Override
    public RationaleBuilder.PermissionInit setPermission(ArrayList<SmoothPermission> smoothPermission) {
        permissionDialogBuilder.setSmoothPermission(smoothPermission);
        return this;
    }


    @Override
    public void build(boolean buildAnyway) {
        permissionDialogBuilder.build(styleRes, buildAnyway);
    }

    public static boolean permissionResolved(Intent intent) {
        return intent.getIntExtra(RationaleBase.RESULT_TYPE, RationaleDialog.NO_ACTION) == RationaleDialog.PERMISSION_RESOLVE;
    }

    public static ArrayList<SmoothPermission> getSmoothPermission(Intent intent) {
        if (permissionResolved(intent)) {
            Bundle bundle = intent.getBundleExtra(RationaleBase.RESULT_DATA);
            if (bundle != null) {
                ArrayList<SmoothPermission> smoothPermissions = bundle.getParcelableArrayList(RationaleDialog.SMOOTH_PERMISSIONS);
                return smoothPermissions;
            }
        }
        return null;
    }

}
