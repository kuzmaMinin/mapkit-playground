import { requireNativeView } from "expo";
import * as React from "react";

import { MapKitNativeProps, MapKitProps } from "./yandexmap.types";
import { ComponentType } from "react";

const NativeView: ComponentType<MapKitNativeProps> =
  requireNativeView("YandexMap");

export default function YandexMapView({
  children,
  style,
  onMapReady,
  clusterized,
  clusterStyle,
  clusterConfig,
  onClusterPress,
  initialRegion,
  mapRef,
  onMapTap,
  onMapLongTap,
  zoomEnabled = true,
  tiltEnabled = true,
  rotationEnabled = true,
  scrollEnabled = true,
}: MapKitProps) {
  return (
    <NativeView
      children={children}
      style={style}
      onMapReady={onMapReady}
      clusterized={clusterized}
      clusterStyle={clusterStyle}
      clusterConfig={clusterConfig}
      onClusterPress={onClusterPress}
      initialRegion={initialRegion}
      ref={mapRef}
      zoomEnabled={zoomEnabled}
      tiltEnabled={tiltEnabled}
      rotationEnabled={rotationEnabled}
      scrollEnabled={scrollEnabled}
      onMapTap={onMapTap}
      onMapLongTap={onMapLongTap}
    />
  );
}
