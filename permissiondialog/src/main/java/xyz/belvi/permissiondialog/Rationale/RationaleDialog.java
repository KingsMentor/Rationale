package xyz.belvi.permissiondialog.Rationale;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.PermissionRationale;
import xyz.belvi.permissiondialog.Permission.PermissionState;
import xyz.belvi.permissiondialog.Permission.SmoothPermission;
import xyz.belvi.permissiondialog.R;

/**
 * Created by zone2 on 1/19/17.
 */

public class RationaleDialog extends DialogFragment {

    private ViewPager rationalePager;
    private AppCompatButton noButton, yesButton;
    private final String SMOOTH_PERMISSIONS = "com.appzonegroup.zone.displayMessage.SMOOTH_PERMISSIONS";
    private final String SHOW_SETTINGS = "com.appzonegroup.zone.displayMessage.SHOW_SETTINGS";
    private final String BUILD_ANYWAY = "com.appzonegroup.zone.displayMessage.BUILD_ANYWAY";
    private final String STYLE_RES = "com.appzonegroup.zone.displayMessage.STYLE_RES";
    private PermissionRationalePager permissionRationalePager;
    private static PermissionResolveListener permissionResolveListener;


    public RationaleDialog initialise(ArrayList<SmoothPermission> smoothPermission, PermissionResolveListener resolveListener, int styleRes, boolean showSettings, boolean buildAnyway) {

        Bundle argument = new Bundle();
        argument.putParcelableArrayList(SMOOTH_PERMISSIONS, smoothPermission);
        argument.putBoolean(SHOW_SETTINGS, showSettings);
        argument.putBoolean(BUILD_ANYWAY, buildAnyway);
        argument.putInt(STYLE_RES, styleRes);
        setArguments(argument);
        permissionResolveListener = resolveListener;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    private ArrayList<SmoothPermission> smoothPermissions;

    private ArrayList<SmoothPermission> getSmoothPermissions() {
        return smoothPermissions;
    }

    private boolean showSettings;

    private boolean showSettings() {
        return showSettings;
    }

    private boolean buildAnyway() {
        return getArguments().getBoolean(BUILD_ANYWAY);
    }

    private int getStyleRes() {
        return getArguments().getInt(STYLE_RES, R.style.Beliv_RationaleStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(false);
    }


    private int rationaleColorDeny;
    private int rationaleColorIgnore;
    private int rationaleTextColorDeny;
    private int rationaleTextColorIgnore;
    private float rationaleTextSize;
    private String rationaleTextFont;
    private int[] attrs = {R.attr.bv_primary, R.attr.bv_secondary,
            R.attr.bv_rationale_state_visibility, R.attr.bv_rationale_state_deny_color
            , R.attr.bv_rationale_state_ignore_color, R.attr.bv_rationale_text_color_deny,
            R.attr.bv_rationale_text_color_ignore, R.attr.bv_rationale_text_size, R.attr.bv_card_corner_radius
            , R.attr.bv_rationale_text_font
    };

    private void styleUI(View view) {


        TypedArray ta = getContext().obtainStyledAttributes(getStyleRes(), attrs);
        int primary_color = ta.getColor(0, ContextCompat.getColor(getContext(), R.color.primary_color));
        int secondary_color = ta.getColor(1, ContextCompat.getColor(getContext(), R.color.secondary_color));
        boolean showRationaleState = ta.getBoolean(2, true);

        rationaleColorDeny = ta.getColor(3, ContextCompat.getColor(getContext(), R.color.deny_state));
        rationaleColorIgnore = ta.getColor(4, ContextCompat.getColor(getContext(), R.color.ignore_state));


        rationaleTextColorDeny = ta.getColor(5, ContextCompat.getColor(getContext(), R.color.grey));
        rationaleTextColorIgnore = ta.getColor(6, ContextCompat.getColor(getContext(), R.color.ignore_state));

        rationaleTextSize = ta.getDimension(7, getResources().getDimension(R.dimen.rationale_text_size));


        float card_corner = ta.getDimension(8, getResources().getDimension(R.dimen.card_corner));

        rationaleTextFont = ta.getString(9);
        rationaleTextFont = rationaleTextFont == null ? getResources().getString(R.string.rationale_font) : rationaleTextFont.isEmpty() ? getResources().getString(R.string.rationale_font) : rationaleTextFont;

        ((CardView) view.findViewById(R.id.dialog_card)).setRadius(card_corner);

        view.findViewById(R.id.permission_state).setVisibility(showRationaleState ? View.VISIBLE : View.GONE);

        view.findViewById(R.id.primary).setBackgroundColor(primary_color);
        view.findViewById(R.id.secondary).setBackgroundColor(secondary_color);
        ((AppCompatButton) view.findViewById(R.id.permission_no)).setTextColor(primary_color);
        ((AppCompatButton) view.findViewById(R.id.permission_yes)).setTextColor(primary_color);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        waiting_for_permission = false;
        View view = inflater.inflate(R.layout.rationale_dialog, container, false);
        styleUI(view);
        smoothPermissions = getArguments().getParcelableArrayList(SMOOTH_PERMISSIONS);
        showSettings = getArguments().getBoolean(SHOW_SETTINGS);

        final AppCompatImageView drawableView = (AppCompatImageView) view.findViewById(R.id.permission_icon);
        yesButton = (AppCompatButton) view.findViewById(R.id.permission_yes);
        noButton = (AppCompatButton) view.findViewById(R.id.permission_no);
        final AppCompatTextView permissionState = (AppCompatTextView) view.findViewById(R.id.permission_state);

        rationalePager = (ViewPager) view.findViewById(R.id.permission_rationale_pager);
        permissionRationalePager = new PermissionRationalePager(getChildFragmentManager(), getSmoothPermissions());
        rationalePager.setAdapter(permissionRationalePager);
        updatePermissionState(permissionState, drawableView, 0);
        rationalePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position + 1 == getSmoothPermissions().size()) {
                    if (showSettings()) {
                        yesButton.setText("Settings");
                    } else {
                        yesButton.setText("Continue");
                    }
                } else {
                    if (getSmoothPermissions().size() > 1) {
                        yesButton.setText("Next");
                    }
                }
                updatePermissionState(permissionState, drawableView, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (getSmoothPermissions().size() > 1) {
            yesButton.setText("Next");
        } else {
            if (showSettings()) {
                yesButton.setText("Settings");
            } else {
                yesButton.setText("Continue");
            }
        }

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
                returnCallback(permissionResolveListener, getSmoothPermissions(), buildAnyway());

            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentIndex = rationalePager.getCurrentItem();
                if (getSmoothPermissions().size() > 1) {
                    if (currentIndex + 1 < getSmoothPermissions().size()) {
                        rationalePager.setCurrentItem(currentIndex + 1);
                        currentIndex = rationalePager.getCurrentItem();
                        if (currentIndex + 1 == getSmoothPermissions().size()) {
                            if (showSettings()) {
                                yesButton.setText("Settings");
                            } else {
                                yesButton.setText("Continue");
                            }
                        }
                    } else {
                        if (showSettings()) {
                            loadPermissionPage(getContext());
                        } else {
                            dismissAllowingStateLoss();
                            returnCallback(permissionResolveListener, getSmoothPermissions(), buildAnyway());

                        }
                    }
                } else {
                    if (showSettings()) {
                        loadPermissionPage(getContext());
                    } else {
                        dismissAllowingStateLoss();
                        returnCallback(permissionResolveListener, getSmoothPermissions(), buildAnyway());

                    }
                }
            }
        });

