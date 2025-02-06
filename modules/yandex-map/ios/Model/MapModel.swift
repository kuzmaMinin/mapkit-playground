import ExpoModulesCore
import YandexMapsMobile

struct Position: Record {
    @Field
    var latitude: Double = 0.0
    
    @Field
    var longitude: Double = 0.0
    
    @Field
    var zoom: Float? = nil
    
    @Field
    var azimuth: Float? = nil
    
    @Field
    var tilt: Float? = nil
    
    func toPoint() -> YMKPoint {
        return YMKPoint(latitude: latitude, longitude: longitude)
    }
}

struct MapConfig {
    var initialRegion: Position? = nil
    var clusterized: Bool = false
}
