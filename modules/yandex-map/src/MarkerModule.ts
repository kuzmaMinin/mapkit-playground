import { NativeModule, requireNativeModule } from "expo";

declare class YandexMapModule extends NativeModule<any> {}

// This call loads the native module object from the JSI.
export default requireNativeModule<YandexMapModule>("Marker");
