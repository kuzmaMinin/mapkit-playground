import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode } from "react";

export type IconAsset =
  | string
  | number
  | {
      height: number;
      uri: string;
      width: number;
    };

export type YandexMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type YandexMapViewProps = {
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
};

export type MarkerViewProps = {
  coordinate: {
    latitude: number;
    longitude: number;
  };
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  iconStyle?: StyleProp<ViewStyle>;
  iconSource?: IconAsset;
  icon?: string | null;
};
