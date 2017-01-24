package xyz.belvi.permissiondialog.Rationale;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/20/17.
 */

public class Rationale implements RationaleBuilder.PermissionBuild, RationaleBuilder.PermissionInit, RationaleBuilder.DialogStyle {


    private RationaleDialogBuilder permissionDialogBuilder;
    private int styleRes;

    private Rationale(AppCompatActivity activity) {

        permissionDialogBuilder = new RationaleDialogBuilder(activity);
    }
    public static RationaleBuilder.PermissionInit withActivity(AppCompatActivity activity) {
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
    public void build(boolean buildAnyway) {
         permissionDialogBuilder.build(styleRes, buildAnyway);
    }


}
