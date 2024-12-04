// Reexport the native module. On web, it will be resolved to YandexMapModule.web.ts
// and on native platforms to YandexMapModule.ts
export { default } from "./src/YandexMapModule";
export { default as YandexMap } from "./src/YandexMapView";
export { default as Marker } from "./src/MarkerView";
export * from "./src/YandexMap.types";
