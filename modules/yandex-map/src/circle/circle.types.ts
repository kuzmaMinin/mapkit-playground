import { ICoordinate } from "@/modules/yandex-map";

export interface ICircleViewProps {
  center: ICoordinate;
  radius: number; // In meters
  strokeWidth?: number;
  strokeColor?: string;
  fillColor?: string;
}
