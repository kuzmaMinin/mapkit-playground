Pod::Spec.new do |s|
  s.name           = 'YandexMap'
  s.version        = '1.0.0'
  s.summary        = 'Wrapper for Yandex MapKit'
  s.description    = 'Wrapper for Yandex MapKit'
  s.author         = 'Oleg Kuzmin'
  s.homepage       = 'https://github.com/kuzmaMinin'
  s.platforms      = {
    :ios => '15.1'
  }
  s.source         = { git: '' }
  s.static_framework = true

  s.dependency 'ExpoModulesCore'
  s.dependency 'YandexMapsMobile', '4.10.1-lite'

  # Swift/Objective-C compatibility
  s.pod_target_xcconfig = {
    'DEFINES_MODULE' => 'YES',
  }

  s.source_files = "**/*.{h,m,mm,swift,hpp,cpp}"
end
