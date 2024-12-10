import { requireNativeView } from "expo";
import * as React from "react";
import { IconAsset, MarkerViewProps } from "@/modules/yandex-map";
import { View } from "react-native";

import { Asset } from "expo-asset";

const NativeView: React.ComponentType<MarkerViewProps> =
  requireNativeView("Marker");

export default function MarkerView({
  iconSource,
  children,
  coordinate,
}: MarkerViewProps) {
  const asset = resolveImageAsset(iconSource);

  return (
    <NativeView
      style={{ position: "absolute" }}
      icon={asset}
      coordinate={coordinate}
    >
      {children && <View style={{ zIndex: 0 }}>{children}</View>}
    </NativeView>
  );
}

function resolveImageAsset(iconSource?: IconAsset) {
  return iconSource ? Asset.fromModule(iconSource).uri : null;
}
