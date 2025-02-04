import ExpoModulesCore
import YandexMapsMobile

struct Coordinate: Record {
    @Field
    var latitude: Double = 0.0

    @Field
    var longitude: Double = 0.0

    func toPoint() -> YMKPoint {
        return YMKPoint(latitude: latitude, longitude: longitude)
    }
}

struct MarkerIconStyleModel: Record {
    @Field
    var anchor: PointXY = PointXY(x: 0.5, y: 0.5)

    @Field
    var rotationType: Float = .zero

    @Field
    var zIndex: Int = .zero

    @Field
    var flat: Bool = false

    @Field
    var visible: Bool = true

    @Field
    var scale: Float = 1.0

    @Field
    var tappableArea: RectRecord? = nil
    
    func toIconStyle() -> YMKIconStyle {
        let iconStyle = YMKIconStyle()
        iconStyle.anchor = NSValue(cgPoint: CGPoint(x: anchor.x, y: anchor.y))
        iconStyle.rotationType = NSNumber(value: rotationType)
        iconStyle.zIndex = NSNumber(value: zIndex)
        iconStyle.flat = NSNumber(value: flat)
        iconStyle.visible = NSNumber(value: visible)
        iconStyle.scale = NSNumber(value: scale)
        iconStyle.tappableArea = tappableArea?.toRect()
        
        return iconStyle
    }
    
}

struct PointXY: Record {
    @Field
    var x: Double = 0.0

    @Field
    var y: Double = 0.0
}

struct RectRecord: Record {
    @Field
    var min: PointXY? = nil

    @Field
    var max: PointXY? = nil

    func toRect() -> YMKRect {
        let minPoint = CGPoint(x: min?.x ?? 10.0, y: min?.y ?? 10.0)
        let maxPoint = CGPoint(x: max?.x ?? 10.0, y: max?.y ?? 10.0)

        return YMKRect(min: minPoint, max: maxPoint)
    }
}

struct PlacemarkConfig {
    var coordinate: YMKPoint = YMKPoint()
    var iconStyle: YMKIconStyle = YMKIconStyle()
    var iconView: UIView = UIView()
}
