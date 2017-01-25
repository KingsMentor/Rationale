package xyz.belvi.permissiondialog.Rationale;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

public interface RationaleBuilder {

    interface PermissionInit {

        PermissionInit addSmoothPermission(SmoothPermission... smoothPermission);

        PermissionInit addSmoothPermission(ArrayList<SmoothPermission> smoothPermission);

        PermissionInit requestCode(int requestCode);

        PermissionBuild includeStyle(int styleRes);

        PermissionInit setPermission(SmoothPermission... smoothPermission);

        PermissionInit setPermission(ArrayList<SmoothPermission> smoothPermission);

    }

    interface PermissionBuild {
        void build(boolean buildAnyway);
    }


}