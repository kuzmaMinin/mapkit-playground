import ExpoModulesCore
import YandexMapsMobile
// TODO: move to const
struct Position: Record {
    @Field
    var latitude: Double = 0.0
    
    @Field
    var longitude: Double = 0.0
    
    @Field
    var zoom: Float? = 13.0
    
    @Field
    var azimuth: Float? = .zero
    
    @Field
    var tilt: Float? = .zero
    
    func toPoint() -> YMKPoint {
        return YMKPoint(latitude: latitude, longitude: longitude)
    }
}

struct MapConfig {
    var initialRegion: Position? = nil
}
