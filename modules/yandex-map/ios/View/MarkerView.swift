import ExpoModulesCore
import YandexMapsMobile

class MarkerView: ExpoView {
    let onMarkerPress = EventDispatcher()
    
    weak var delegate: MarkerDelegate?
    private lazy var markerTapListener: YMKMapObjectTapListener = TapListener(viewController: self)
    
    var placemark: YMKPlacemarkMapObject? = nil
    var placemarkConfig = PlacemarkConfig()
    
    override func insertSubview(_ subview: UIView, at atIndex: Int) {
        placemarkConfig.iconView = subview
        
        subview.isOpaque = false
        
        super.insertSubview(subview, at: atIndex)
    }
    
    func setCoordinate(_ point: YMKPoint) {
        placemarkConfig.coordinate = point
    }
    
    func setIconStyleValue(_ style: YMKIconStyle) {
        placemarkConfig.iconStyle = style
    }
    
    func updateCommonMarker() {
        placemark?.geometry = placemarkConfig.coordinate
        
        placemark?.addTapListener(with: markerTapListener)
        
        let iconView = YRTViewProvider(uiView: placemarkConfig.iconView)
        
        placemark?.setViewWithView(
            iconView ?? YRTViewProvider(),
            style: placemarkConfig.iconStyle
        )
    }
    
    func updateMarker() {
        if delegate?.mapConfig.clusterized == true {
            guard let clusterizedCollection = delegate?.clusterizedCollection else { return }
            
            placemark = clusterizedCollection.addPlacemark()
            
            updateCommonMarker()
            
            let clusterConfig = delegate?.clusterConfig ?? ClusterConfigModel()
            
            clusterizedCollection.clusterPlacemarks(
                withClusterRadius: clusterConfig.clusterRadius,
                minZoom: UInt(clusterConfig.minZoom)
            )
        } else {
            guard let markersCollection = delegate?.markersCollection else { return }
            
            placemark = markersCollection.addPlacemark()
            
            updateCommonMarker()
        }
    }
}

final class TapListener: NSObject, YMKMapObjectTapListener {
    func onMapObjectTap(with mapObject: YMKMapObject, point: YMKPoint) -> Bool {
        viewController?.onMarkerPress(["latitude": point.latitude, "longitude": point.longitude])
        
        return true
    }
    
    private weak var viewController: MarkerView?
    
    
    init(viewController: MarkerView) {
        self.viewController = viewController
    }
}
