package xyz.belvi.permissiondialog.Permission;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.belvi.permissiondialog.R;

/**
 * Created by zone2 on 1/20/17.
 */

public class PermissionRationale extends Fragment {

    private final String TEXT_KEY = "xyz.belvi.permissiondialog.TEXT_KEY";
    private final String TEXT_COLOR = "xyz.belvi.permissiondialog.TEXT_COLOR";
    private final String TEXT_SIZE = "xyz.belvi.permissiondialog.TEXT_SIZE";
    private final String TEXT_FONT = "xyz.belvi.permissiondialog.TEXT_FONT";

    public PermissionRationale newInstance(String text, String fontName, int color, float size) {
        Bundle arguments = new Bundle();
        arguments.putString(TEXT_KEY, text);
        arguments.putString(TEXT_FONT, fontName);
        arguments.putInt(TEXT_COLOR, color);
        arguments.putFloat(TEXT_SIZE, size);
        setArguments(arguments);
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.permission_rationale, container, false);
        AppCompatTextView rationaleView = (AppCompatTextView) rootView.findViewById(R.id.permission_text);
        rationaleView.setText(getArguments().getString(TEXT_KEY));
        rationaleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getArguments().getFloat(TEXT_SIZE));
        rationaleView.setTextColor(getArguments().getInt(TEXT_COLOR));
        rationaleView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/" + getArguments().getString(TEXT_FONT)));
        return rootView;
    }
}
