import { ICoordinate } from "@/modules/yandex-map";

export interface IPolylineViewProps {
  points: ICoordinate[];
  strokeWidth?: number;
  strokeColor?: string;
  outlineWidth?: number;
  outlineColor?: string;
}
