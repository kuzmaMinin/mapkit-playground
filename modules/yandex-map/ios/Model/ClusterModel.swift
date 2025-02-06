import ExpoModulesCore
import YandexMapsMobile

struct ClusterStyleModel: Record {
    @Field
    var fontSize: Float = DEFAULT_CLUSTER_FONT_SIZE
    
    @Field
    var padding: Int = DEFAULT_CLUSTER_PADDING
    
    @Field
    var backgroundColor: String = DEFAULT_CLUSTER_BACKGROUND
    
    @Field
    var strokeWidth: Float = DEFAULT_CLUSTER_STROKE_WIDTH
    
    @Field
    var strokeColor: String = DEFAULT_CLUSTER_STROKE_COLOR
    
    @Field
    var textColor: String = DEFAULT_CLUSTER_TEXT_COLOR
}

struct ClusterConfigModel: Record {
    @Field
    var clusterRadius: Double = DEFAULT_CLUSTER_RADIUS
    
    @Field
    var minZoom: Double = DEFAULT_CLUSTER_ZOOM
}

