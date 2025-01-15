package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.view.PolylineView

class PolylineModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("Polyline")

    View(PolylineView::class) {
      Prop("points") { view: PolylineView, points: List<Coordinate> ->
        view.setPointsValue(points)
      }

      Prop("strokeColor") { view: PolylineView, color: String? ->
        view.setStrokeColorValue(color)
      }

      Prop("strokeWidth") { view: PolylineView, width: Double? ->
        view.setStrokeWidthValue(width)
      }

      Prop("outlineColor") { view: PolylineView, color: String? ->
        view.setOutlineColorValue(color)
      }

      Prop("outlineWidth") { view: PolylineView, width: Double? ->
        view.setOutlineWidthValue(width)
      }
    }
  }
}