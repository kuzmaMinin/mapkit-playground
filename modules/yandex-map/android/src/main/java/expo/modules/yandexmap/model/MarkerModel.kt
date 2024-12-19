package expo.modules.yandexmap.model

import android.graphics.Color
import android.graphics.PointF
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.Rect
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.map.TextStyle
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

class MarkerTextStyle(
  val size: Float? = null,
  val color: Int? = null,
  val outlineWidth: Float? = null,
  val outlineColor: Int? = null,
  val placement: TextStyle.Placement? = null,
  val offset: Float? = null,
  val offsetFromIcon: Boolean? = null,
  val textOptional: Boolean? = null
) {
  companion object {
    fun fromMap(map: Map<String, Any?>): TextStyle {
      return TextStyle().apply {
        size = (map["size"] as? Number)?.toFloat() ?: 10.0F
        color = (map["color"] as? String)?.let { parseColor(it) }
        outlineWidth = (map["outlineWidth"] as? Number)?.toFloat() ?: 1.0F
        outlineColor = (map["outlineColor"] as? String)?.let { parseColor(it) }
        placement = (map["placement"] as? Number)?.let { TextStyle.Placement.entries[it.toInt()] }
          ?: TextStyle.Placement.CENTER
        offset = (map["offset"] as? Number)?.toFloat() ?: 0.0F
        offsetFromIcon = map["offsetFromIcon"] as? Boolean ?: true
        textOptional = map["textOptional"] as? Boolean ?: false
      }
    }

    private fun parseColor(colorString: String): Int? {
      return try {
        Color.parseColor(colorString)
      } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
      }
    }
  }
}

class MarkerIconStyle(
  anchor: PointF? = null,
  rotationType: RotationType? = null,
  zIndex: Float? = null,
  flat: Boolean? = null,
  visible: Boolean? = null,
  scale: Float? = null,
  tappableArea: Rect? = null
) {
  companion object {
    fun fromMap(map: Map<String, Any?>): IconStyle {
      return IconStyle().apply {
        anchor = (map["anchor"] as? Map<String, Any?>)?.let {
          parsePointF(it, 0.5f)
        }
        rotationType = (map["rotationType"] as? Number)?.let { RotationType.entries[it.toInt()] }
        zIndex = (map["zIndex"] as? Number)?.toFloat() ?: 0.0F
        flat = map["flat"] as? Boolean ?: false
        visible = map["visible"] as? Boolean ?: true
        scale = (map["scale"] as? Number)?.toFloat() ?: 1.0F
        tappableArea = (map["tappableArea"] as? Map<String, Any?>)?.let {
          parseRect(map)
        }
      }
    }

    private fun parsePointF(map: Map<String, Any?>, default: Float = 1.0f): PointF {
      val x = (map["x"] as? Number)?.toFloat() ?: default
      val y = (map["y"] as? Number)?.toFloat() ?: default

      return PointF(x, y)
    }

    private fun parseRect(map: Map<String, Any?>): Rect {
      val min = (map["min"] as? Map<String, Any?>)?.let {
        parsePointF(it, 10.0f)
      } ?: PointF(10.0f, 10.0f)

      val max = (map["max"] as? Map<String, Any?>)?.let {
        parsePointF(it, 10.0f)
      } ?: PointF(10.0f, 10.0f)

      return Rect(min, max)
    }
  }
}