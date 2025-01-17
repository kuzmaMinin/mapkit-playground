import * as React from "react";

import { YandexMapViewProps } from "./yandexmap.types";

export default function YandexMapView(props: YandexMapViewProps) {
  return (
    <div>
      <iframe style={{ flex: 1 }} />
    </div>
  );
}
