import * as React from "react";
import { IPolygonViewProps } from "@/modules/yandex-map";
import { requireNativeView } from "expo";

const NativeView: React.ComponentType<IPolygonViewProps> =
  requireNativeView("Polygon");

export default function PolygonView({
  points,
  innerPoints,
  strokeColor,
  strokeWidth,
  fillColor,
}: IPolygonViewProps) {
  return (
    <NativeView
      points={points}
      innerPoints={innerPoints}
      strokeColor={strokeColor}
      strokeWidth={strokeWidth}
      fillColor={fillColor}
    />
  );
}
