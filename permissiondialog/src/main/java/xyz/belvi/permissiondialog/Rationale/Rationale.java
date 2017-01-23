package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

public class Rationale implements RationaleBuilder.PermissionBuild, RationaleBuilder.PermissionInit, RationaleBuilder.DialogStyle {


    private RationaleDialogBuilder permissionDialogBuilder;
    private int styleRes;

    private Rationale(Activity activity) {
        init(activity);
    }


    private void initialize(Activity activity) {
        permissionDialogBuilder = new RationaleDialogBuilder(activity);
    }

    public static RationaleBuilder.PermissionInit withActivity(Activity activity) {
        return new Rationale(activity);
    }


    @Override
    public RationaleBuilder.PermissionInit init(Activity activity) {
        initialize(activity);
        return this;
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
    public RationaleBuilder.DialogStyle includeStyle(int styleRes) {
        this.styleRes = styleRes;
        return this;
    }

    @Override
    public RationaleBuilder.PermissionBuild withPermissionResolved(PermissionResolveListener resolveListener) {
        permissionDialogBuilder.resolvePermission(resolveListener);
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
    public RationaleDialog build(boolean buildAnyway) {
        return permissionDialogBuilder.build(styleRes, buildAnyway);
    }


}
