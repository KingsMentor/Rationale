package xyz.belvi.permissiondialog.Permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;

/**
 * Created by zone2 on 1/21/17.
 */

public class PermissionDetails {
    private String description;
    private int drawableResId;
    private int protectionLevel;

    public String getDescription() {
        return this.description;
    }

    public PermissionDetails setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPermissionIcon() {
        return this.drawableResId;
    }

    public PermissionDetails setPermissionIcon(int drawable) {
        this.drawableResId = drawable;
        return this;
    }

    public int getProtectionLevel() {
        return this.protectionLevel;
    }

    public PermissionDetails getPermissionDetails(Context context, String permission, int permissionDrawableResID) {
        PackageManager pm = context.getPackageManager();
        PermissionInfo info = null;
        try {
            info = pm.getPermissionInfo(permission, PackageManager.GET_META_DATA);
            String text = info.loadDescription(context.getPackageManager()).toString();
            this.description = text;
            this.protectionLevel = info.protectionLevel;
            this.drawableResId = permissionDrawableResID;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
