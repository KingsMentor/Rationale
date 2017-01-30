package xyz.belvi.permissiondialog.Rationale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.SmoothPermission;

import static xyz.belvi.permissiondialog.Rationale.RationaleDialog.SMOOTH_PERMISSIONS;

/**
 * Created by zone2 on 1/20/17.
 */

public class Rationale implements RationaleBuilder.PermissionBuild, RationaleBuilder.PermissionInit {


    private RationaleDialogBuilder permissionDialogBuilder;

    private Rationale(Activity activity) {

        permissionDialogBuilder = new RationaleDialogBuilder(activity);
    }

    private Rationale(Fragment fragment) {

        permissionDialogBuilder = new RationaleDialogBuilder(fragment);
    }

    /**
     * launching with a an activity
     * @param activity - activity to return response via onActivityResult
     */
    public static RationaleBuilder.PermissionInit withActivity(Activity activity) {
        return new Rationale(activity);
    }

    /**
     * launching with a fragment
     * @param fragment - fragment to return response via onActivityResult
     */
    public static RationaleBuilder.PermissionInit withFragment(Fragment fragment) {
        return new Rationale(fragment);
    }

    /**
     * add of permissions to run Rationale on
     * @param smoothPermission - list of permission
     * @return PermissionInit
     */
    @Override
    public RationaleBuilder.PermissionInit addSmoothPermission(SmoothPermission... smoothPermission) {
        permissionDialogBuilder.addSmoothPermission(smoothPermission);
        return this;
    }

    /**
     * add of permissions to run Rationale on
     * @param smoothPermission - arraylist of permission
     * @return PermissionInit
     */
    @Override
    public RationaleBuilder.PermissionInit addSmoothPermission(ArrayList<SmoothPermission> smoothPermission) {
        permissionDialogBuilder.addSmoothPermission(smoothPermission);
        return this;
    }

    /**
     * set request code to mark response from Rationale
     * @param requestCode - request code to to start Rationale and return via onActivityResult
     * @return PermissionInit
     */

    @Override
    public RationaleBuilder.PermissionInit requestCode(int requestCode) {
        permissionDialogBuilder.requestCode(requestCode);
        return this;
    }

    /**
     * style the Rationale Dialog with @styleRes
     * @param styleRes - styleRes id
     * @return PermissionBuild
     */

    @Override
    public RationaleBuilder.PermissionBuild includeStyle(int styleRes) {
        permissionDialogBuilder.includeStyle(styleRes);
        return this;
    }

    /**
     * set list of permissions to run Rationale on
     * @param smoothPermission - list of permissions
     * @return PermissionInit
     */
    @Override
    public RationaleBuilder.PermissionInit setPermission(SmoothPermission... smoothPermission) {
        permissionDialogBuilder.setSmoothPermission(smoothPermission);
        return this;
    }

    /**
     * set list of permissions to run Rationale on
     * @param smoothPermission - arraylist of permissions
     * @return PermissionInit
     */
    @Override
    public RationaleBuilder.PermissionInit setPermission(ArrayList<SmoothPermission> smoothPermission) {
        permissionDialogBuilder.setSmoothPermission(smoothPermission);
        return this;
    }

    /**
     *
     * @param buildAnyway - true if the Rationale should show permissions that not not been permanently denied.
     */

    @Override
    public void build(boolean buildAnyway) {
        permissionDialogBuilder.build(buildAnyway);
    }


    public static Bundle bundleResponse(ArrayList<SmoothPermission> smoothPermissions, boolean userDecline) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SMOOTH_PERMISSIONS, new RationaleResponse(smoothPermissions, smoothPermissions.size() > 0 && !userDecline, userDecline));
        return bundle;
    }

    public static boolean isResultFromRationale(int requestCode, int expectedCode) {
        return requestCode == expectedCode;
    }

    public static boolean permissionResolved(Intent intent) {
        return intent.getIntExtra(RationaleBase.RESULT_TYPE, -1) == RationaleDialog.PERMISSION_RESOLVE;
    }

    public static boolean noAction(Intent intent) {
        return intent.getIntExtra(RationaleBase.RESULT_TYPE, -1) == RationaleDialog.NO_ACTION;
    }

    public static RationaleResponse getRationaleResponse(Intent intent) {
        if (permissionResolved(intent) || noAction(intent)) {
            RationaleResponse rationaleResponse = getResponse(intent);
            if (rationaleResponse != null)
                return rationaleResponse;
        }
        return null;
    }

    private static RationaleResponse getResponse(Intent intent) {
        Bundle bundle = intent.getBundleExtra(RationaleBase.RESULT_DATA);
        if (bundle != null) {
            RationaleResponse rationaleResponse = bundle.getParcelable(SMOOTH_PERMISSIONS);
            return rationaleResponse;
        }
        return null;
    }

}
