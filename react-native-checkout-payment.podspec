require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-checkout-payment"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-checkout-payment
                   DESC
  s.homepage     = "https://github.com/wedoogift/react-native-checkout-payment"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Wedoogift" => "victor.levasseur@wedoogift.com" }
  s.platform     = :ios, "10.0"
  s.source       = { :git => "https://github.com/github_account/react-native-checkout-payment.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.swift_version = "4.2"
  s.requires_arc = true

  s.dependency "React"
  # s.dependency 'AFNetworking', '~> 3.0'

  #Checkout
  s.dependency 'Frames', '~> 2.6'
end

