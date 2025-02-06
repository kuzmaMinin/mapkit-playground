import ExpoModulesCore

public class YandexMapModule: Module {
  public func definition() -> ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView.self) {
        Events("onMapReady", "onMapTap", "onMapLongTap", "onClusterPress")
        
        Prop("clusterized") { (view: YandexMapView, isClusterized: Bool) in
            view.setClusterized(isClusterized)
        }
        
        Prop("clusterStyle") { (view: YandexMapView, clusterStyle: ClusterStyleModel) in
            view.setClusterStyle(clusterStyle)
        }
        
        Prop("clusterConfig") { (view: YandexMapView, config: ClusterConfigModel) in
            view.setClusterConfig(config)
        }
        
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
        
        AsyncFunction("moveToCenter") { (view: YandexMapView, position: Position) in
            view.moveCamera(position)
        }
        
        AsyncFunction("fitMarkers") { (view: YandexMapView, points: Array<Position>) in
            let markers = points.map { $0.toPoint() }
            
            view.fitMarkers(markers)
        }
    }
  }
}
