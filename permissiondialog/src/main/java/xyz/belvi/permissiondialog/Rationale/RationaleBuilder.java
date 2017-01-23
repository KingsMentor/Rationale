package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

public interface RationaleBuilder {

    interface PermissionInit {

        PermissionInit init(Activity activity);

        PermissionInit addSmoothPermission(SmoothPermission... smoothPermission);

        PermissionInit addSmoothPermission(ArrayList<SmoothPermission> smoothPermission);

        DialogStyle includeStyle(int styleRes);

        void setPermission(SmoothPermission... smoothPermission);

        void setPermission(ArrayList<SmoothPermission> smoothPermission);

    }

    interface DialogStyle {
        PermissionBuild withPermissionResolved(PermissionResolveListener resolveListener);
    }

    interface PermissionBuild {
        PermissionDialog build(boolean buildAnyway);
    }


}