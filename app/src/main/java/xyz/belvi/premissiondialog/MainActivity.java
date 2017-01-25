package xyz.belvi.premissiondialog;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import xyz.belvi.permissiondialog.Permission.PermissionDetails;
import xyz.belvi.permissiondialog.Permission.PermissionTracker;
import xyz.belvi.permissiondialog.Permission.SmoothPermission;
import xyz.belvi.permissiondialog.Rationale.Rationale;
import xyz.belvi.permissiondialog.Rationale.RationaleResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPermDialog();
            }
        });


    }


    private void showPermDialog() {
        final PermissionDetails smsPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.READ_SMS, R.drawable.ic_sms_white_24dp);
        PermissionDetails storagePermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.READ_EXTERNAL_STORAGE, R.drawable.ic_sms_white_24dp);
        PermissionDetails audioPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.RECORD_AUDIO, R.drawable.ic_sms_white_24dp);
        PermissionDetails contactPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.WRITE_CONTACTS, R.drawable.ic_sms_white_24dp);
        PermissionDetails locationPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.ACCESS_FINE_LOCATION, R.drawable.ic_sms_white_24dp);
        PermissionDetails internetPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.INTERNET, R.drawable.ic_sms_white_24dp);

        Rationale.withActivity(this)
                .addSmoothPermission(new SmoothPermission(smsPermissionDetails.getPermission(), smsPermissionDetails.getDescription(), smsPermissionDetails.getDescription(), smsPermissionDetails.getPermissionIcon()))
                .addSmoothPermission(new SmoothPermission(storagePermissionDetails.getPermission(), storagePermissionDetails.getDescription(), storagePermissionDetails.getDescription(), storagePermissionDetails.getPermissionIcon()))
                .addSmoothPermission(new SmoothPermission(audioPermissionDetails.getPermission(), audioPermissionDetails.getDescription(), audioPermissionDetails.getDescription(), audioPermissionDetails.getPermissionIcon()))
                .includeStyle(R.style.Beliv_RationaleStyle).build(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Rationale.isResultFromRationale(requestCode)) {
            RationaleResponse rationaleResponse = Rationale.getRationaleResponse(data);
            if (rationaleResponse.shouldRequestForPermissions()) {
                super.onActivityResult(requestCode, resultCode, data);
                final PermissionDetails smsPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.RECORD_AUDIO, R.drawable.ic_sms_white_24dp);

                Dexter.withActivity(this).withPermission(smsPermissionDetails.getPermission())
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                PermissionTracker.markPermission(MainActivity.this, permission.getName());
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        }

    }

}
