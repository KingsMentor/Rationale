package xyz.belvi.permissiondialog.Permission;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by zone2 on 1/22/17.
 */

public class PermissionTracker {

    public static void markPermission(Context context, String... permissions) {
        for (String permission : permissions)
            markPermission(context, permission);
    }

    public static void markPermission(Context context, ArrayList<String> permissions) {
        for (String permission : permissions)
            markPermission(context, permission);
    }

    public static void markPermission(Context context, String permission) {
        context.getSharedPreferences(context.getPackageName() + "." + PermissionTracker.class.getSimpleName(), Context.MODE_PRIVATE)
                .edit().putString(context.getPackageName() + "." + PermissionTracker.class.getSimpleName() + ".permission." + permission, permission)
                .commit();
    }


    public static boolean hasRequired(Context context, String permission) {
        return context.getSharedPreferences(context.getPackageName() + "." + PermissionTracker.class.getSimpleName(), Context.MODE_PRIVATE)
                .getString(context.getPackageName() + "." + PermissionTracker.class.getSimpleName() + ".permission." + permission, "")
                .equals(permission);
    }
}
