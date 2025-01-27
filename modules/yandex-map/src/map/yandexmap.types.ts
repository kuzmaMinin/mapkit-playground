import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode, SyntheticEvent, Ref } from "react";
import { ICoordinate } from "@/modules/yandex-map";

export interface IClusterStyle {
  fontSize?: number;
  padding?: number;
  textColor?: string;
  backgroundColor?: string;
  strokeWidth?: number;
  strokeColor?: string;
}

export interface IClusterConfig {
  clusterRadius?: number;
  minZoom?: number;
}

export interface MapKitActions {
  moveToCenter: (params: IRegion) => void;
  fitMarkers: (points: ICoordinate[]) => void;
}

export interface IRegion {
  latitude: number;
  longitude: number;
  zoom?: number;
  azimuth?: number;
  tilt?: number;
}

export interface MapKitBaseProps {
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  onMapReady?: (event: SyntheticEvent<any, { payload: "success" }>) => void;
  onMapTap?: (event: SyntheticEvent<any, ICoordinate>) => void;
  onMapLongTap?: (event: SyntheticEvent<any, ICoordinate>) => void;
  clusterized?: boolean;
  clusterStyle?: IClusterStyle;
  clusterConfig?: IClusterConfig;
  onClusterPress?: (event: SyntheticEvent<any, { size: number }>) => void;
  initialRegion?: IRegion;
  scrollEnabled?: boolean;
  zoomEnabled?: boolean;
  rotationEnabled?: boolean;
  tiltEnabled?: boolean;
}

export interface MapKitProps extends MapKitBaseProps {
  mapRef?: Ref<MapKitActions>;
}

export interface MapKitNativeProps extends MapKitBaseProps {
  ref?: Ref<MapKitActions>;
}

export interface CameraPosition {
  latitude: number;
  longitude: number;
  zoom?: number;
  azimuth?: number;
  tilt?: number;
}
