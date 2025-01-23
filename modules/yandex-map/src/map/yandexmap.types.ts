import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode, SyntheticEvent, Ref } from "react";
import { ICoordinate } from "@/modules/yandex-map";

export interface IMoveParams {
  latitude: number;
  longitude: number;
  zoom?: number;
  azimuth?: number;
  tilt?: number;
}

export interface MapKitActions {
  move: (params: IMoveParams) => void;
  fitMarkers: (points: ICoordinate[]) => void;
}

export interface MapKitBaseProps {
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  onMapReady?: (event: SyntheticEvent<any, { payload: "success" }>) => void;
  clusterized?: boolean;
  clusterStyle?: {
    fontSize?: number;
    padding?: number;
    textColor?: string;
    backgroundColor?: string;
    strokeWidth?: number;
    strokeColor?: string;
  };
  clusterConfig?: {
    clusterRadius?: number;
    minZoom?: number;
  };
  onClusterPress?: (event: SyntheticEvent<any, { size: number }>) => void;
  initialRegion?: {
    latitude: number;
    longitude: number;
    zoom?: number;
    azimuth?: number;
    tilt?: number;
  };
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
