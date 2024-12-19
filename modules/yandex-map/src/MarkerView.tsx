import { requireNativeView } from "expo";
import * as React from "react";
import { IconAsset, IMarkerViewProps } from "@/modules/yandex-map";
import { View } from "react-native";

import { Asset } from "expo-asset";

const NativeView: React.ComponentType<IMarkerViewProps> =
  requireNativeView("Marker");

export default function MarkerView({
  iconSource,
  children,
  coordinate,
  text,
  textStyle,
  iconStyle,
  animated,
}: IMarkerViewProps) {
  const asset = resolveImageAsset(iconSource);

  return (
    <NativeView
      style={{ position: "absolute" }}
      icon={asset}
      coordinate={coordinate}
      text={text}
      textStyle={textStyle}
      iconStyle={iconStyle}
      animated={animated}
    >
      {children && <View style={{ zIndex: 0 }}>{children}</View>}
    </NativeView>
  );
}

function resolveImageAsset(iconSource?: IconAsset) {
  return iconSource ? Asset.fromModule(iconSource).uri : null;
}
