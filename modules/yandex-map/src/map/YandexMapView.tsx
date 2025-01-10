import { requireNativeView } from "expo";
import * as React from "react";

import { YandexMapViewProps } from "./YandexMap.types";

const NativeView: React.ComponentType<YandexMapViewProps> =
  requireNativeView("YandexMap");

export default function YandexMapView(props: YandexMapViewProps) {
  return <NativeView {...props} />;
}
