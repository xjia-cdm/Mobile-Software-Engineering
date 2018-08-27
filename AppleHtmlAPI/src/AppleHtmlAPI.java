import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


public class AppleHtmlAPI {

    public static void main(String[] args) throws IOException {

        AppleHtmlAPI androidAPIJ = new AppleHtmlAPI();
        Document doc;

        String [] widgets = {
                "UIView",
                "UIStackView",
                "UIScrollView",
                "UIActivityIndicatorView",
                "UIImageView",
                "UIPickerView",
                "UIProgressView",
                "UIWebView",
                "UIControl",
                "UIButton",
                "UIDatePicker",
                "UIPageControl",
                "UISegmentedControl",
                "UISlider",
                "UIStepper",
                "UISwitch",
                "UILabel",
                "UITextField",
                "UITextView",
                "UIVisualEffect",
                "UIVisualEffectView",
                "UIVibrancyEffect",
                "UIBlurEffect",
                "UIBarItem",
                "UIBarButtonItem",
                "UIBarButtonItemGroup",
                "UINavigationBar",
                "UISearchBar",
                "UIToolbar",
                "UITabBar",
                "UITabBarItem"
        };

        String url;
        System.out.println("Downloading Start: ");
        System.out.println("-----------");

        for(String widget: widgets) {

            url = "https://developer.apple.com/documentation/uikit/" + widget.toLowerCase();
            doc = Jsoup.connect(url).get();
            androidAPIJ.saveData(widget.toLowerCase(), doc.outerHtml());
            System.out.println(widget.toLowerCase());
        }
        System.out.println("-----------");
        System.out.println("Downloading Finish");
    }

    private void saveData(String widget, String data) {

        String htmlFile = "html/" + widget + ".html";

        UseFile.createDirectoryFile(htmlFile);

        UseFile.writeFile(htmlFile, data);
    }
}