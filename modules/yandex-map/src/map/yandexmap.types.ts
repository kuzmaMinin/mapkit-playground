import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode, SyntheticEvent } from "react";

export interface YandexMapViewProps {
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
}
