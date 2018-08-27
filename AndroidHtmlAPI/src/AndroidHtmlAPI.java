import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


public class AndroidHtmlAPI {

    public static void main(String[] args) throws IOException {

        AndroidHtmlAPI androidAPIJ = new AndroidHtmlAPI();
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

        String url;
        System.out.println("Downloading Start: ");
        System.out.println("-----------");

        for(String widget: widgets) {

            url = "https://developer.android.com/reference/android/widget/" + widget + ".html";
            doc = Jsoup.connect(url).get();
            androidAPIJ.saveData(widget, doc.outerHtml());
            System.out.println(widget);
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