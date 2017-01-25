package xyz.belvi.permissiondialog.Rationale;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

/**
 * Created by zone2 on 1/25/17.
 */

public class RationaleResponse implements Parcelable {
    private ArrayList<SmoothPermission> smoothPermissions;
    private boolean shouldRequestForPermissions, userDecline;

    public RationaleResponse(ArrayList<SmoothPermission> smoothPermissions, boolean shouldRequestForPermissions, boolean userDecline) {
        this.smoothPermissions = smoothPermissions;
        this.shouldRequestForPermissions = shouldRequestForPermissions;
        this.userDecline = userDecline;
    }

    protected RationaleResponse(Parcel in) {
        smoothPermissions = in.createTypedArrayList(SmoothPermission.CREATOR);
        shouldRequestForPermissions = in.readByte() != 0;
        userDecline = in.readByte() != 0;
    }

    public static final Creator<RationaleResponse> CREATOR = new Creator<RationaleResponse>() {
        @Override
        public RationaleResponse createFromParcel(Parcel in) {
            return new RationaleResponse(in);
        }

        @Override
        public RationaleResponse[] newArray(int size) {
            return new RationaleResponse[size];
        }
    };

    public ArrayList<SmoothPermission> getSmoothPermissions() {
        return this.smoothPermissions;
    }


    public boolean shouldRequestForPermissions() {
        return this.shouldRequestForPermissions;
    }


    public boolean userDecline() {
        return this.userDecline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(smoothPermissions);
        parcel.writeByte((byte) (shouldRequestForPermissions ? 1 : 0));
        parcel.writeByte((byte) (userDecline ? 1 : 0));
    }
}
