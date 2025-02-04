import ExpoModulesCore

public class MarkerModule: Module {
    public func definition() -> ModuleDefinition {
        Name("Marker")
        
        View(MarkerView.self) {
            Prop("coordinate") { (view: MarkerView, coordinate: Coordinate) in
                view.setCoordinate(coordinate.toPoint())
            }
            
            Prop("iconStyle") { (view: MarkerView, style: MarkerIconStyleModel) in
                view.setIconStyleValue(style.toIconStyle())
            }
            
            Events("onMarkerPress")
        }
    }
}
