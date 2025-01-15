package expo.modules.yandexmap.model

import com.yandex.mapkit.geometry.Point

data class CircleConfig(
  var center: Point? = null,
  var radius: Float? = null,
  var strokeWidth: Float? = null,
  var strokeColor: Int? = null,
  var fillColor: Int? = null,
)