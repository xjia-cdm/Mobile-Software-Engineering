import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AndroidAPI {

    String formatData = "";

    public static void main(String []args) {
        //System.out.println("Hello World");

        AndroidAPI androidAPI = new AndroidAPI();

        String [] api = {"Button", "TexView", "View", "CheckBox", "RadioButton", "Switch", "ToggleButton"};
        String [] url = {
                "https://developer.android.com/reference/android/widget/Button",
                "https://developer.android.com/reference/android/widget/TextView",
                "https://developer.android.com/reference/android/view/View",
                "https://developer.android.com/reference/android/widget/CheckBox",
                "https://developer.android.com/reference/android/widget/RadioButton",
                "https://developer.android.com/reference/android/widget/Switch",
                "https://developer.android.com/reference/android/widget/ToggleButton"
                };

        for (int i = 0; i < api.length; i++) {

            String content = androidAPI.getUrlContents(url[i]);

            int file = androidAPI.createFile("data/" + api[i] + ".xml", content);

            if(file == 1)
                System.out.println(api[i] + " File created successfully");
            else
                System.out.println(api[i] + " File not created");

        } 
        
        /*

        String content = androidAPI.getUrlContents("https://developer.android.com/reference/android/widget/Button");

        //System.out.println("Contents = " + content);

        //int file = androidAPI.createFile("data/MyFile.txt", content);
        int file = androidAPI.createFile("data/api.xml", content);

        if(file == 1)
            System.out.println("File created successfully");
        else
            System.out.println("File not created");
         */
    }

    private String getUrlContents(String theUrl)
    {
        StringBuilder contents = new StringBuilder();
        String data = "";
        String refinedData = "";
        //String formatData = "";

        try
        {
            URL url = new URL(theUrl);

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( url.openStream() )) ;

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                contents.append(line + "\n");
            }

            String startData = "START OF CLASS DATA";
            String endData = "END OF CLASS DATA";

            int start = contents.indexOf(startData);
            //System.out.println("start = " + start);

            int end = contents.indexOf(endData);

            if(end > 0) // if end data is available
                end = end + endData.length();

            //System.out.println("end = " + end);

            refinedData += "<api>\n";

            if(end > start) { // data is available within given range
                data = contents.substring(start, end);
                //System.out.println("data = " + data);

                getData("<h1", "</h1>", data);
                data = dataLeft("</h1>", data);
                refinedData += "<api-title>" + formatData + "</api-title>\n";
                //System.out.println("data = " + data);

                formatData = "";
                data = getRepeatedData("<code class=\"api-signature\">", "</code>", data);
                refinedData += "<api-signature>" + trimNewLines(formatData).trim() + "</api-signature>\n";

                formatData = "";
                getData("<table class=\"jd-inheritance-table\">", "</table>", data);
                data = dataLeft("</table>", data);
                refinedData += "<super-class>" + trimNewLines(formatData).trim() + "</super-class>\n";

                formatData = "";
                getData("<div id=\"subclasses-direct\"", "</div>", data);
                data = dataLeft("</div>", data);
                refinedData += "<subclasses-direct>" + trimNewLines(formatData).trim() + "</subclasses-direct>\n";

                formatData = "";
                getData("<div id=\"subclasses-indirect\"", "</div>", data);
                data = dataLeft("</div>", data);
                refinedData += "<subclasses-indirect>" + trimNewLines(formatData).trim() + "</subclasses-indirect>\n";


                formatData = "";
                data = data.replace("<code>"," --code-- ");
                data = data.replace("</code>"," --/code-- ");

                getData("<div class=\"expandable jd-inherited-apis\"", "</div>", data);

                formatData = trimNewLines(formatData).trim();

                formatData = formatData.replaceAll("--code--","<code>");
                formatData = formatData.replaceAll("--/code--","</code>\n");

                data = dataLeft("</div>", data);
                refinedData += "<summary>" + trimNewLines(formatData).trim() + "</summary>\n";

/*
                formatData = "";
                data = data.replace("<code>"," --code-- ");
                data = data.replace("</code>"," --/code-- ");

                data = getRepeatedData("<div class=\"expandable jd-inherited-apis\"", "</div>", data);

                formatData = formatData.replaceAll("--code--","<code>");
                formatData = formatData.replaceAll("--/code--","</code>\n");

                refinedData += "<summary>" + trimNewLines(formatData).trim() + "</summary>\n";
*/
                //System.out.println("data = " + data);
                refinedData += "</api>";
                //System.out.println(" ---- Refined Data ---- \n" + refinedData);

            }

            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //return content.toString();
        return refinedData;
    }

    private int count(final String string, final String substring)
    {
        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != -1)
        {
            idx++;
            count++;
        }

        return count;
    }

    private String trimNewLines(String contents) {

        contents = contents.replace("&nbsp;", " ");
        contents = contents.replaceAll("&#x21b3;", " -> ");
        contents = contents.replaceAll("\\s{2,}", " ");

        return contents;
    }

    private String dataLeft(String startData, String contents) {
        // start from end of startData
        return contents.substring(contents.indexOf(startData) + startData.length());
    }

    private void getData(String startData, String endData, String contents) {

        String data = "";

        int start = contents.indexOf(startData);
        //System.out.println("start = " + start);

        //int end = contents.indexOf(endData);
        int end = contents.indexOf(endData, start);

        if(end > 0) // if end data is available
            end = end + endData.length();

        //System.out.println("end = " + end);

        if(end > start && start >= 0) { // data is available within given range
            data = trimTags( contents.substring(start, end) );
            //System.out.println("data = " + data);
        }

        formatData += " " + data;
        //return data;
    }

    private String getRepeatedData(String startData, String endData, String contents) {

        while (contents.indexOf(startData) >= 0) {
            getData(startData, endData, contents);

            if(contents.indexOf(endData) >= 0)
                contents = dataLeft(endData, contents);
            else
                break;
        }

        return contents; // return remaining data in content after last endData
    }

    private String trimTags(String contents) {

        return contents.replaceAll("\\<.*?\\>", "");
    }

    private int createFile(String file, String content) {

        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }
}
