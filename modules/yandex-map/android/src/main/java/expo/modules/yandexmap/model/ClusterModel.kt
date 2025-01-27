package expo.modules.yandexmap.model

import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.yandexmap.utils.ColorUtils

class ClusterStyleModel : Record {
    @Field
    var fontSize: Float? = null

    @Field
    var padding: Int? = null

    @Field
    var backgroundColor: String? = null

    @Field
    var strokeWidth: Float? = null

    @Field
    var strokeColor: String? = null

    @Field
    var textColor: String? = null

    fun toClusterStyle(): ClusterStyleData {
        val backgroundColorInt = backgroundColor?.let { ColorUtils.parseColor(it) }
        val strokeColorInt = strokeColor?.let { ColorUtils.parseColor(it) }
        val textColorInt = textColor?.let { ColorUtils.parseColor(it) }

        return ClusterStyleData(
            fontSize,
            padding,
            backgroundColorInt,
            strokeWidth,
            strokeColorInt,
            textColorInt
        )
    }
}

data class ClusterStyleData(
    var fontSize: Float? = null,
    var padding: Int? = null,
    var backgroundColor: Int? = null,
    var strokeWidth: Float? = null,
    var strokeColor: Int? = null,
    var textColor: Int? = null
)

class ClusterConfigModel : Record {
    @Field
    val clusterRadius: Double = DEFAULT_CLUSTER_RADIUS

    @Field
    val minZoom: Int = DEFAULT_CLUSTER_ZOOM

    fun toClusterConfigData(): ClusterConfig {
        return ClusterConfig(clusterRadius, minZoom)
    }
}

data class ClusterConfig(
    var clusterRadius: Double = DEFAULT_CLUSTER_RADIUS,
    var minZoom: Int = DEFAULT_CLUSTER_ZOOM,
)