package expo.modules.yandexmap.model

import com.yandex.mapkit.geometry.Point

data class PolylineConfig(
  var points: List<Point>? = null,
  var strokeWidth: Float? = null,
  var strokeColor: Int? = null,
  var outlineWidth: Float? = null,
  var outlineColor: Int? = null,
)