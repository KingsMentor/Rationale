package xyz.belvi.permissiondialog.Permission;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zone2 on 1/20/17.
 */

public class SmoothPermission implements Parcelable {
    private String permission, rationaleMessage = "", deniedMessage = "";
    private int drawableRes;
    private PermissionState state = PermissionState.DENIED;

    public SmoothPermission(String permission, String rationaleMessage, String deniedMessage, int drawableResId) {
        this.permission = permission;
        this.rationaleMessage = rationaleMessage;
        this.deniedMessage = deniedMessage;
        this.drawableRes = drawableResId;
    }

    public SmoothPermission(PermissionDetails permissionDetails) {
        this.permission = permissionDetails.getPermission();
        this.rationaleMessage = permissionDetails.getDescription();
        this.deniedMessage = permissionDetails.getDescription();
        this.drawableRes = permissionDetails.getPermissionIcon();
    }

    protected SmoothPermission(Parcel in) {
        permission = in.readString();
        rationaleMessage = in.readString();
        deniedMessage = in.readString();
        drawableRes = in.readInt();
        state = PermissionState.valueOf(in.readString());
    }


    public PermissionState getState() {
        return this.state;
    }

    public SmoothPermission setState(PermissionState state) {
        this.state = state;
        return this;
    }


    public String getPermission() {
        return this.permission;
    }

    public SmoothPermission setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public String getRationaleMessage() {
        return this.rationaleMessage;
    }

    public SmoothPermission setRationaleMessage(String rationaleMessage) {
        this.rationaleMessage = rationaleMessage;
        return this;
    }

    public String getDeniedMessage() {
        return this.deniedMessage;
    }

    public SmoothPermission setDeniedMessage(String deniedMessage) {
        this.deniedMessage = deniedMessage;
        return this;
    }

    public int getDrawable() {
        return this.drawableRes;
    }

    public SmoothPermission setDrawable(int iconRes) {
        this.drawableRes = iconRes;
        return this;
    }

    public static final Creator<SmoothPermission> CREATOR = new Creator<SmoothPermission>() {
        @Override
        public SmoothPermission createFromParcel(Parcel in) {
            return new SmoothPermission(in);
        }

        @Override
        public SmoothPermission[] newArray(int size) {
            return new SmoothPermission[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(permission);
        parcel.writeString(rationaleMessage);
        parcel.writeString(deniedMessage);
        parcel.writeInt(drawableRes);
        parcel.writeString(state.name());
    }
}
