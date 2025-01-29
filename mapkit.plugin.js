const Config = require("expo/config-plugins");

const AndroidConfig = Config.AndroidConfig;
const withAndroidManifest = Config.withAndroidManifest;
const withInfoPlist = Config.withInfoPlist;

const MAPKIT_API_KEY = "MAPKIT_API_KEY";

const withMapKitApiKey = (config, { apiKey }) => {
  config = withInfoPlist(config, (config) => {
    config.modResults[MAPKIT_API_KEY] = apiKey;

    return config;
  });

  config = withAndroidManifest(config, (config) => {
    const mainApplication = AndroidConfig.Manifest.getMainApplicationOrThrow(
      config.modResults,
    );

    AndroidConfig.Manifest.addMetaDataItemToMainApplication(
      mainApplication,
      MAPKIT_API_KEY,
      apiKey,
    );
    return config;
  });

  return config;
};

module.exports = withMapKitApiKey;
