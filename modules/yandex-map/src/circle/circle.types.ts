import { ICoordinate } from "@/modules/yandex-map";

export interface ICircleViewProps {
  center: ICoordinate;
  radius: number;
  strokeWidth?: number;
  strokeColor?: string;
  fillColor?: string;
}
