# @capacitor-community//fullscreen

Enables immersive fullscreen mode for android devices

## Install

```bash
npm install @capacitor-community//fullscreen
npx cap sync
```

## API

<docgen-index>

* [`setNavigationBarContrastColor(...)`](#setnavigationbarcontrastcolor)
* [`setStatusBarContrastColor(...)`](#setstatusbarcontrastcolor)
* [`getSafeAreaInsets()`](#getsafeareainsets)
* [`getSafeAreaInsets()`](#getsafeareainsets)
* [`addListener('insetsChanged', ...)`](#addlistenerinsetschanged)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setNavigationBarContrastColor(...)

```typescript
setNavigationBarContrastColor(value: { contrast: 'light' | 'dark'; }) => Promise<void>
```

| Param       | Type                                          |
| ----------- | --------------------------------------------- |
| **`value`** | <code>{ contrast: 'light' \| 'dark'; }</code> |

--------------------


### setStatusBarContrastColor(...)

```typescript
setStatusBarContrastColor(value: { contrast: 'light' | 'dark'; }) => Promise<void>
```

| Param       | Type                                          |
| ----------- | --------------------------------------------- |
| **`value`** | <code>{ contrast: 'light' \| 'dark'; }</code> |

--------------------


### getSafeAreaInsets()

```typescript
getSafeAreaInsets() => Promise<SafeAreaInsets>
```

**Returns:** <code>Promise&lt;<a href="#safeareainsets">SafeAreaInsets</a>&gt;</code>

--------------------


### getSafeAreaInsets()

```typescript
getSafeAreaInsets() => Promise<SafeAreaInsets>
```

**Returns:** <code>Promise&lt;<a href="#safeareainsets">SafeAreaInsets</a>&gt;</code>

--------------------


### addListener('insetsChanged', ...)

```typescript
addListener(eventName: 'insetsChanged', listenerFunc: (insets: SafeAreaInsets) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                           |
| ------------------ | ------------------------------------------------------------------------------ |
| **`eventName`**    | <code>'insetsChanged'</code>                                                   |
| **`listenerFunc`** | <code>(insets: <a href="#safeareainsets">SafeAreaInsets</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### SafeAreaInsets

| Prop         | Type                |
| ------------ | ------------------- |
| **`top`**    | <code>number</code> |
| **`right`**  | <code>number</code> |
| **`bottom`** | <code>number</code> |
| **`left`**   | <code>number</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>
