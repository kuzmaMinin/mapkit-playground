package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.ClusterConfig
import expo.modules.yandexmap.model.ClusterStyle
import expo.modules.yandexmap.view.YandexMapView

class YandexMapModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView::class) {
      Events("onMapReady", "onClusterPress")

      Prop("clusterized") { view: YandexMapView, clusterized: Boolean? ->
        view.setClusterizedValue(clusterized)
      }

      Prop("clusterStyle") { view: YandexMapView, map: Map<String, Any?> ->
          val clusterStyle = ClusterStyle.fromMap(map)

          view.setClusterStyleValue(clusterStyle)
      }

      Prop("clusterConfig") { view: YandexMapView, map: Map<String, Any?> ->
        val clusterConfig = ClusterConfig.fromMap(map)

        view.setClusterConfigValue(clusterConfig)
      }
    }
  }
}
