import { WebPlugin } from '@capacitor/core';

import type { FullscreenPlugin, SafeAreaInsets } from './definitions';

export class FullscreenWeb extends WebPlugin implements FullscreenPlugin {
    async getSafeAreaInsets(): Promise<SafeAreaInsets> {
        throw new Error('not implemented')
    }
    async setNavigationBarContrastColor(): Promise<void> {
        throw new Error('not implemented')
    }
    async setStatusBarContrastColor(): Promise<void> {
        throw new Error('not implemented')
    }
}
