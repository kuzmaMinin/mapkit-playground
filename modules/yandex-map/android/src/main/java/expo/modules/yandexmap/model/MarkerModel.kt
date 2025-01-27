package expo.modules.yandexmap.model

import android.graphics.PointF
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.Rect
import com.yandex.mapkit.map.RotationType
import com.yandex.runtime.image.ImageProvider
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record

class Coordinate : Record {
    @Field
    val latitude: Double = 0.0

    @Field
    val longitude: Double = 0.0

    fun toPoint(): Point {
        return Point(latitude, longitude)
    }
}

class MarkerIconStyleModel : Record {
    @Field
    val anchor: PointXY? = null

    @Field
    val rotationType: Int? = null

    @Field
    val zIndex: Float = 0.0F

    @Field
    val flat: Boolean = false

    @Field
    val visible: Boolean = true

    @Field
    val scale: Float = 1.0F

    @Field
    val tappableArea: RectRecord? = null

    fun toIconStyle(): IconStyle {
        val rotationTypeEntry = rotationType?.let { RotationType.entries[it] }
        val tappableAreaEntry = tappableArea?.toRect()

        return IconStyle(
            PointF(anchor?.x ?: 1.0f, anchor?.y ?: 1.0f),
            rotationTypeEntry,
            zIndex,
            flat,
            visible,
            scale,
            tappableAreaEntry
        )
    }
}

class PointXY : Record {
    @Field
    var x: Float = 1.0f

    @Field
    var y: Float = 1.0f
}

class RectRecord : Record {
    @Field
    val min: PointXY? = null

    @Field
    val max: PointXY? = null

    fun toRect(): Rect {
        val minPoint = PointF(min?.x ?: 10.0f, min?.y ?: 10.0f)
        val maxPoint = PointF(max?.x ?: 10.0f, max?.y ?: 10.0f)

        return Rect(minPoint, maxPoint)
    }
}

data class PlacemarkConfig(
    var coordinate: Point = Point(0.0, 0.0),
    var iconStyle: IconStyle = IconStyle(),
    var imageProvider: ImageProvider? = null,
)

