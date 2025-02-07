import ExpoModulesCore
import YandexMapsMobile

class CircleView: ExpoView {
    weak var delegate: MapObjectsDelegate?
    
    var circleMapObject: YMKCircleMapObject? = nil
    
    private var circleConfig: CircleConfig = CircleConfig()
    
    func setCenterValue(_ center: Coordinate) {
        circleConfig.center = center.toPoint()
    }
    
    func setRadiusValue(_ radius: Float) {
        circleConfig.radius = radius
    }
    
    func setStrokeWidthValue(_ strokeWidth: Float) {
        circleConfig.strokeWidth = strokeWidth
    }
    
    func setStrokeColorValue(_ color: String) {
        circleConfig.strokeColor = color
    }
    
    func setFillColorValue(_ color: String) {
        circleConfig.fillColor = color
    }
    
    func updateCircle() {
        guard let mapObjects = delegate?.mapObjects else { return }
        
        let circle = YMKCircle(
            center: circleConfig.center,
            radius: circleConfig.radius
        )
        
        circleMapObject = mapObjects.addCircle(with: circle)
        
        if circleConfig.fillColor != nil {
            circleMapObject!.fillColor = UIColor.fromString(circleConfig.fillColor!)!
        }
        
        if circleConfig.strokeColor != nil {
            circleMapObject!.strokeColor = UIColor.fromString(circleConfig.strokeColor!)!
        }
        
        if circleConfig.strokeWidth != nil {
            circleMapObject!.strokeWidth = circleConfig.strokeWidth!
        }
    }
}
