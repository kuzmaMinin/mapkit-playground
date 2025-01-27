import * as React from "react";

import { MapKitProps } from "./yandexmap.types";

export default function YandexMapView(props: MapKitProps) {
  return (
    <div>
      <iframe style={{ flex: 1 }} />
    </div>
  );
}
