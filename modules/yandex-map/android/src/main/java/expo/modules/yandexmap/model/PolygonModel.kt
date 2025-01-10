package expo.modules.yandexmap.model

import com.yandex.mapkit.geometry.Point

data class PolygonConfig(
  var points: List<Point>? = null,
  var innerPoints: List<Point>? = null,
  var strokeWidth: Float? = null,
  var strokeColor: Int? = null,
  var fillColor: Int? = null
)