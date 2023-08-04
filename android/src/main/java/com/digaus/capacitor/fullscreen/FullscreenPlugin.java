package com.digaus.capacitor.fullscreen;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Fullscreen")
public class FullscreenPlugin extends Plugin {

    private Fullscreen fullscreen = new Fullscreen();

    private View decorView;
    private View contentView;
    private int additionalSpacing;
    private int lastOrientation = -1;

    @PluginMethod
    public void getSafeAreaInsets(PluginCall call) {
        call.resolve(fullscreen.getSafeAreaInsets(getBridge()));
    }

    @PluginMethod
    public void setNavigationBarContrastColor(PluginCall call) {
        getBridge()
            .executeOnMainThread(
                () -> {
                    fullscreen.setNavigationBarContrastColor(call, getActivity().getWindow());
                }
            );
    }

    @PluginMethod
    public void setStatusBarContrastColor(PluginCall call) {
        getBridge()
            .executeOnMainThread(
                () -> {
                    fullscreen.setStatusBarContrastColor(call, getActivity().getWindow());
                }
            );
    }

    @Override
    public void load() {
        super.load();
        this.hideBars();

        this.decorView = getActivity().getWindow().getDecorView();
        this.contentView = ((ViewGroup) decorView.findViewById(android.R.id.content)).getChildAt(0);

        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            JSObject insets = fullscreen.getSafeAreaInsets(getBridge());
            notifyListeners("insetsChanged", insets, true);
            Boolean keyboardResizeNative = getConfig().getBoolean("keyboardResizeNative", true);
            if (keyboardResizeNative) {
                //r will be populated with the coordinates of your view that area still visible.
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                //get screen height and calculate the difference with the useable area from the r
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int orientation = getActivity().getResources().getConfiguration().orientation;

                if (orientation != lastOrientation) {
                    lastOrientation = orientation;
                    additionalSpacing = (height - r.bottom) * -1;
                }
                int diff = height + additionalSpacing - r.bottom;

                // Somehow we need this on Android < 9
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && diff > 0) {
                    diff = (int) (diff - insets.getInteger("bottom") * getActivity().getResources().getDisplayMetrics().density);
                }

                if (contentView.getPaddingBottom() != diff && diff >= 0) {
                    contentView.setPadding(0, 0, 0, diff);
                }
            }
        });
    }

    @Override
    public void handleOnResume(){
        super.handleOnResume();
        this.hideBars();
    }
    @Override
    public void handleOnPause(){
        super.handleOnPause();
        this.hideBars();
    }

    private void hideBars() {
        Logger.warn("Version: " + Build.VERSION.SDK_INT + " | " + Build.VERSION_CODES.R);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getBridge().getActivity().getWindow().setDecorFitsSystemWindows(false);
            this.getBridge().getActivity().getWindow().setStatusBarColor(0);
            this.getBridge().getActivity().getWindow().setNavigationBarColor(0);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            this.getBridge().getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            this.getBridge().getActivity().getWindow().setStatusBarColor(0);
            this.getBridge().getActivity().getWindow().setNavigationBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            this.getBridge().getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
    private void setWindowFlag(final int bits, boolean on) {
        Window win = this.getBridge().getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
