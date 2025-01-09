import { requireNativeView } from "expo";
import * as React from "react";
import {
  IconAsset,
  IMarkerViewProps,
  INativeMarkerViewProps,
} from "@/modules/yandex-map";
import { View } from "react-native";

import { Asset } from "expo-asset";

const NativeView: React.ComponentType<INativeMarkerViewProps> =
  requireNativeView("Marker");

export default function MarkerView({
  icon,
  children,
  coordinate,
  text,
  textStyle,
  iconStyle,
  animated,
}: IMarkerViewProps) {
  const asset = resolveImageAsset(icon);

  return (
    <NativeView
      style={{ position: "absolute" }}
      coordinate={coordinate}
      text={text}
      textStyle={textStyle}
      iconStyle={iconStyle}
      iconData={{ icon: asset, animated }}
    >
      {children && <View style={{ zIndex: 0 }}>{children}</View>}
    </NativeView>
  );
}

function resolveImageAsset(iconSource?: IconAsset) {
  return iconSource ? Asset.fromModule(iconSource).uri : null;
}
