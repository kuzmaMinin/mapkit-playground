import { NativeModule, requireNativeModule } from "expo";

import { YandexMapModuleEvents } from "./YandexMap.types";

declare class YandexMapModule extends NativeModule<YandexMapModuleEvents> {}

// This call loads the native module object from the JSI.
export default requireNativeModule<YandexMapModule>("YandexMap");
