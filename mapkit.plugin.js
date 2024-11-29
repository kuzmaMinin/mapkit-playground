const Config = require('expo/config-plugins');

const AndroidConfig = Config.AndroidConfig
const withAndroidManifest = Config.withAndroidManifest;


const withMapKitApiKey = (config, { apiKey }) => {
    config = withAndroidManifest(config, config => {
        const mainApplication = AndroidConfig.Manifest.getMainApplicationOrThrow(config.modResults);

        AndroidConfig.Manifest.addMetaDataItemToMainApplication(
            mainApplication,
            'MAPKIT_API_KEY',
            apiKey
        );
        return config;
    });

    return config;
};

module.exports = withMapKitApiKey;