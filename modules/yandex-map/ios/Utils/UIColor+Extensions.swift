import UIKit

extension UIColor {
    static func fromString(_ colorString: String) -> UIColor? {
        if let namedColor = UIColor(named: colorString) {
            return namedColor
        }
        
        if colorString.hasPrefix("#") {
            let hex = String(colorString.dropFirst())
            
            var rgbValue: UInt64 = 0
            let scanner = Scanner(string: hex)
            scanner.scanHexInt64(&rgbValue)
            
            switch hex.count {
            case 6:
                return UIColor(
                    red: CGFloat((rgbValue >> 16) & 0xFF) / 255.0,
                    green: CGFloat((rgbValue >> 8) & 0xFF) / 255.0,
                    blue: CGFloat(rgbValue & 0xFF) / 255.0,
                    alpha: 1.0
                )
            case 8:
                return UIColor(
                    red: CGFloat((rgbValue >> 24) & 0xFF) / 255.0,
                    green: CGFloat((rgbValue >> 16) & 0xFF) / 255.0,
                    blue: CGFloat((rgbValue >> 8) & 0xFF) / 255.0,
                    alpha: CGFloat(rgbValue & 0xFF) / 255.0
                )
            default:
                return nil
            }
        }
        
        return nil
    }
}

