//
//  ViewController.swift
//  NettruyenIOS
//
//  Created by admin on 14/07/2022.
//

import UIKit
import Flutter

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        let button = UIButton(type:UIButton.ButtonType.custom)
          button.addTarget(self, action: #selector(showFlutter), for: .touchUpInside)
          button.setTitle("Show Flutter!", for: UIControl.State.normal)
        button.frame = CGRect(x:(view.frame.size.width - 180) / 2, y: 210.0, width: 180.0 , height: 40.0)
          button.backgroundColor = UIColor.blue
          self.view.addSubview(button)
    }

@objc func showFlutter() {
    let flutterEngine = (UIApplication.shared.delegate as! AppDelegate).flutterEngine
    let flutterViewController =
        FlutterViewController(engine: flutterEngine, nibName: nil, bundle: nil)
    present(flutterViewController, animated: true)
  }


}

