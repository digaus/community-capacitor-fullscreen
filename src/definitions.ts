/// <reference types="@capacitor/cli" />

import { PluginListenerHandle } from "@capacitor/core";

declare module '@capacitor/cli' {
    interface PluginsConfig {
        /**
         * On Android, the keyboard can be configured with the following options:
         */
        Fullscreen?: {
            keyboardResizeNative?: boolean;
        };
    }
}
export interface FullscreenPlugin {

    setNavigationBarContrastColor(value: {contrast: Contrast}): Promise<void>;
    setStatusBarContrastColor(value: {contrast: Contrast}): Promise<void>;
    getSafeAreaInsets(): Promise<SafeAreaInsets>;
    getSafeAreaInsets(): Promise<SafeAreaInsets>;
    addListener(
        eventName: 'insetsChanged',
        listenerFunc: (insets: SafeAreaInsets) => void,
    ): Promise<PluginListenerHandle> & PluginListenerHandle;

}

export enum Contrast {
    light = 'light',
    dark = 'dark',
}
export interface SafeAreaInsets {
    top: number;
    right: number;
    bottom: number;
    left: number;
}