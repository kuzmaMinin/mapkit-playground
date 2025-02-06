import ExpoModulesCore
import Foundation
import YandexMapsMobile

final class ClusterView: ExpoView {
    var count: UInt = 1
    var clusterStyle = ClusterStyleModel()
    
    override func layoutSubviews() {
        setupView()
    }
    
    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)
        
        self.frame = CGRect(x: 0, y: 0, width: 100, height: 100)
        self.isOpaque = false
    }
    
    private func getTextSize() -> CGFloat {
        let helper = UILabel()
        helper.text = String(count)
        helper.font = UIFont.systemFont(ofSize: CGFloat(clusterStyle.fontSize))
        
        let textWidth = helper.intrinsicContentSize.width
        let textHeight = helper.intrinsicContentSize.height
        
        return max(textWidth, textHeight)
    }
    
    private func setupCluster(cluster: UIView, radius: CGFloat) {
        cluster.clipsToBounds = true
        cluster.backgroundColor = UIColor.fromString(clusterStyle.backgroundColor)
        cluster.layer.cornerRadius = radius
        cluster.layer.borderWidth = CGFloat(clusterStyle.strokeWidth)
        cluster.layer.borderColor = UIColor.fromString(clusterStyle.strokeColor)?.cgColor
    }
    
    private func setupLabel(_ label: UILabel) {
        label.font = UIFont.systemFont(ofSize: CGFloat(clusterStyle.fontSize))
        label.textColor = UIColor.fromString(clusterStyle.textColor)
        label.text = String(count)
        label.textAlignment = .center
    }
    
    private func setupView() {
        let dimension = getTextSize() + CGFloat(clusterStyle.padding * 2)
        let frame = CGRect(x: 0, y: 0, width: dimension, height: dimension)
        
        let cluster = UIView(frame: frame)
        let label = UILabel(frame: frame)
        
        setupCluster(cluster: cluster, radius: dimension / 2)
        setupLabel(label)
        
        cluster.addSubview(label)
        addSubview(cluster)
    }
}

