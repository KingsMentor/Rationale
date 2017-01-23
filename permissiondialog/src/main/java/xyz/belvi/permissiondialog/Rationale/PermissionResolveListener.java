package xyz.belvi.permissiondialog.Rationale;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/21/17.
 */

public interface PermissionResolveListener {
    void onResolved(ArrayList<SmoothPermission> smoothPermissions);

    void possiblePermissionUpdate(ArrayList<SmoothPermission> smoothPermissions);
}
