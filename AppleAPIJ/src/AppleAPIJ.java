
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class AppleAPIJ {

    private String xmlFile, yamlFile;

    public static void main(String[] args) throws IOException {

        AppleAPIJ androidAPIJ = new AppleAPIJ();
        Document doc;

        String [] widgets = {
                "UIButton",
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

        String path;

        for(String widget: widgets) {

            path = "html/" + widget.toLowerCase() + ".html";

            String content = UseFile.readFile(path);
            doc = Jsoup.parse(content);
            //doc = Jsoup.connect(url).get(); //data data from url directly.

            androidAPIJ.fetchData(doc);
        }
    }

    private void fetchData(Document doc) {

        className(doc);
        System.out.println("Started to Create file: " + xmlFile);

        description(doc);
        System.out.print("Added Description, ");

        overview(doc);
        System.out.print("Added Overview, ");

        methods(doc);
        System.out.print("Added Methods, ");

        relationships(doc);
        System.out.print("Added Relationships, ");

        seeAlso(doc);
        System.out.print("Added See Also, ");

        endTag();

        System.out.println("\n -------------- \n");
    }

    private void className(Document doc)  {

        String title = doc.selectFirst("H1").text();

        xmlFile = "xml/" + title + ".xml";
        UseFile.createDirectoryFile(xmlFile);

        String xml = "<api>";
        xml += space(1) + "<title>" + title + "</title>";
        UseFile.writeFile(xmlFile, xml);

        // for yaml file
        yamlFile = "yaml/" + title + ".yaml";
        UseFile.createDirectoryFile(yamlFile);

        String yaml = "api:";
        yaml += space(1) + "title: " + title;
        UseFile.writeFile(yamlFile, yaml);
    }

    private void endTag() {

        UseFile.writeFile(xmlFile, space(1) + "</api>");
        //System.out.println("Created: " + xmlFile);
    }

    private void description(Document doc) {

        String description = doc.selectFirst(".topic-description").text(); // first <p> in <div id="jd-content">

        String xml = space(1) + "<description>" + fixInvalidXML(description, true) + "</description>";

        String yaml = space(1) + "description: " + fixInvalidXML(description, false);

        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void overview(Document doc) {

        //String overview = doc.selectFirst("#overview").text(); // first <p> in <div id="jd-content">

        Element overview = doc.selectFirst("section#overview");

        String xml = space(1) + "<overview>";
        String yaml = space(1) + "overview: ";
        boolean indexFirst = true;

        if(!overview.select("p").isEmpty()) {
            String description = overview.selectFirst("p").text();

            xml += space(1) + "<description>" + fixInvalidXML(description, true) + "</description>";

            yaml += space(2) + "description: " + fixInvalidXML(description, false);
        }

        Elements sub_overviews = overview.select("h3");

        for (Element sub_overview: sub_overviews) {

            xml += space(1) + "<sub_overview>";
            xml += space(1) + "<name>" + fixInvalidXML(sub_overview.text(), true) + "</name>";

            if(indexFirst) {
                yaml += space(2) + "sub_overview: ";
                indexFirst = false;
            }

            if(sub_overviews.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(sub_overview.text(), false);

            if(!overview.select(sub_overview.cssSelector() + " + div").isEmpty()) {
                String description = overview.select(sub_overview.cssSelector() + " + div").text();

                xml += space(1) + "<description>" + fixInvalidXML(description, true) + "</description>";

                yaml += space(4) + "description: " + fixInvalidXML(description, false).replace("\n", " ");
            }

            xml += space(1) + "</sub_overview>";;
        }

        xml += space(1) + "</overview>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void methods(Document doc) {

        Elements topics = doc.select("section#topics .contenttable-section");

        String xml = space(1) + "<topics>";
        String yaml = space(1) + "topics: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element topic : topics) {

            String title = topic.selectFirst("h3.contenttable-section-title").text();

            xml += space(1) + "<topic>";
            xml += space(1) + "<title>" + fixInvalidXML(title, true) + "</title>";

            if(indexFirst) {
                yaml += space(2) + "topic: ";
                indexFirst = false;
            }

            if(topics.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "title: " + fixInvalidXML(title, false);

            Elements methods = topic.select(".task-topic-info");
            indexSecond = true;

            for (Element method: methods) {

                xml += space(1) + "<method>";

                if(indexSecond) {
                    yaml += space(5) + "method: ";
                    indexSecond = false;
                }

                if(methods.size() > 1)
                    yaml += space(6) + "- ";

                if(!method.select("a").isEmpty()) {
                    String name = method.selectFirst("a").text();

                    xml += space(1) + "<name>" + fixInvalidXML(name, true) + "</name>";

                    yaml += space(7) + "name: " + fixInvalidXML(name, false);
                }

                if(!method.select(".task-topic-abstract").isEmpty()) {
                    String description = method.selectFirst(".task-topic-abstract").text();

                    xml += space(1) + "<description>" + fixInvalidXML(description, true) + "</description>";

                    yaml += space(7) + "description: " + fixInvalidXML(description, false);
                }

                xml += space(1) + "</method>";
            }
            xml += space(1) + "</topic>";
        }

        xml += space(1) + "</topics>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void relationships(Document doc) {

        Elements sections = doc.select("section#relationships section");

        String xml = space(1) + "<relationships>";
        String yaml = space(1) + "relationships: ";

        for (Element section : sections) {

            if(!section.select("h3.contenttable-section-title").isEmpty()) {

                String relation = section.selectFirst("h3.contenttable-section-title").text();
                relation =  relation.trim().toLowerCase().replace(" ", "_");

                xml += space(1) + "<" + relation + ">";

                yaml += space(2) + relation + ": ";

                if(sections.size() > 1)
                    yaml += space(3) + "- ";

                Elements names = section.select("a");

                for (Element name: names) {

                    xml += space(1) + "<name>" + fixInvalidXML(name.text(), true) + "</name>";

                    if(names.size() > 1)
                        yaml += space(4) + "- ";

                    yaml += space(5) + "name: " + fixInvalidXML(name.text(), false);
                }

                xml += space(1) + "</" + relation + ">";
            }
        }

        xml += space(1) + "</relationships>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void seeAlso(Document doc) {

        Elements controls = doc.select("section#see-also .task-topic-info");

        String xml = space(1) + "<see-also>";
        String yaml = space(1) + "see-also: ";
        boolean indexFirst = true;

        for (Element control : controls) {

            xml += space(1) + "<control>";

            if(indexFirst) {
                yaml += space(2) + "control: ";
                indexFirst = false;
            }

            if(controls.size() > 1)
                yaml += space(3) + "- ";


            if(!control.select("a").isEmpty()) {
                String name = control.selectFirst(".task-topic-info a").text();

                xml += space(1) + "<name>" + fixInvalidXML(name, true) + "</name>";

                yaml += space(4) + "name: " + fixInvalidXML(name, false);
            }

            if(!control.select(".task-topic-abstract").isEmpty()) {
                String description = control.selectFirst(".task-topic-info .task-topic-abstract").text();

                xml += space(1) + "<description>" + fixInvalidXML(description, true) + "</description>";

                yaml += space(4) + "description: " + fixInvalidXML(description, false);
            }

            xml += space(1) + "</control>";
        }

        xml += space(1) + "</see-also>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private String fixInvalidXML(String data, boolean fix) {

        data = data.replace("<", "&lt;");
        data = data.replace(">", "&gt;");
        data = data.replace("'", "&apos;");
        data = data.replace("\"", "&quot;");
        data = data.replace("&", "&amp;");

        return data;
    }

    private String space(int total) {

        String spaces = "\n";

        for(int i = 0; i < total; i++) {
            spaces = spaces + " ";
        }

        return spaces;
    }

}
