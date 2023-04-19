import { registerPlugin } from '@capacitor/core';

import type { FullscreenPlugin } from './definitions';

const Fullscreen = registerPlugin<FullscreenPlugin>('Fullscreen', {
  web: () => import('./web').then(m => new m.FullscreenWeb()),
});

export * from './definitions';
export { Fullscreen };
