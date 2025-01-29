import ExpoModulesCore
import YandexMapsMobile

class YandexMapView: ExpoView {
    let onMapReady = EventDispatcher()
    let onMapTap = EventDispatcher()
    let onMapLongTap = EventDispatcher()
    
    var isMapLoaded = false
    var mapConfig: MapConfig = MapConfig()
    var map: YMKMap?
    
    private var mapView: YMKMapView?
    
    private lazy var mapLoadedListener: YMKMapLoadedListener = MapLoadedListener(viewController: self)
    private lazy var mapInputListener: YMKMapInputListener = InputListener(viewController: self)
    
    func setScrollEnabled(_ isScrollEnabled: Bool) {
        map?.isScrollGesturesEnabled = isScrollEnabled
    }
    
    func setZoomEnabled(_ isZoomEnabled: Bool) {
        map?.isZoomGesturesEnabled = isZoomEnabled
    }
    
    func setRotationEnabled(_ isRotationEnabled: Bool) {
        map?.isRotateGesturesEnabled = isRotationEnabled
    }
    
    func setTiltEnabled(_ isTiltEnabled: Bool) {
        map?.isTiltGesturesEnabled = isTiltEnabled
    }
    
    func setInitialRegion(_ position: Position) {
        mapConfig.initialRegion = position
    }
    
    required init (appContext: AppContext? = nil) {
        // TODO: check vulkan preffered
        mapView = YMKMapView(frame: CGRect.zero, vulkanPreferred: true)
        map = mapView?.mapWindow.map
        
        super.init(appContext: appContext)
                
        clipsToBounds = true
        
        if mapView != nil {
            addSubview(mapView!)
        }
        
        map?.setMapLoadedListenerWith(mapLoadedListener)
        map?.addInputListener(with: mapInputListener)
    }
}

final class MapLoadedListener: NSObject, YMKMapLoadedListener {
    private weak var viewController: YandexMapView?
    
    
    init(viewController: YandexMapView) {
        self.viewController = viewController
    }
        
    func onMapLoaded(with statistics: YMKMapLoadStatistics) {
        viewController?.isMapLoaded = true
        viewController?.onMapReady(["payload": "success"])
        
        guard let initialRegion = viewController?.mapConfig.initialRegion,
                  let map = viewController?.map else { return }
        
        let point = initialRegion.toPoint()
        // TODO: move to const
        map.move(
            with: YMKCameraPosition(
                target: initialRegion.toPoint(),
                zoom: initialRegion.zoom ?? 13.0,
                azimuth: initialRegion.azimuth ?? 0.0,
                tilt: initialRegion.tilt ?? 0.0
            ),
            animation: YMKAnimation(
                type: YMKAnimationType.linear,
                duration: 1.0
            ), cameraCallback: nil)
    }
}

final class InputListener: NSObject, YMKMapInputListener {
    private weak var viewController: YandexMapView?
    
    
    init(viewController: YandexMapView) {
        self.viewController = viewController
    }
    
    func onMapTap(with map: YMKMap, point: YMKPoint) {
        viewController?.onMapTap(["latitude": point.latitude, "longitude": point.longitude])
    }
    
    func onMapLongTap(with map: YMKMap, point: YMKPoint) {
        viewController?.onMapLongTap(["latitude": point.latitude, "longitude": point.longitude])
    }    
}
