import { requireNativeView } from "expo";
import * as React from "react";
import { MarkerViewProps } from "@/modules/yandex-map";
import { View } from "react-native";

const NativeView: React.ComponentType<MarkerViewProps> =
  requireNativeView("Marker");

export default function MarkerView(props: MarkerViewProps) {
  return (
    <NativeView {...props} style={{ position: "absolute" }}>
      {props.children && <View style={{ zIndex: 0 }}>{props.children}</View>}
    </NativeView>
  );
}
