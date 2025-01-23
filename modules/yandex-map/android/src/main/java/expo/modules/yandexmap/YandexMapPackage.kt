package expo.modules.yandexmap

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import expo.modules.core.interfaces.ApplicationLifecycleListener
import expo.modules.core.interfaces.Package
import expo.modules.core.interfaces.ReactActivityLifecycleListener
import expo.modules.yandexmap.model.ERROR_TAG
import expo.modules.yandexmap.model.INFO_TAG
import expo.modules.yandexmap.model.MAPKIT_API_KEY

class YandexMapPackage : Package {
    override fun createApplicationLifecycleListeners(context: Context): List<ApplicationLifecycleListener> {
        return listOf(object : ApplicationLifecycleListener {
            override fun onCreate(application: Application) {
                super.onCreate(application)

                try {
                    val applicationInfo =
                        context.packageManager?.getApplicationInfo(context.packageName, 128)
                    val apiKey = applicationInfo?.metaData?.getString(MAPKIT_API_KEY)
                    // TODO: move text to resource
                    apiKey?.let {
                        MapKitFactory.setApiKey(it)
                        // TODO: move text to resource
                    } ?: Log.v(INFO_TAG, "API Key not found in AndroidManifest.")
                } catch (e: Exception) {
                    Log.e(ERROR_TAG, "Error in OnCreate ApplicationLifecycleListener ${e.message}.")
                }
            }
        })
    }

    override fun createReactActivityLifecycleListeners(activityContext: Context): List<ReactActivityLifecycleListener> {
        return listOf(object : ReactActivityLifecycleListener {
            override fun onCreate(activity: Activity, savedInstanceState: Bundle?) {
                super.onCreate(activity, savedInstanceState)

                try {
                    MapKitFactory.initialize(activityContext)
                } catch (e: Exception) {
                    Log.e(
                        ERROR_TAG,
                        "Error in OnCreate ReactActivityLifecycleListener ${e.message}."
                    )
                    // TODO: move text to resource
                }
            }

            override fun onDestroy(activity: Activity) {
                MapKitFactory.getInstance().onStop()
                MapView(activityContext).onStop()

                super.onDestroy(activity)
            }
        })
    }
}
