package expo.modules.yandexmap.model

import expo.modules.yandexmap.utils.ColorUtils

class ClusterStyle(
    var fontSize: Float? = null,
    var padding: Int? = null,
    var backgroundColor: Int? = null,
    var strokeWidth: Float? = null,
    var strokeColor: Int? = null,
    var textColor: Int? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): ClusterStyle {
            return ClusterStyle().apply {
                fontSize = (map["fontSize"] as? Double)?.toFloat()
                padding = (map["padding"] as? Double)?.toInt()
                backgroundColor = (map["backgroundColor"] as? String)?.let { ColorUtils.parseColor(it) }
                strokeWidth = (map["strokeWidth"] as? Number)?.toFloat()
                textColor = (map["textColor"] as? String)?.let { ColorUtils.parseColor(it) }
                strokeColor = (map["strokeColor"] as? String)?.let { ColorUtils.parseColor(it) }
            }
        }
    }
}

class ClusterConfig(
    var clusterRadius: Double = DEFAULT_CLUSTER_RADIUS,
    var minZoom: Int = DEFAULT_CLUSTER_ZOOM,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): ClusterConfig {
            return ClusterConfig().apply {
                clusterRadius = (map["clusterRadius"] as? Double) ?: DEFAULT_CLUSTER_RADIUS
                minZoom = (map["minZoom"] as? Double)?.toInt() ?: DEFAULT_CLUSTER_ZOOM
            }
        }
    }
}