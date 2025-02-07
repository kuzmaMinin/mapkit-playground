import ExpoModulesCore
import YandexMapsMobile

protocol MarkerDelegate: AnyObject {
    var markersCollection: YMKMapObjectCollection? { get }
    var clusterizedCollection: YMKClusterizedPlacemarkCollection? { get }
    var mapConfig: MapConfig { get }
    var clusterConfig: ClusterConfigModel { get }
}

protocol MapObjectsDelegate: AnyObject {
    var mapObjects: YMKMapObjectCollection? { get }
}

class YandexMapView: ExpoView, MarkerDelegate, MapObjectsDelegate {
    let onMapReady = EventDispatcher()
    let onMapTap = EventDispatcher()
    let onMapLongTap = EventDispatcher()
    let onClusterPress = EventDispatcher()
    
    var map: YMKMap?
    var mapObjects: YMKMapObjectCollection?
    var markersCollection: YMKMapObjectCollection?
    var clusterizedCollection: YMKClusterizedPlacemarkCollection?
    
    var mapConfig: MapConfig = MapConfig()
    var clusterConfig: ClusterConfigModel = ClusterConfigModel()
    var clusterStyle: ClusterStyleModel = ClusterStyleModel()
    
    private var mapView: YMKMapView?
    
    var markerViews: Array<MarkerView> = []
    var circleViews: Array<CircleView> = []
    var polygonViews: Array<PolygonView> = []
    
    var isMapLoaded = false
    
    private lazy var mapLoadedListener: YMKMapLoadedListener = MapLoadedListener(viewController: self)
    private lazy var mapInputListener: YMKMapInputListener = InputListener(viewController: self)
    private lazy var clusterListener: YMKClusterListener = ClusterListener(viewController: self)
        
    override func insertSubview(_ view: UIView, at index: Int) {
        switch view {
            
        case let markerView as MarkerView where !isMapLoaded:
            markerView.delegate = self
            markerViews.append(markerView)
            
            break
        case let circleView as CircleView where !isMapLoaded:
            circleView.delegate = self
            circleViews.append(circleView)
            
            break
        case let polygonView as PolygonView where !isMapLoaded:
            polygonView.delegate = self
            polygonViews.append(polygonView)
            
            break
        case  let markerView as MarkerView where isMapLoaded:
            markerView.delegate = self
            markerView.updateMarker()
            
            break
            
        case let circleView as CircleView where isMapLoaded:
            circleView.delegate = self
            circleView.updateCircle()
            
            break
        case let polygonView as PolygonView where isMapLoaded:
            polygonView.delegate = self
            polygonView.updatePolygon()
            
            break
        default:
            break
        }
        
        super.insertSubview(view, at: index)
    }
    
    override func willRemoveSubview(_ view: UIView) {
        switch view {
        // TODO: check case when last placemark not disappeared
        case _ as MarkerView:
            if mapConfig.clusterized {
                clusterizedCollection?.remove(with: (view as! MarkerView).placemark!)
                
                clusterizedCollection?.clusterPlacemarks(
                    withClusterRadius: clusterConfig.clusterRadius,
                    minZoom: UInt(clusterConfig.minZoom)
                )
            } else {
                markersCollection?.remove(with: (view as! MarkerView).placemark!)
            }
            
            break
            
        case _ as CircleView:
            mapObjects?.remove(with: (view as! CircleView).circleMapObject!)
            
            break
            
        case _ as PolygonView:
            mapObjects?.remove(with: (view as! PolygonView).polygonMapObject!)
            
            break
        default:
            break
        }
        
        super.willRemoveSubview(view)
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
    
    func setClusterConfig(_ config: ClusterConfigModel) {
        clusterConfig = config
    }
    
    func setClusterStyle(_ style: ClusterStyleModel) {
        clusterStyle = style
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
        viewController?.circleViews.forEach({ $0.updateCircle() })
        viewController?.polygonViews.forEach({ $0.updatePolygon() })
        
        guard let initialRegion = viewController?.mapConfig.initialRegion else { return }
        
        let cameraPosition = YMKCameraPosition(
            target: initialRegion.toPoint(),
            zoom: initialRegion.zoom ?? INITIAL_CAMERA_ZOOM,
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
        let clusterView = ClusterView()
        clusterView.count = cluster.size
        clusterView.clusterStyle = viewController?.clusterStyle ?? ClusterStyleModel()
        
        cluster.appearance.setViewWithView(YRTViewProvider(uiView: clusterView))
        
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
