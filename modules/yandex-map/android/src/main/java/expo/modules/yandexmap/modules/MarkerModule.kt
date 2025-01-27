package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.MarkerIconStyleModel
import expo.modules.yandexmap.view.MarkerView

class MarkerModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("Marker")

        View(MarkerView::class) {
            Prop("coordinate") { view: MarkerView, coordinate: Coordinate ->
                view.setCoordinate(coordinate)
            }

            Prop("iconStyle") { view: MarkerView, style: MarkerIconStyleModel ->
                view.setIconStyleValue(style.toIconStyle())
            }

            Events("onPress")
        }
    }
}