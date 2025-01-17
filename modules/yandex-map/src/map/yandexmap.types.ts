import type { StyleProp, ViewStyle } from "react-native";
import { ReactNode, SyntheticEvent } from "react";

export interface YandexMapViewProps {
  children?: ReactNode;
  style?: StyleProp<ViewStyle>;
  onMapReady?: (event: SyntheticEvent<any, { payload: "success" }>) => void;
}
