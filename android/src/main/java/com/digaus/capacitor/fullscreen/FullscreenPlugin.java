package com.digaus.capacitor.fullscreen;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

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
    private int additionalSpacing = -1;
    private int lastWidth = 0;

    private JSObject lastInsets;

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
        //only required on newer android versions. it was working on API level 19 (Build.VERSION_CODES.KITKAT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.decorView = this.getBridge().getActivity().getWindow().getDecorView();
            this.contentView = ((ViewGroup) decorView.findViewById(android.R.id.content)).getChildAt(0);
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hideBars();
    }*/
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
    /*@Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        this.hideBars();
    }*/

    private void hideBars() {
        Logger.warn("Version: " + Build.VERSION.SDK_INT + " | " + Build.VERSION_CODES.R);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getBridge().getActivity().getWindow().setDecorFitsSystemWindows(false);
            this.getBridge().getActivity().getWindow().setStatusBarColor(0);
            this.getBridge().getActivity().getWindow().setNavigationBarColor(0);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // On older versions of android setDecorFitsSystemWindows doesn't exist yet, but it can
            // be emulated with flags.
            // It still must be P or greater, as that is the min version for getting the insets
            // through the native plugin.

            this.getBridge().getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            this.getBridge().getActivity().getWindow().setStatusBarColor(0);
            this.getBridge().getActivity().getWindow().setNavigationBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        } else if (Build.VERSION.SDK_INT >= 19) {
            this.getBridge().getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            this.getBridge().getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
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


    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            JSObject insets = fullscreen.getSafeAreaInsets(getBridge());
            notifyListeners("insetsChanged", insets, true);


            /*
            //r will be populated with the coordinates of your view that area still visible.
            Rect r = new Rect();
            decorView.getWindowVisibleDisplayFrame(r);


            //get screen height and calculate the difference with the useable area from the r
            int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
            int width = decorView.getContext().getResources().getDisplayMetrics().widthPixels;
            if (additionalSpacing == -1 || lastWidth != width) {
                //get additional spacing that could be caused by elements like the status bar
                additionalSpacing = (height - r.bottom) * -1;
                lastWidth = width;
            }
            int diff = height + additionalSpacing - r.bottom;

            if (contentView.getPaddingBottom() != diff) {
              //  contentView.setPadding(0, 0, 0, diff);
            }*/
        }
    };
}
