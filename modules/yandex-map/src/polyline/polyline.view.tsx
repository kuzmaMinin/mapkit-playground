import * as React from "react";
import { requireNativeView } from "expo";
import { IPolylineViewProps } from "./polyline.types";

const NativeView: React.ComponentType<IPolylineViewProps> =
  requireNativeView("Polyline");

export default function PolylineView({
  points,
  strokeColor,
  strokeWidth,
  outlineWidth,
  outlineColor,
}: IPolylineViewProps) {
  return (
    <NativeView
      points={points}
      strokeColor={strokeColor}
      strokeWidth={strokeWidth}
      outlineWidth={outlineWidth}
      outlineColor={outlineColor}
    />
  );
}
