package com.digaus.capacitor.fullscreen;

import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowInsets;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;

public class Fullscreen {

    public void setStatusBarContrastColor(PluginCall call, Window window) {
        String contrast = call.getString("contrast");
        if (contrast == null) {
            call.reject("contrast is required");
            return;
        }
        WindowInsetsControllerCompat windowInsetsControllerCompat = WindowCompat.getInsetsController(window, window.getDecorView());
        windowInsetsControllerCompat.setAppearanceLightStatusBars(contrast.equals("dark"));
        call.resolve();
    }

    public void setNavigationBarContrastColor(PluginCall call, Window window) {
        String contrast = call.getString("contrast");
        if (contrast == null) {
            call.reject("contrast is required");
            return;
        }
        WindowInsetsControllerCompat windowInsetsControllerCompat = WindowCompat.getInsetsController(window, window.getDecorView());
        windowInsetsControllerCompat.setAppearanceLightNavigationBars(contrast.equals("dark"));
        call.resolve();
    }
    public JSObject getSafeAreaInsets(Bridge bridge) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(Fullscreen.class.toString(), String.format("Requires at least %d+", Build.VERSION_CODES.P));
            return this.result(0, 0, 0, 0);
        }
        WindowInsets windowInsets = bridge.getActivity().getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Log.i(Fullscreen.class.toString(), "WindowInsets is not available.");
            return this.result(0, 0, 0, 0);
        }
        float density = this.getDensity(bridge);
        int top = Math.round(windowInsets.getStableInsetTop() / density);
        int left = Math.round(windowInsets.getStableInsetLeft() / density);
        int right = Math.round(windowInsets.getStableInsetRight() / density);
        int bottom = Math.round(windowInsets.getStableInsetBottom() / density);
        Logger.warn("usableHeightNow_getSafeInsetBottom: " + String.valueOf(bottom));

        return this.result(top, left, right, bottom);
    }

    private JSObject result(int top, int left, int right, int bottom) {
        JSObject json = new JSObject();
        json.put("top", top);
        json.put("left", left);
        json.put("right", right);
        json.put("bottom", bottom);
        return json;
    }

    private float getDensity(Bridge bridge) {
        return bridge.getActivity().getResources().getDisplayMetrics().density;
    }
}
