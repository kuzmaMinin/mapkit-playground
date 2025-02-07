import ExpoModulesCore
import YandexMapsMobile

class PolygonView: ExpoView {
    weak var delegate: MapObjectsDelegate?
    
    var polygonMapObject: YMKPolygonMapObject? = nil
    
    private var polygonConfig: PolygonConfig = PolygonConfig()
    
    func setPointsValue(_ points: Array<Coordinate>) {
        let pointMap = points.map { $0.toPoint() }
        
        polygonConfig.points = pointMap
    }
    
    func setInnerPointsValue(_ points: Array<Coordinate>) {
        let innerPointMap = points.map { $0.toPoint() }
        
        polygonConfig.innerPoints = innerPointMap
    }
    
    func setStrokeWidthValue(_ strokeWidth: Float) {
        polygonConfig.strokeWidth = strokeWidth
    }

    func setStrokeColorValue(_ color: String) {
        polygonConfig.strokeColor = color
    }
    
    func setFillColorValue(_ color: String) {
        polygonConfig.fillColor = color
    }
    
    func updatePolygon() {
        guard let mapObjects = delegate?.mapObjects else { return }
        
        let polygon: YMKPolygon = {
            var points = polygonConfig.points
            
            if points.count > 0 {
                points.append(points[0])
            }
            
            let outerRing = YMKLinearRing(points: points)
            
            var innerPoints = polygonConfig.innerPoints
            
            if innerPoints.count > 0 {
                innerPoints.append(innerPoints[0])
            }
            
            let innerRing = YMKLinearRing(points: innerPoints)
            
            return YMKPolygon(outerRing: outerRing, innerRings: [innerRing])
        }()
        
        polygonMapObject = mapObjects.addPolygon(with: polygon)
        
        if polygonConfig.fillColor != nil {
            polygonMapObject!.fillColor = UIColor.fromString(polygonConfig.fillColor!)!
        }
        
        if polygonConfig.strokeColor != nil {
            polygonMapObject!.strokeColor = UIColor.fromString(polygonConfig.strokeColor!)!
        }
        
        if polygonConfig.strokeWidth != nil {
            polygonMapObject!.strokeWidth = polygonConfig.strokeWidth!
        }
    }
}
