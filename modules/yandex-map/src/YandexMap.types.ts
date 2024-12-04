import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode } from "react";

export type YandexMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type YandexMapViewProps = {
  style?: StyleProp<ViewStyle>;
  children?: ReactNode;
};

export type MarkerViewProps = {
  style?: StyleProp<ViewStyle>;
  children?: ReactNode;
  coordinate: {
    latitude: number;
    longitude: number;
  };
  key: string;
};
