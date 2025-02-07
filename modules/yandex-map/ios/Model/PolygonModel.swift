import YandexMapsMobile

struct PolygonConfig {
    var points: Array<YMKPoint> = []
    var innerPoints: Array<YMKPoint> = []
    var strokeWidth: Float? = nil
    var strokeColor: String? = nil
    var fillColor: String? = nil
}

