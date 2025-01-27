package expo.modules.yandexmap.model

import com.yandex.mapkit.geometry.Point
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record

class Position : Record {
    @Field
    val latitude: Double = 0.0

    @Field
    val longitude: Double = 0.0

    @Field
    val zoom: Float? = null

    @Field
    val azimuth: Float? = null

    @Field
    val tilt: Float? = null

    fun toPoint(): Point {
        return Point(latitude, longitude)
    }
}

class FocusRect : Record {
    @Field
    val topLeft: CoordinateXY = CoordinateXY()

    @Field
    val bottomRight: CoordinateXY = CoordinateXY()
}

class CoordinateXY : Record {
    @Field
    val x: Float = 0f

    @Field
    val y: Float = 0f
}

data class MapConfig(
    var clusterized: Boolean = false,
    var clusterStyle: ClusterStyleData = ClusterStyleData(),
    var initialRegion: Position? = null
)