import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode, SyntheticEvent } from "react";

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
  onMapReady?: (event: SyntheticEvent<any, { payload: "success" }>) => void;
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

interface IBaseMarkerProps {
  coordinate: ICoordinate;
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  text?: string;
  textStyle?: TextStyle;
  iconStyle?: IIconStyle;
  onPress?: (event: SyntheticEvent<any, ICoordinate>) => void;
}

export interface IMarkerViewProps extends IBaseMarkerProps {
  icon?: IconAsset;
  animated?: boolean;
}

export interface INativeMarkerViewProps extends IBaseMarkerProps {
  iconSource?: IconAsset;
  iconData?: { icon?: string | null; animated?: boolean };
}

export interface IPolygonViewProps {
  points: ICoordinate[];
  innerPoints?: ICoordinate[];
  strokeWidth?: number;
  strokeColor?: string;
  fillColor?: string;
}
