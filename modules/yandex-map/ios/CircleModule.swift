import ExpoModulesCore

public class CircleModule: Module {
    public func definition() -> ModuleDefinition {
        Name("Circle")
        
        View(CircleView.self) {
            Prop("center") { (view: CircleView, center: Coordinate) in
                view.setCenterValue(center)
            }
            
            Prop("radius") { (view: CircleView, radius: Float) in
                view.setRadiusValue(radius)
            }
            
            Prop("strokeColor") { (view: CircleView, color: String) in
                view.setStrokeColorValue(color)
            }
            
            Prop("strokeWidth") { (view: CircleView, width: Float) in
                view.setStrokeWidthValue(width)
            }
            
            Prop("fillColor") { (view: CircleView, color: String) in
                view.setFillColorValue(color)
            }
        }
    }
}
