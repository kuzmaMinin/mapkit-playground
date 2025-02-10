import ExpoModulesCore

public class PolylineModule: Module {
    public func definition() -> ModuleDefinition {
        Name("Polyline")
        
        View(PolylineView.self) {
            Prop("points") { (view: PolylineView, points: Array<Coordinate>) in
                view.setPointsValue(points)
            }
            
            Prop("strokeColor") { (view: PolylineView, color: String) in
                view.setStrokeColorValue(color)
            }
            
            Prop("strokeWidth") { (view: PolylineView, width: Float) in
                view.setStrokeWidthValue(width)
            }
            
            Prop("outlineColor") { (view: PolylineView, color: String) in
                view.setOutlineColorValue(color)
            }
            
            Prop("outlineWidth") { (view: PolylineView, width: Float) in
                view.setOutlineWidthValue(width)
            }
        }
    }
}

