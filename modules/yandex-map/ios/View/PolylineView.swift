import ExpoModulesCore
import YandexMapsMobile

class PolylineView: ExpoView {
    weak var delegate: MapObjectsDelegate?
    
    var polylineMapObject: YMKPolylineMapObject? = nil
    
    private var polylineConfig: PolylineConfig = PolylineConfig()

    func setPointsValue(_ points: Array<Coordinate>) {
        let pointMap = points.map { $0.toPoint() }
        
        polylineConfig.points = pointMap
    }
    
    func setStrokeWidthValue(_ strokeWidth: Float) {
        polylineConfig.strokeWidth = strokeWidth
    }

    func setStrokeColorValue(_ color: String) {
        polylineConfig.strokeColor = color
    }
    
    func setOutlineWidthValue(_ strokeWidth: Float) {
        polylineConfig.outlineWidth = strokeWidth
    }

    func setOutlineColorValue(_ color: String) {
        polylineConfig.outlineColor = color
    }
    
    func updatePolyline() {
        guard let mapObjects = delegate?.mapObjects else { return }
        
        let polyline: YMKPolyline = {
            YMKPolyline(
                points: polylineConfig.points
            )
        }()
        
        polylineMapObject = mapObjects.addPolyline(with: polyline)
        
        if polylineConfig.strokeColor != nil {
            polylineMapObject!.setStrokeColorWith(UIColor.fromString(polylineConfig.strokeColor!)!)
        }
        
        if polylineConfig.outlineColor != nil {
            polylineMapObject!.outlineColor = UIColor.fromString(polylineConfig.outlineColor!)!
        }
        
        if polylineConfig.strokeWidth != nil {
            polylineMapObject!.strokeWidth = polylineConfig.strokeWidth!
        }
        
        if polylineConfig.outlineWidth != nil {
            polylineMapObject!.outlineWidth = polylineConfig.outlineWidth!
        }
    }
}