        return view;
    }

    private void updatePermissionState(AppCompatTextView permissionState, AppCompatImageView drawableImage, int position) {
        drawableImage.setImageDrawable(ContextCompat.getDrawable(getContext(), getSmoothPermissions().get(position).getDrawable()));
        if (getSmoothPermissions().get(position).getState() == PermissionState.PERMANENTLY_DENIED) {
            permissionState.setText("Permanently Denied");
            permissionState.setTextColor(rationaleColorIgnore);
        } else if (getSmoothPermissions().get(position).getState() == PermissionState.DENIED) {
            permissionState.setText("Denied");
            permissionState.setTextColor(rationaleColorDeny);
        }

    }


    private void loadPermissionPage(Context context) {
//        waiting_for_permission = true;
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    private class PermissionRationalePager extends FragmentStatePagerAdapter {
        ArrayList<SmoothPermission> smoothPermissions;

        public PermissionRationalePager(FragmentManager fm, ArrayList<SmoothPermission> smoothPermissions) {
            super(fm);
            this.smoothPermissions = smoothPermissions;
        }

        public void setSmoothPermissions(ArrayList<SmoothPermission> smoothPermissions) {
            this.smoothPermissions = smoothPermissions;
        }

        @Override
        public Fragment getItem(int position) {
            String text = "";
            int rationaleColor;
            if (smoothPermissions.get(position).getState() == PermissionState.PERMANENTLY_DENIED) {
                rationaleColor = rationaleTextColorIgnore;
                text = smoothPermissions.get(position).getDeniedMessage();
            } else {
                rationaleColor = rationaleTextColorDeny;
                text = smoothPermissions.get(position).getRationaleMessage();
            }
            return new PermissionRationale().newInstance(text, rationaleTextFont, rationaleColor, rationaleTextSize);
        }


        @Override
        public int getCount() {
            return this.smoothPermissions.size();
        }
    }

    public static void returnCallback(PermissionResolveListener permissionResolveListener, ArrayList<SmoothPermission> smoothPermissions, boolean buildAnyway) {
        if (buildAnyway)
            permissionResolveListener.onResolved(smoothPermissions);
        else {
            smoothPermissions = new ArrayList<>();
            RationaleDialogBuilder.showSettings(smoothPermissions, true);
            permissionResolveListener.onResolved(smoothPermissions);
        }
    }

    public static void returnPossibleCallback(PermissionResolveListener permissionResolveListener, ArrayList<SmoothPermission> smoothPermissions, boolean buildAnyway) {
        if (buildAnyway)
            permissionResolveListener.possiblePermissionUpdate(smoothPermissions);
        else {
            smoothPermissions = new ArrayList<>();
            RationaleDialogBuilder.showSettings(smoothPermissions, true);
            permissionResolveListener.possiblePermissionUpdate(smoothPermissions);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // refresh stuff
        ArrayList<SmoothPermission> smoothPermissions = new ArrayList<>();
        showSettings = RationaleDialogBuilder.showSettings(smoothPermissions, buildAnyway());
        this.smoothPermissions = smoothPermissions;
//            setArguments(argument);


        permissionRationalePager.setSmoothPermissions(smoothPermissions);
        permissionRationalePager.notifyDataSetChanged();
        rationalePager.setAdapter(permissionRationalePager);

        if (smoothPermissions.size() == 0) {
            dismissAllowingStateLoss();

            returnCallback(permissionResolveListener, getSmoothPermissions(), buildAnyway());

            if (showSettings) {
                yesButton.setText("Settings");
            } else {
                yesButton.setText("Continue");
            }
            //call
        } else {

            returnPossibleCallback(permissionResolveListener, getSmoothPermissions(), buildAnyway());

            if (showSettings) {
                yesButton.setText("Next");
            } else {
                yesButton.setText("Continue");
            }
        }


    }

}
