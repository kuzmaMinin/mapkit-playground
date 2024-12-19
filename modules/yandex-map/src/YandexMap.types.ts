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

export interface YandexMapModuleEvents {
  onChange: (params: ChangeEventPayload) => void;
}

export interface ChangeEventPayload {
  value: string;
}

export interface YandexMapViewProps {
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
}

export enum Placement {
  CENTER,
  LEFT,
  RIGHT,
  TOP,
  BOTTOM,
  TOP_LEFT,
  TOP_RIGHT,
  BOTTOM_LEFT,
  BOTTOM_RIGHT,
}

export interface TextStyle {
  size?: number;
  color?: string;
  outlineWidth?: number;
  outlineColor?: string;
  placement?: Placement;
  offset?: number;
  offsetFromIcon?: boolean;
  textOptional?: boolean;
}

export interface IPosition {
  x: number;
  y: number;
}

export enum RotationType {
  NO_ROTATION,
  ROTATE,
}

export interface ITapableArea {
  min: IPosition;
  max: IPosition;
}

export interface IIconStyle {
  anchor?: IPosition;
  rotationType?: RotationType;
  zIndex?: number;
  flat?: boolean;
  visible?: boolean;
  scale?: number;
  tappableArea?: ITapableArea;
}

export interface ICoordinate {
  latitude: number;
  longitude: number;
}

export interface IMarkerViewProps {
  coordinate: ICoordinate;
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  iconSource?: IconAsset;
  icon?: string | null;
  text?: string;
  textStyle?: TextStyle;
  iconStyle?: IIconStyle;
  animated?: boolean;
}
