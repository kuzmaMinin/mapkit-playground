package expo.modules.yandexmap.modules

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.IconRecord
import expo.modules.yandexmap.model.MarkerIconStyleModel
import expo.modules.yandexmap.model.MarkerTextStyleModel
import expo.modules.yandexmap.view.MarkerView

class MarkerModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("Marker")

        View(MarkerView::class) {
            Prop("coordinate") { view: MarkerView, coordinate: Coordinate ->
                view.setCoordinate(coordinate)
            }

            Prop("text") { view: MarkerView, text: String ->
                view.setTextValue(text)
            }

            Prop("textStyle") { view: MarkerView, style: MarkerTextStyleModel ->
                view.setTextStyleValue(style.toMarkerStyleData())
            }

            Prop("iconData") { view: MarkerView, iconRecord: IconRecord ->
                view.setIconSource(iconRecord.icon, iconRecord.animated)
            }

            Prop("iconStyle") { view: MarkerView, style: MarkerIconStyleModel ->
                view.setIconStyleValue(style.toIconStyle())
            }

            Events("onPress")
        }
    }
}