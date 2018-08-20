import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class iOSApp {

    String appName = "";

    iOSApp(String appName) {
        this.appName = appName;
    }

    void fileStructure() {

        File directory;
        // If you require it to make the entire directory path including parents, use directory.mkdirs(); here instead.

        directory = new File("ios");
        if (! directory.exists())
            directory.mkdir();

        //directory = new File("ios/MyApp"); // new File("ios/" + appName);
        directory = new File("ios/" + appName);
        if (! directory.exists())
            directory.mkdir();

        //directory = new File("ios/MyApp/Base.lproj"); // new File("ios/" + appName + "/Base.lproj");
        directory = new File("ios/" + appName + "/Base.lproj");
        if (! directory.exists())
            directory.mkdir();

    }

    void projectConfiguration() {
        //create project.yaml file having ios project configuration

        String content = "";

        //content += "name: MyProject \n"; // "name: " + appName + " \n"; //"name: MyProject \n"; // Project Name
        content += "name: " + appName + " \n"; //"name: MyProject \n"; // Project Name
        content += "options: \n";
        content += " bundleIdPrefix: edu.depaul \n";
        content += "targets: \n";
        //content += " MyApp: \n"; // appName + ": \n"; //" MyApp: \n"; // App Name
        content += " " + appName + ": \n"; //" MyApp: \n"; // App Name
        content += "  type: application \n";
        content += "  platform: iOS \n";
        //content += "  sources: [MyApp] \n"; // "  sources: [" + appName + "] \n"; //"  sources: [MyApp] \n"; // folder having source files
        content += "  sources: [" + appName + "] \n"; //"  sources: [MyApp] \n"; // folder having source files

        UseFile.writeFile("ios/project.yml", content);
    }

    void infoPList() {

        String content = "";

        content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";
        content += "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\"> \n";
        content += "<plist version=\"1.0\"> \n";
        content += "<dict> \n";
        content += "<key>CFBundleDevelopmentRegion</key> \n";
        content += "<string>$(DEVELOPMENT_LANGUAGE)</string> \n";
        content += "<key>CFBundleExecutable</key> \n";
        content += "<string>$(EXECUTABLE_NAME)</string> \n";
        content += "<key>CFBundleIdentifier</key> \n";
        content += "<string>$(PRODUCT_BUNDLE_IDENTIFIER)</string> \n";
        content += "<key>CFBundleInfoDictionaryVersion</key> \n";
        content += "<string>6.0</string> \n";
        content += "<key>CFBundleName</key> \n";
        content += "<string>$(PRODUCT_NAME)</string> \n";
        content += "<key>CFBundlePackageType</key> \n";
        content += "<string>APPL</string> \n";
        content += "<key>CFBundleShortVersionString</key> \n";
        content += "<string>1.0</string> \n";
        content += "<key>CFBundleVersion</key> \n";
        content += "<string>1</string> \n";
        content += "<key>LSRequiresIPhoneOS</key> \n";
        content += "<true/> \n";
        content += "<key>UILaunchStoryboardName</key> \n";
        content += "<string>LaunchScreen</string> \n";
        content += "<key>UIMainStoryboardFile</key> \n";
        content += "<string>Main</string> \n";
        content += "</dict> \n";
        content += "</plist> \n";

        UseFile.writeFile("ios/" + appName + "/Info.plist", content);
        //UseFile.writeFile("ios/MyApp/Info.plist", content);
    }

    void appDelegate() {
        String content = "";

        content += "import UIKit \n\n";

        content += "@UIApplicationMain \n";
        content += "class AppDelegate: UIResponder, UIApplicationDelegate { \n\n";

        content += "var window: UIWindow? \n\n";


        content += "func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool { \n";
        content += "// Override point for customization after application launch. \n";
        content += "return true \n";
        content += "} \n\n";

        content += "func applicationWillResignActive(_ application: UIApplication) { \n";
        content += "// Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state. \n";
        content += "// Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game. \n";
        content += "} \n\n";

        content += "func applicationDidEnterBackground(_ application: UIApplication) { \n";
        content += "// Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. \n";
        content += "// If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits. \n";
        content += "} \n\n";

        content += "func applicationWillEnterForeground(_ application: UIApplication) { \n";
        content += "// Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background. \n";
        content += "} \n\n";

        content += "func applicationDidBecomeActive(_ application: UIApplication) { \n";
        content += "// Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface. \n";
        content += "} \n\n";

        content += "func applicationWillTerminate(_ application: UIApplication) { \n";
        content += "// Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:. \n";
        content += "} \n\n";


        content += "} \n\n";

        UseFile.writeFile("ios/" + appName + "/AppDelegate.swift", content);
        //UseFile.writeFile("ios/MyApp/AppDelegate.swift", content);
    }

    void mainStoryboard(String label, Boolean create) {
        String content = "";

        content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";
        content += "<document type=\"com.apple.InterfaceBuilder3.CocoaTouch.Storyboard.XIB\" version=\"3.0\" toolsVersion=\"13122.16\" systemVersion=\"17A277\" targetRuntime=\"iOS.CocoaTouch\" propertyAccessControl=\"none\" useAutolayout=\"YES\" useTraitCollections=\"YES\" useSafeAreas=\"YES\" colorMatched=\"YES\" initialViewController=\"BYZ-38-t0r\"> \n";
        content += "<dependencies> \n";
        content += "<plugIn identifier=\"com.apple.InterfaceBuilder.IBCocoaTouchPlugin\" version=\"13104.12\"/> \n";
        content += "<capability name=\"Safe area layout guides\" minToolsVersion=\"9.0\"/> \n";
        content += "<capability name=\"documents saved in the Xcode 8 format\" minToolsVersion=\"8.0\"/> \n";
        content += "</dependencies> \n";
        content += "<scenes> \n";
        content += "<!--View Controller--> \n";
        content += "<scene sceneID=\"tne-QT-ifu\"> \n";
        content += "<objects> \n";
        content += "<viewController id=\"BYZ-38-t0r\" customClass=\"ViewController\" customModuleProvider=\"target\" sceneMemberID=\"viewController\"> \n";
        content += "<view key=\"view\" contentMode=\"scaleToFill\" id=\"8bC-Xf-vdC\"> \n";
        content += "<rect key=\"frame\" x=\"0.0\" y=\"0.0\" width=\"375\" height=\"667\"/> \n";
        content += "<autoresizingMask key=\"autoresizingMask\" widthSizable=\"YES\" heightSizable=\"YES\"/> \n";

        // Added label in story board if user want through storyboard
        if(create)
            content += createLabelStoryboard(label);

        content += "<color key=\"backgroundColor\" red=\"1\" green=\"1\" blue=\"1\" alpha=\"1\" colorSpace=\"custom\" customColorSpace=\"sRGB\"/> \n";
        content += "<viewLayoutGuide key=\"safeArea\" id=\"6Tk-OE-BBY\"/> \n";
        content += "</view> \n";
        content += "</viewController> \n";
        content += "<placeholder placeholderIdentifier=\"IBFirstResponder\" id=\"dkx-z0-nzr\" sceneMemberID=\"firstResponder\"/> \n";
        content += "</objects> \n";
        content += "</scene> \n";
        content += "</scenes> \n";
        content += "</document> \n";

        UseFile.writeFile("ios/" + appName + "/Base.lproj/Main.storyboard", content);
        //UseFile.writeFile("ios/MyApp/Base.lproj/Main.storyboard", content);
    }

    String createLabelProgramatically(String label) {

        String code = "\t\t let label: UILabel = UILabel() \n";
        code += "\t\t label.frame = CGRect(x: 10, y: 20, width: 250, height: 100) \n";
        code += "\t\t label.text = \"" + label + "\" \n";
        code += "\t\t self.view.addSubview(label) \n";

        return code;
    }

    String createLabelStoryboard(String label) {

        String code = "\t\t let label: UILabel = UILabel() \n";
        code += "<subviews>";
        code += "<label opaque=\"NO\" userInteractionEnabled=\"NO\" contentMode=\"left\" horizontalHuggingPriority=\"251\" verticalHuggingPriority=\"251\" fixedFrame=\"YES\" text=\"" + label + "\" textAlignment=\"natural\" lineBreakMode=\"tailTruncation\" baselineAdjustment=\"alignBaselines\" adjustsFontSizeToFit=\"NO\" translatesAutoresizingMaskIntoConstraints=\"NO\" id=\"WWx-La-CSa\">";
        code += "<rect key=\"frame\" x=\"10\" y=\"150\" width=\"250\" height=\"100\"/>";
        code += "<autoresizingMask key=\"autoresizingMask\" flexibleMaxX=\"YES\" flexibleMaxY=\"YES\"/>";
        code += "<fontDescription key=\"fontDescription\" type=\"system\" pointSize=\"17\"/>";
        code += "<nil key=\"textColor\"/>";
        code += "<nil key=\"highlightedColor\"/>";
        code += "</label>";
        code += "</subviews>";

        return code;
    }

    void viewController(String label, Boolean create) {

        String content = "";

        content += "import UIKit \n\n";

        content += "class ViewController: UIViewController { \n\n";

        content += "\t override func viewDidLoad() { \n";
        content += "\t\t super.viewDidLoad() \n";
        content += "\t\t // Do any additional setup after loading the view, typically from a nib. \n";

        // Added label in story board if user want programatically
        if(create)
            content += createLabelProgramatically(label);

        content += "\t} \n\n";
        content += "\t override func didReceiveMemoryWarning() { \n";
        content += "\t\t super.didReceiveMemoryWarning() \n";
        content += "\t\t // Dispose of any resources that can be recreated. \n";
        content += "\t} \n";
        content += "} \n";

        UseFile.writeFile("ios/" + appName + "/ViewController.swift", content);
        //UseFile.writeFile("ios/MyApp/ViewController.swift", content);
    }

    void iosProject() {

        try {
            //Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "cd ios; xcodegen; ls -l"});
            Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "cd ios; "});
            //p.waitFor();

            /*
            String line = null;
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while((line = input.readLine()) != null) {

                System.out.println(line);
            }

            if (p.waitFor() == 0) {
                System.out.println("Success!");
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
