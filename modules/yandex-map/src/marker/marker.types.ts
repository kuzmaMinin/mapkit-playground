import { ReactNode, SyntheticEvent } from "react";
import type { StyleProp, ViewStyle } from "react-native";
import { ICoordinate } from "../common/common.types";

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

interface IBaseMarkerProps {
  coordinate: ICoordinate;
  children: ReactNode;
  style?: StyleProp<ViewStyle>;
  iconStyle?: IIconStyle;
  onPress?: (event: SyntheticEvent<any, ICoordinate>) => void;
}

export interface IMarkerViewProps extends IBaseMarkerProps {}

export interface INativeMarkerViewProps extends IBaseMarkerProps {
  onMarkerPress?: (event: SyntheticEvent<any, ICoordinate>) => void;
}
