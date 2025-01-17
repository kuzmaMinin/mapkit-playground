import { ICoordinate } from "../common/common.types";

export interface IPolygonViewProps {
  points: ICoordinate[];
  innerPoints?: ICoordinate[];
  strokeWidth?: number;
  strokeColor?: string;
  fillColor?: string;
}
