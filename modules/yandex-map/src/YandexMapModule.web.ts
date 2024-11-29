import { registerWebModule, NativeModule } from "expo";

import { ChangeEventPayload } from "./YandexMap.types";

type YandexMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

class YandexMapModule extends NativeModule<YandexMapModuleEvents> {}

export default registerWebModule(YandexMapModule);
