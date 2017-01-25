package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

class RationaleDialogBuilder {

    private ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
    private Activity mActivity;
    private PermissionResolveListener permissionResolveListener;


    public RationaleDialogBuilder(Activity activity) {
        mActivity = activity;
        smoothPermissions = new ArrayList<>();

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
        RationaleBase.startTransparentBase(mActivity, smoothPermissions, styleRes, buildAnyway);
    }





}
