import ExpoModulesCore
import YandexMapsMobile

protocol MarkerDelegate: AnyObject {
    var markersCollection: YMKMapObjectCollection? { get }
    var clusterizedCollection: YMKClusterizedPlacemarkCollection? { get }
    var mapConfig: MapConfig { get }
}

class YandexMapView: ExpoView, MarkerDelegate {
    let onMapReady = EventDispatcher()
    let onMapTap = EventDispatcher()
    let onMapLongTap = EventDispatcher()
    let onClusterPress = EventDispatcher()
    
    var isMapLoaded = false
    var mapConfig: MapConfig = MapConfig()
    var map: YMKMap?
    
    var mapObjects: YMKMapObjectCollection?
    var markersCollection: YMKMapObjectCollection?
    var clusterizedCollection: YMKClusterizedPlacemarkCollection?
    
    private var mapView: YMKMapView?
    var markerViews: Array<MarkerView> = []
    
    private lazy var mapLoadedListener: YMKMapLoadedListener = MapLoadedListener(viewController: self)
    private lazy var mapInputListener: YMKMapInputListener = InputListener(viewController: self)
    private lazy var clusterListener: YMKClusterListener = ClusterListener(viewController: self)
        
    override func insertSubview(_ view: UIView, at index: Int) {
        switch view {
            
        case let markerView as MarkerView where !isMapLoaded:
            markerView.delegate = self
            markerViews.append(markerView)
            
            break
            
        case  let markerView as MarkerView where isMapLoaded:
            markerView.updateMarker()
            
            break
            
            
        default:
            break
            
        }
        
        super.insertSubview(view, at: index)
    }
    
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
    
    func setClusterized(_ isClusterized: Bool) {
        mapConfig.clusterized = isClusterized
    }

    func moveCamera(_ position: Position) {
        let zoom = position.zoom ?? map?.cameraPosition.zoom
        let azimuth = position.azimuth ?? map?.cameraPosition.azimuth
        let tilt = position.tilt ?? map?.cameraPosition.tilt
        
        let cameraPosition = YMKCameraPosition(
            target: position.toPoint(),
            zoom: zoom ?? 13.0,
            azimuth: azimuth ?? 0.0,
            tilt: tilt ?? 0.0
        )
        
        moveCameraToPosition(position: cameraPosition)
    }
    
    func moveCameraToPosition(
        position: YMKCameraPosition,
        duration: Float = 1.0,
        cameraCallback: YMKMapCameraCallback? = nil
    ) {
        map?.move(
            with: position,
            animation: YMKAnimation(
                type: YMKAnimationType.linear,
                duration: duration
            ), cameraCallback: cameraCallback)
    }
    
    func fitMarkers(_ markers: Array<YMKPoint>) {
        if (markers.isEmpty) {
            return
        }
        
        var cameraPosition = map?.cameraPosition(
            with: YMKGeometry.init(boundingBox: calculateBoundingBox(markers))
        )
        
        if (cameraPosition != nil) {
            cameraPosition = YMKCameraPosition(
                target: cameraPosition!.target,
                zoom: cameraPosition!.zoom - 0.8,
                azimuth: cameraPosition!.azimuth,
                tilt: cameraPosition!.tilt
            )
            
            moveCameraToPosition(position: cameraPosition!)
        }
    }
    
    func calculateBoundingBox(_ points: Array<YMKPoint>) -> YMKBoundingBox {
        var minLat = Double.infinity
        var maxLat = -Double.infinity
        var minLng = Double.infinity
        var maxLng = -Double.infinity
        
        points.forEach { point in
            minLat = min(minLat, point.latitude)
            maxLat = max(maxLat, point.latitude)
            minLng = min(minLng, point.longitude)
            maxLng = max(maxLng, point.longitude)
        }
        
        return YMKBoundingBox(
            southWest: YMKPoint(latitude: minLat, longitude: minLng),
            northEast: YMKPoint(latitude: maxLat, longitude: maxLng)
        )
    }
    
    
    required init (appContext: AppContext? = nil) {
        // TODO: check vulkan preffered
        mapView = YMKMapView(frame: CGRect.zero, vulkanPreferred: true)
        
        map = mapView?.mapWindow.map
        mapObjects = map?.mapObjects
        
        markersCollection = mapObjects?.add()
        
        super.init(appContext: appContext)
                
        clipsToBounds = true
        
        if mapView != nil {
            addSubview(mapView!)
        }
        
        map?.setMapLoadedListenerWith(mapLoadedListener)
        map?.addInputListener(with: mapInputListener)
        
        clusterizedCollection = mapObjects?.addClusterizedPlacemarkCollection(with: clusterListener)
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
        
        viewController?.markerViews.forEach({ $0.updateMarker() })
        
        guard let initialRegion = viewController?.mapConfig.initialRegion else { return }
        
        // TODO: check initial region
        let cameraPosition = YMKCameraPosition(
            target: initialRegion.toPoint(),
            zoom: initialRegion.zoom ?? 13,
            azimuth: initialRegion.azimuth ?? 0,
            tilt: initialRegion.tilt ?? 0
        )
        
        viewController?.moveCameraToPosition(position: cameraPosition)
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

final class ClusterListener: NSObject, YMKClusterListener, YMKClusterTapListener {
    private weak var viewController: YandexMapView?
    
    init(viewController: YandexMapView) {
        self.viewController = viewController
    }
    
    func onClusterAdded(with cluster: YMKCluster) {
        //cluster.appearance.setViewWithView(YRTViewProvider(uiView: ClusterView()))
        
        cluster.addClusterTapListener(with: self)
    }
    
    func onClusterTap(with cluster: YMKCluster) -> Bool {
        viewController?.onClusterPress(["size": cluster.size])
        
        let markers = cluster.placemarks.map {
            return YMKPoint(latitude: $0.geometry.latitude, longitude: $0.geometry.longitude)
        }
        
        viewController?.fitMarkers(markers)
        
        return true
    }
}
