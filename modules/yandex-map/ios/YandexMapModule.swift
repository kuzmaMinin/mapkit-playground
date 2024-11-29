import ExpoModulesCore

public class YandexMapModule: Module {
  public func definition() -> ModuleDefinition {
    Name("YandexMap")

    View(YandexMapView.self) {}
  }
}
