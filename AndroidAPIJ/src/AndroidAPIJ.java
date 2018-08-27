
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AndroidAPIJ {

    private String xmlFile, yamlFile;

    public static void main(String[] args) throws IOException {

        AndroidAPIJ androidAPIJ = new AndroidAPIJ();
        Document doc;

        String [] widgets = {
                "Button",
                "AbsListView",
                "AbsListView.LayoutParams",
                "AbsoluteLayout",
                "AbsoluteLayout.LayoutParams",
                "AbsSeekBar",
                "AbsSpinner",
                "ActionMenuView",
                "ActionMenuView.LayoutParams",
                "AdapterView",
                "AdapterView.AdapterContextMenuInfo",
                "AdapterViewAnimator",
                "AdapterViewFlipper",
                "AlphabetIndexer",
                "AnalogClock",
                "ArrayAdapter",
                "AutoCompleteTextView",
                "BaseAdapter",
                "BaseExpandableListAdapter",
                "Button",
                "CalendarView",
                "CheckBox",
                "CheckedTextView",
                "Chronometer",
                "CompoundButton",
                "CursorAdapter",
                "CursorTreeAdapter",
                "DatePicker",
                "DialerFilter",
                "DigitalClock",
                "EdgeEffect",
                "EditText",
                "ExpandableListView",
                "ExpandableListView.ExpandableListContextMenuInfo",
                "Filter",
                "Filter.FilterResults",
                "FrameLayout",
                "FrameLayout.LayoutParams",
                "Gallery",
                "Gallery.LayoutParams",
                "GridLayout",
                "GridLayout.Alignment",
                "GridLayout.LayoutParams",
                "GridLayout.Spec",
                "GridView",
                "HeaderViewListAdapter",
                "HorizontalScrollView",
                "ImageButton",
                "ImageSwitcher",
                "ImageView",
                "LinearLayout",
                "LinearLayout.LayoutParams",
                "ListPopupWindow",
                "ListView",
                "ListView.FixedViewInfo",
                "Magnifier",
                "MediaController",
                "MultiAutoCompleteTextView",
                "MultiAutoCompleteTextView.CommaTokenizer",
                "NumberPicker",
                "OverScroller",
                "PopupMenu",
                "PopupWindow",
                "ProgressBar",
                "QuickContactBadge",
                "RadioButton",
                "RadioGroup",
                "RadioGroup.LayoutParams",
                "RatingBar",
                "RelativeLayout",
                "RelativeLayout.LayoutParams",
                "RemoteViews",
                "RemoteViewsService",
                "ResourceCursorAdapter",
                "ResourceCursorTreeAdapter",
                "Scroller",
                "ScrollView",
                "SearchView",
                "SeekBar",
                "ShareActionProvider",
                "SimpleAdapter",
                "SimpleCursorAdapter",
                "SimpleCursorTreeAdapter",
                "SimpleExpandableListAdapter",
                "SlidingDrawer",
                "Space",
                "Spinner",
                "StackView",
                "Switch",
                "TabHost",
                "TabHost.TabSpec",
                "TableLayout",
                "TableLayout.LayoutParams",
                "TableRow",
                "TableRow.LayoutParams",
                "TabWidget",
                "TextClock",
                "TextSwitcher",
                "TextView",
                "TextView.SavedState",
                "TimePicker",
                "Toast",
                "ToggleButton",
                "Toolbar",
                "Toolbar.LayoutParams",
                "TwoLineListItem",
                "VideoView",
                "ViewAnimator",
                "ViewFlipper",
                "ViewSwitcher",
                "ZoomButton",
                "ZoomButtonsController",
                "ZoomControls"
        };

        String path;

        for(String widget: widgets) {

            path = "html/" + widget + ".html";

            String content = UseFile.readFile(path);
            doc = Jsoup.parse(content);
            //doc = Jsoup.connect(url).get(); //data data from url directly.

            androidAPIJ.fetchData(doc);
        }

    }

    private void fetchData(Document doc) {

        className(doc);
        System.out.println("Started to Create file: " + xmlFile);

        apiSignature(doc);
        System.out.print("Added API Signature, ");

        inheritance(doc);
        System.out.print("Inheritance, ");

        subclasses(doc, "subclasses-direct");
        System.out.print("Subclasses direct, ");

        subclasses_summary(doc, "subclasses-direct-summary");
        System.out.print("Subclasses direct summary, ");

        subclasses(doc, "subclasses-indirect");
        System.out.print("Subclasses indirect, ");

        subclasses_summary(doc, "subclasses-indirect-summary");
        System.out.print("subclasses indirect summary, ");

        inherited_xml_attributes(doc);
        System.out.print(" Interited xml attribute, ");

        inherited_constants(doc);
        System.out.print("Inherited constants, ");

        inherited_fields(doc);
        System.out.print("Inherited fields, ");

        inherited_methods(doc);
        System.out.print("Inherited methods, ");

        public_constructors(doc);
        System.out.print("public constructors, ");

        public_methods(doc);
        System.out.print("public methods ");

        public_constructors_methods_detail(doc);
        System.out.print("and public constructors and methods");

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        endTag();
    }

    private void className(Document doc)  {

        String title = doc.selectFirst("H1").text();

        // for xml file
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

        UseFile.writeFile(xmlFile, "\n </api>");
       // System.out.println("Created: " + xmlFile);
    }

    private void apiSignature(Document doc) {

        String api_signature = doc.selectFirst("#jd-content p").text(); // first <p> in <div id="jd-content">

        String xml = space(1) + "<api-signature>" + fixInvalidXML(api_signature, true) + "</api-signature>";
        UseFile.writeFile(xmlFile, xml);

        String yaml = space(1) + "api-signature: " + fixInvalidXML(api_signature, false);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void inheritance(Document doc) {

        String inheritance = doc.selectFirst(".jd-inheritance-table").text(); // Text in <table class="jd-sumtable-subclasses">

        String xml = space(1) + "<inheritance>" + fixInvalidXML(inheritance, true) + "</inheritance>";
        UseFile.writeFile(xmlFile, xml);

        String yaml = space(1) + "inheritance: " + fixInvalidXML(inheritance, false);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void subclasses(Document doc, String subclass_type) {

        if(doc.select("#" + subclass_type).isEmpty())
            return;

        String subclasses = doc.selectFirst("#" + subclass_type).text(); // Text in <div id="subclasses-direct">

        String xml = space(1) + "<" + subclass_type + ">" + fixInvalidXML(subclasses, true) + "</" + subclass_type + ">";
        UseFile.writeFile(xmlFile, xml);

        String yaml = space(1) + subclass_type + ": " + fixInvalidXML(subclasses, false);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void subclasses_summary(Document doc, String subclass_type) {

        Elements subclasses_direct_summary = doc.select("#" + subclass_type + " table tr"); // select table and tr in <div id="subclasses-direct-summary">

        String xml = space(1) + "<" + subclass_type + ">";
        String yaml = space(1) + subclass_type + ": ";
        boolean indexFirst = true;

        for (Element tr : subclasses_direct_summary) {

            Elements tds = tr.select("td");

            xml += space(1) + "<class>";
            xml += space(1) + "<name>" + fixInvalidXML(tds.get(0).text(), true) + "</name>";
            xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).text(), true) + "</description>";
            xml += space(1) + "</class>";

            if(indexFirst) {
                yaml += space(2) + "class: ";
                indexFirst = false;
            }

            if(subclasses_direct_summary.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(tds.get(0).text(), false);
            yaml += space(4) + "description: " + fixInvalidXML(tds.get(1).text(), false);
        }

        xml += space(1) + "</" + subclass_type + ">";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void inherited_xml_attributes(Document doc) {

        Elements jd_inherited_apis = doc.select("#inhattrs .jd-inherited-apis");

        String xml = space(1) + "<inherited_xml_attributes>";
        String yaml = space(1) + "inherited_xml_attributes: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element jd_inherited : jd_inherited_apis) {

            String code = jd_inherited.selectFirst("code").text();

            xml += space(1) + "<class>";
            xml += space(1) + "<name>" + fixInvalidXML(code, true) + "</name>";

            if(indexFirst) {
                yaml += space(2) + "class: ";
                indexFirst = false;
            }

            if(jd_inherited_apis.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(code, false);

            Elements tr_all = jd_inherited.select("tr");
            indexSecond = true;

            for (Element tr : tr_all) {
                Elements tds = tr.select("td");

                xml += space(1) + "<attirbute>";
                xml += space(1) + "<name>" + fixInvalidXML(tds.get(0).text(), true) + "</name>";

                if(indexSecond) {
                    yaml += space(5) + "class: ";
                    indexSecond = false;
                }

                if(tr_all.size() > 1)
                    yaml += space(6) + "- ";

                yaml += space(7) + "attirbute: ";
                yaml += space(8) + "name: " + fixInvalidXML(tds.get(0).text(), false);

                if(tds.get(1).children().size() > 1) {
                    xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).text(), false) + "</description>";

                    yaml += space(8) + "description: " + fixInvalidXML(tds.get(1).text(), false);
                }

                xml += space(1) + "</attirbute>";
            }

            xml += space(1) + "</class>";

        }

        xml += space(1) + "</inherited_xml_attributes>";
        UseFile.writeFile(xmlFile, xml);

        UseFile.writeFile(yamlFile, yaml);
    }

    private void inherited_constants(Document doc) {

        Elements jd_inherited_apis = doc.select("#inhconstants .jd-inherited-apis");

        String xml = space(1) + "<inherited_constants>";
        String yaml = space(1) + "inherited_constants: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element jd_inherited : jd_inherited_apis) {

            String code = jd_inherited.selectFirst("code").text();

            xml += space(1) + "<class>";
            xml += space(1) + "<name>" + fixInvalidXML(code, true) + "</name>";

            if(indexFirst) {
                yaml += space(2) + "class: ";
                indexFirst = false;
            }

            if(jd_inherited_apis.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(code, false);

            Elements tr_all = jd_inherited.select("tr");
            indexSecond = true;

            for (Element tr : tr_all) {
                Elements tds = tr.select("td");

                xml += space(1) + "<constant>";
                xml += space(1) + "<type>" + fixInvalidXML(tds.get(0).text(), true) + "</type>";
                xml += space(1) + "<name>" + fixInvalidXML(tds.get(1).child(0).text(), true) + "</name>";

                if(indexSecond) {
                    yaml += space(5) + "conctant: ";
                    indexSecond = false;
                }

                if(tr_all.size() > 1)
                    yaml += space(6) + "- ";

                yaml += space(7) + "type: " + fixInvalidXML(tds.get(0).text(), false);
                yaml += space(7) + "name: " + fixInvalidXML(tds.get(1).child(0).text(), false);

                if(tds.get(1).children().size() > 1) {
                    xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).child(1).text(), true) + "</description>";

                    yaml += space(7) + "description:" + fixInvalidXML(tds.get(1).child(1).text(), false);
                }

                xml += space(1) + "</constant>";
            }

            xml += space(1) + "</class>";

        }

        xml += space(1) + "</inherited_constants>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void inherited_fields(Document doc) {

        Elements jd_inherited_apis = doc.select("#inhfields .jd-inherited-apis");

        String xml = space(1) + "<inherited_fields>";
        String yaml = space(1) + "inherited_fields: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element jd_inherited : jd_inherited_apis) {

            String code = jd_inherited.selectFirst("code").text();

            xml += space(1) + "<class>";
            xml += space(1) + "<name>" + fixInvalidXML(code, true) + "</name>";

            if(indexFirst) {
                yaml += space(2) + "class: ";
                indexFirst = false;
            }

            if(jd_inherited_apis.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(code, false);

            Elements tr_all = jd_inherited.select("tr");
            indexSecond = true;

            for (Element tr : tr_all) {
                Elements tds = tr.select("td");

                xml += space(1) + "<field>";
                xml += space(1) + "<type>" + fixInvalidXML(tds.get(0).text(), true) + "</type>";
                xml += space(1) + "<name>" + fixInvalidXML(tds.get(1).child(0).text(), true) + "</name>";

                if(indexSecond) {
                    yaml += space(5) + "field: ";
                    indexSecond = false;
                }

                if(tr_all.size() > 1)
                    yaml += space(6) + "- ";

                yaml += space(7) + "type: " + fixInvalidXML(tds.get(0).text(), false);
                yaml += space(7) + "name: " + fixInvalidXML(tds.get(1).child(0).text(), false);

                if(tds.get(1).children().size() > 1) {
                    xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).child(1).text(), true) + "</description>";

                    yaml += space(7) + "description: " + fixInvalidXML(tds.get(1).child(1).text(), false);
                }

                xml += space(1) + "</field>";
            }

            xml += space(1) + "</class>";

        }

        xml += space(1) + "</inherited_fields>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void inherited_methods(Document doc) {

        Elements jd_inherited_apis = doc.select("#inhmethods .jd-inherited-apis");

        String xml = space(1) + "<inherited_methods>";
        String yaml = space(1) + "inherited_methods: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element jd_inherited : jd_inherited_apis) {

            String code = jd_inherited.selectFirst("code").text();

            xml += space(1) + "<name>" + fixInvalidXML(code, true) + "</name>";
            xml += space(1) + "<class>";

            yaml += space(2) + "name: " + fixInvalidXML(code, false);

            if(indexFirst) {
                yaml += space(3) + "class: ";
                indexFirst = false;
            }

            //if(jd_inherited_apis.size() > 1)
                //yaml += space(4) + "- ";

            Elements tr_all = jd_inherited.select("tr");
            indexSecond = true;

            for (Element tr : tr_all) {
                Elements tds = tr.select("td");

                xml += space(1) + "<method>";
                xml += space(1) + "<return-value>" + fixInvalidXML(tds.get(0).text(), true) + "</return-value>";
                xml += space(1) + "<name>" + fixInvalidXML(tds.get(1).child(0).text(), true) + "</name>";

                if(indexSecond) {
                    yaml += space(5) + "method: ";
                    indexSecond = false;
                }

                if(tr_all.size() > 1)
                    yaml += space(6) + "- ";

                yaml += space(7) + "return-value: " + fixInvalidXML(tds.get(0).text(), false);
                yaml += space(7) + "name: " + fixInvalidXML(tds.get(1).child(0).text(), false);

                if(tds.get(1).children().size() > 1) {
                    xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).child(1).text(), true) + "</description>";

                    yaml += space(7) + "description: " + fixInvalidXML(tds.get(1).child(1).text(), false);
                }

                xml += space(1) + "</method>";
            }

            xml += space(1) + "</class>";

        }

        xml += space(1) + "</inherited_methods>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void public_constructors(Document doc) {

        Elements td_all = doc.select("#pubctors tr td");

        String xml = space(1) + "<public_constructors>";
        String yaml = space(1) + "public_constructors: ";
        boolean indexFirst = true;

        for (Element tds : td_all) {

            xml += space(1) + "<method>";
            xml += space(1) + "<name>" + fixInvalidXML(tds.child(0).text(), true) + "</name>";

            if(indexFirst) {
                yaml += space(2) + "method: ";
                indexFirst = false;
            }

            if(td_all.size() > 1)
                yaml += space(3) + "- ";

            yaml += space(4) + "name: " + fixInvalidXML(tds.child(0).text(), false);

            if(tds.children().size() > 1) {
                xml += space(1) + "<description>" + fixInvalidXML(tds.child(1).text(), true) + "</description>";

                yaml += space(4) + "description: " + fixInvalidXML(tds.child(1).text(), true);
            }

            xml += space(1) + "</method>";
        }

        xml += space(1) + "</public_constructors>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void public_methods(Document doc) {

        Elements tr_all = doc.select("#pubmethods tr");

        String xml = space(1) + "<public_methods>";
        String yaml = space(1) + "public_methods: ";
        boolean indexFirst = true;

            for (Element tr : tr_all) {

                Elements tds = tr.select("td");

                if(tds.isEmpty())
                    continue;

                xml += space(1) + "<method>";
                xml += space(1) + "<return-value>" + fixInvalidXML(tds.get(0).text(), true) + "</return-value>";
                xml += space(1) + "<name>" + fixInvalidXML(tds.get(1).child(0).text(), true) + "</name>";

                if(indexFirst) {
                    yaml += space(2) + "method: ";
                    indexFirst = false;
                }

                if(tr_all.size() > 1)
                    yaml += space(3) + "- ";

                yaml += space(4) + "return-value: " + fixInvalidXML(tds.get(0).text(), false);
                yaml += space(4) + "name: " + fixInvalidXML(tds.get(1).child(0).text(), false);

                if(tds.get(1).children().size() > 1) {
                    xml += space(1) + "<description>" + fixInvalidXML(tds.get(1).child(1).text(), true) + "</description>";

                    yaml += space(4) + "description: " + fixInvalidXML(tds.get(1).child(1).text(), false);
                }

                xml += space(1) + "</method>";
            }

        xml += space(1) + "</public_methods>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private void public_constructors_methods_detail(Document doc) {

        Elements api_section = doc.select("div[data-version-added]:not([id])"); //div has attribute "data-version-added" but not attribute "id"

        String xml = space(1) + "<api-section>";
        String yaml = space(1) + "api-section: ";
        boolean indexFirst = true;
        boolean indexSecond = true;

        for (Element api : api_section) {

            xml += space(1) + "<api>";

            if(indexFirst) {
                yaml += space(2) + "api: ";
                indexFirst = false;
            }

            if(api_section.size() > 1)
                yaml += space(3) + "- ";

            if(!api.select(".api-name").isEmpty()) {
                xml += space(1) + "<name>" + fixInvalidXML(api.selectFirst(".api-name").text(), true) + "</name>";

                yaml += space(4) + "name: " + fixInvalidXML(api.selectFirst(".api-name").text(), false);
            }


            if(api.selectFirst(".api-level a") != null) {
                xml += space(1) + "<api_level>" + fixInvalidXML(api.selectFirst(".api-level a").text(), true) + "</api_level>";

                yaml += space(4) + "api_level: " + fixInvalidXML(api.selectFirst(".api-level a").text(), false);
            }

            if(api.selectFirst(".api-signature") != null) {
                xml += space(1) + "<api_signature>" + fixInvalidXML(api.selectFirst(".api-signature").text(), true) + "</api_signature>";

                yaml += space(4) + "api_signature: " + fixInvalidXML(api.selectFirst(".api-signature").text(), false);
            }

            if(!api.select("p").isEmpty()) {
                xml += space(1) + "<description>" + fixInvalidXML(api.selectFirst("p").text(), true) + "</description>";

                yaml += space(4) + "description: " + fixInvalidXML(api.selectFirst("p").text(), false);
            }

            Elements trs = api.select("tr");

            if(!trs.isEmpty()) {
                xml += space(1) + "<parameters>";

                yaml += space(5) + "parameters: ";
            }

            indexSecond = true;

            for(Element tr: trs) {

                Elements tds = tr.select("td");

                if(tds.isEmpty())
                    continue;

                xml += space(1) + "<parameter>";
                xml += space(1) + "<name>" + fixInvalidXML(tr.child(0).text(), true) + "</name>";
                xml += space(1) + "<description>" + fixInvalidXML(tr.child(1).text(), true) + "</description>";
                xml += space(1) + "</parameter>";

                if(indexSecond) {
                    yaml += space(6) + "parameter: ";
                    indexSecond = false;
                }

                if(trs.size() > 1)
                    yaml += space(7) + "- ";

                yaml += space(8) + "name: " + fixInvalidXML(tr.child(0).text(), false);
                yaml += space(8) + "description: " + fixInvalidXML(tr.child(1).text(), false);
            }

            if(!trs.isEmpty())
                xml += space(1) + "</parameters>";

            xml += space(1) + "</api>";

        }

        xml += space(1) + "</api-section>";
        UseFile.writeFile(xmlFile, xml);
        UseFile.writeFile(yamlFile, yaml);
    }

    private String fixInvalidXML(String data, boolean fix) {

        if(fix) {
            data = data.replace("<", "&lt;");
            data = data.replace(">", "&gt;");
            data = data.replace("'", "&apos;");
            data = data.replace("\"", "&quot;");
            data = data.replace("&", "&amp;");
        }

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
