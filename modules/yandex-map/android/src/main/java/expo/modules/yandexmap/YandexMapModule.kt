package expo.modules.yandexmap

import android.view.View
import android.view.ViewGroup
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.view.MarkerView
import expo.modules.yandexmap.view.YandexMapView

class MarkerModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("Marker")

    View(MarkerView::class) {
      Prop("coordinate") { view: MarkerView, coordinate: Coordinate ->
        view.setCoordinate(coordinate)
      }

      Prop("text") { view: MarkerView, text: String ->
        view.setText(text)
      }

      Prop("icon") { view: MarkerView, icon: String? ->
        view.setIconSource(icon)
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
