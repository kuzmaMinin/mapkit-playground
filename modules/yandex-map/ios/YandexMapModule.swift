import ExpoModulesCore

public class YandexMapModule: Module {
  public func definition() -> ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView.self) {
        Events("onMapReady", "onMapTap", "onMapLongTap")
        
        Prop("scrollEnabled") { (view: YandexMapView, isScrollEnabled: Bool) in
            view.setScrollEnabled(isScrollEnabled)
        }
        
        Prop("zoomEnabled") { (view: YandexMapView, isZoomEnabled: Bool) in
            view.setZoomEnabled(isZoomEnabled)
        }
        
        Prop("rotationEnabled") { (view: YandexMapView, isRotationEnabled: Bool) in
            view.setRotationEnabled(isRotationEnabled)
        }
        
        Prop("tiltEnabled") { (view: YandexMapView, isTiltEnabled: Bool) in
            view.setTiltEnabled(isTiltEnabled)
        }
        
        Prop("initialRegion") { (view: YandexMapView, position: Position) in
            view.setInitialRegion(position)
        }
    }
  }
}
