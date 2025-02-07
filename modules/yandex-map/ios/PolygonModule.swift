import ExpoModulesCore

public class PolygonModule: Module {
    public func definition() -> ModuleDefinition {
        Name("Polygon")
        
        View(PolygonView.self) {
            Prop("points") { (view: PolygonView, points: Array<Coordinate>) in
                view.setPointsValue(points)
            }
            
            Prop("innerPoints") { (view: PolygonView, points: Array<Coordinate>) in
                view.setInnerPointsValue(points)
            }
            
            Prop("strokeColor") { (view: PolygonView, color: String) in
                view.setStrokeColorValue(color)
            }
            
            Prop("strokeWidth") { (view: PolygonView, width: Float) in
                view.setStrokeWidthValue(width)
            }
            
            Prop("fillColor") { (view: PolygonView, color: String) in
                view.setFillColorValue(color)
            }
        }
    }
}

