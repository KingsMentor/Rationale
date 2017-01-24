package xyz.belvi.permissiondialog.Rationale;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

public interface RationaleBuilder {

    interface PermissionInit {

        PermissionInit addSmoothPermission(SmoothPermission... smoothPermission);

        PermissionInit addSmoothPermission(ArrayList<SmoothPermission> smoothPermission);

        DialogStyle includeStyle(int styleRes);

        PermissionInit setPermission(SmoothPermission... smoothPermission);

        PermissionInit setPermission(ArrayList<SmoothPermission> smoothPermission);

    }

    interface DialogStyle {
        PermissionBuild withPermissionResolved(PermissionResolveListener resolveListener);
    }

    interface PermissionBuild {
        void build(boolean buildAnyway);
    }


}