package expo.modules.yandexmap

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.MarkerIconStyle
import expo.modules.yandexmap.model.MarkerTextStyle
import expo.modules.yandexmap.view.MarkerView
import expo.modules.yandexmap.view.PolygonView
import expo.modules.yandexmap.view.YandexMapView

class MarkerModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("Marker")

    View(MarkerView::class) {
      Prop("coordinate") { view: MarkerView, coordinate: Coordinate ->
        view.setCoordinate(coordinate)
      }

      Prop("text") { view: MarkerView, text: String? ->
        view.setTextValue(text)
      }

      Prop("textStyle") { view: MarkerView, map: Map<String, Any?> ->
        val style = MarkerTextStyle.fromMap(map)

        view.setTextStyleValue(style)
      }

      Prop("iconData") { view: MarkerView, map: Map<String, Any?> ->
        val icon = map["icon"] as? String?
        val animated = map["animated"] as? Boolean ?: false

        view.setIconSource(icon, animated)
      }

      Prop("iconStyle") { view: MarkerView, map: Map<String, Any?> ->
        val style = MarkerIconStyle.fromMap(map)

        view.setIconStyleValue(style)
      }

      Events("onPress")
    }
  }
}

class PolygonModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("Polygon")

    View(PolygonView::class) {
      Prop("points") { view: PolygonView, points: List<Coordinate> ->
        view.setPointsValue(points)
      }

      Prop("innerPoints") { view: PolygonView, points: List<Coordinate>? ->
        view.setInnerPointsValue(points)
      }

      Prop("strokeColor") { view: PolygonView, color: String? ->
        view.setStrokeColorValue(color)
      }

      Prop("fillColor") { view: PolygonView, color: String? ->
        view.setFillColorValue(color)
      }

      Prop("strokeWidth") { view: PolygonView, width: Double? ->
       view.setStrokeWidthValue(width)
      }
    }
  }
}

class YandexMapModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView::class) {
      Events("onMapReady")
    }
  }
}
