import ExpoModulesCore
import YandexMapsMobile

struct ClusterStyleModel: Record {
    @Field
    var fontSize: Float? = nil
    
    @Field
    var padding: Int? = nil
    
    @Field
    var backgroundColor: String? = nil
    
    @Field
    var strokeWidth: Float? = nil
    
    @Field
    var strokeColor: String? = nil
    
    @Field
    var textColor: String? = nil
}

struct ClusterConfigModel: Record {
    @Field
    var clusterRadius: Double = 60
    
    @Field
    var minZoom: Double = 15
}

