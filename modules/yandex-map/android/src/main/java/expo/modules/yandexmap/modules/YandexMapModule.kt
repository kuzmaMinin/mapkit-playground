package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.view.YandexMapView

class YandexMapModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView::class) {
      Events("onMapReady")
    }
  }
}
