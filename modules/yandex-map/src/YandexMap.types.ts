import type { StyleProp, ViewStyle } from "react-native";
import * as React from "react";

export type YandexMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

export type ChangeEventPayload = {
  value: string;
};

interface IMarker {
  latitude: number;
  longitude: number;
}

export type YandexMapViewProps = {
  style?: StyleProp<ViewStyle>;
  markers?: IMarker[];
};
