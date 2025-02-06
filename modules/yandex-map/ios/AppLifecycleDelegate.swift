import ExpoModulesCore
import YandexMapsMobile

public class AppLifecycleDelegate: ExpoAppDelegateSubscriber {
        public func application(
            _ application: UIApplication,
            didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
        ) -> Bool {
            guard let key = Bundle.main.object(forInfoDictionaryKey: MAPKIT_API_KEY) as? String else {
                return false
            }
            
            YMKMapKit.setApiKey(key)
            YMKMapKit.sharedInstance()
            
            return true
        }
}

