import { requireNativeView } from "expo";
import * as React from "react";
import { IMarkerViewProps, INativeMarkerViewProps } from "./marker.types";
import { View } from "react-native";

const NativeView: React.ComponentType<INativeMarkerViewProps> =
  requireNativeView("Marker");

export default function MarkerView({
  children,
  coordinate,
  iconStyle,
  onPress,
}: IMarkerViewProps) {
  return (
    <NativeView
      style={{ position: "absolute" }}
      coordinate={coordinate}
      iconStyle={iconStyle}
      onMarkerPress={onPress}
    >
      <View style={{ zIndex: 0 }}>{children}</View>
    </NativeView>
  );
}

// TODO: Android replace onPress with onMarkerPress on native side
