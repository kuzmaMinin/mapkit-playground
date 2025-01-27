import * as React from "react";
import { requireNativeView } from "expo";
import { ICircleViewProps } from "./circle.types";

const NativeView: React.ComponentType<ICircleViewProps> =
  requireNativeView("Circle");

export default function CircleView({
  center,
  radius,
  strokeColor,
  strokeWidth,
  fillColor,
}: ICircleViewProps) {
  return (
    <NativeView
      center={center}
      radius={radius}
      strokeColor={strokeColor}
      strokeWidth={strokeWidth}
      fillColor={fillColor}
    />
  );
}
