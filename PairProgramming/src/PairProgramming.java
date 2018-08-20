import java.io.*;

public class PairProgramming {

    String appName = "appName";
    Boolean androidApp = true;
    Boolean iOSApp = true;
    String labelTitle = "title";


    public static void main(String []args) {

        PairProgramming pairProgramming = new PairProgramming();

        // Read from MarkDown file.
        pairProgramming.readMarkDown();

        //System.out.println(pairProgramming.appName);
        //System.out.println(pairProgramming.androidApp);
        //System.out.println(pairProgramming.iOSApp);
        //System.out.println(pairProgramming.labelTitle);

        // Create iOS App according
        if(pairProgramming.iOSApp)
            pairProgramming.createiOSApp();

        //create Android App
        if(pairProgramming.androidApp)
            pairProgramming.createAndroidApp();

    }

    private void readMarkDown() {

        //System.out.println("Read MarkDown");

        String content = UseFile.readFile("example1.md");

        //System.out.println("Contents = " + content);

        int start = 0;
        int end = 0;

        start = content.indexOf("## ");
        end = content.indexOf("### ");

        String AppNameBlock = content.substring(start, end);
        //System.out.println("AppNameBlock = " + AppNameBlock);

        String [] lines;
        lines = AppNameBlock.split("\n");

        //System.out.println("Lines = " + lines.length);

        appName = lines[1]; // get app name

        start = content.indexOf("### ");
        end = content.indexOf("#### ");

        String configurationBlock = content.substring(start, end);
        //System.out.println("configurationBlock = " + configurationBlock);

        lines = configurationBlock.split("\n");

        //System.out.println("Lines = " + lines.length);

        for(String line: lines) {
            //System.out.println("Line = " + line);
            if (line.contains("android:")) {
                //System.out.println("androidApp = " + line);
                if (line.contains("false"))
                    androidApp = false;
                //System.out.println("androidApp = " + androidApp);

            }
            if (line.contains("iOS:")) {
                //System.out.println("iOSApp = " + line);
                if (line.contains("false"))
                    iOSApp = false;
                //System.out.println("iOSApp = " + iOSApp);
            }
        }


        start = content.indexOf("#### ");
        end = content.indexOf("---");

        String deisgnBlock = content.substring(start, end);
        //System.out.println("deisgnBlock = " + deisgnBlock);

        lines = deisgnBlock.split("\n");

        //System.out.println("Lines1 = " + lines.length);

        for(String line: lines) {
            //System.out.println("Line = " + line);
            if (line.contains("title:")) {
                labelTitle = line.substring(line.indexOf(":") + 1);
                labelTitle = labelTitle.trim();
                labelTitle = labelTitle.replace("\"", "");
                //System.out.println("labelTitle = " + labelTitle);
            }
        }

    }

    private void createiOSApp() {

        iOSApp myiOSApp = new iOSApp(appName);

        myiOSApp.fileStructure();
        myiOSApp.projectConfiguration();
        myiOSApp.infoPList();
        myiOSApp.appDelegate();

        myiOSApp.viewController(labelTitle + " by code", true);
        myiOSApp.mainStoryboard(labelTitle + " by storyboard", true);

        myiOSApp.iosProject(); // xCodeGen
    }

    private void createAndroidApp() {

        AndroidApp androidApp = new AndroidApp(appName);

        androidApp.fileStructure();
        androidApp.buildGradle();
        androidApp.settingsGradle();
        androidApp.localProperties();
        androidApp.buildGradleModule();
        androidApp.manifest();

        androidApp.mainActivity(labelTitle + " by code", true);
        androidApp.mainActivityLayout(labelTitle + " by layout", true);

        androidApp.androidProject(); //gradle wrapper
    }

}
