package expo.modules.yandexmap

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.MarkerIconStyle
import expo.modules.yandexmap.model.MarkerTextStyle
import expo.modules.yandexmap.view.MarkerView
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

      Prop("icon") { view: MarkerView, icon: String? ->
        view.setIconSource(icon)
      }

      Prop("iconStyle") { view: MarkerView, map: Map<String, Any?> ->
        val style = MarkerIconStyle.fromMap(map)

        view.setIconStyleValue(style)
      }

      Prop("animated") { view: MarkerView, animated: Boolean ->
        view.setAnimatedValue(animated)
      }
    }
  }
}

class YandexMapModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView::class) {}
  }
}
