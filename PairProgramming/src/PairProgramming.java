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

        // Create iOS App according
        if(pairProgramming.iOSApp)
            pairProgramming.createiOSApp();

        //create Android App
        if(pairProgramming.androidApp)
            pairProgramming.createAndroidApp();

    }

    private void readMarkDown() {

        String content = UseFile.readFile("example1.md");

        int start = 0;
        int end = 0;

        start = content.indexOf("## ");
        end = content.indexOf("### ");

        String AppNameBlock = content.substring(start, end);

        String [] lines;
        lines = AppNameBlock.split("\n");

        appName = lines[1]; // get app name

        start = content.indexOf("### ");
        end = content.indexOf("#### ");

        String configurationBlock = content.substring(start, end);

        lines = configurationBlock.split("\n");

        for(String line: lines) {

            if (line.contains("android:")) {
                if (line.contains("false"))
                    androidApp = false;
            }
            if (line.contains("iOS:")) {
                if (line.contains("false"))
                    iOSApp = false;
            }
        }


        start = content.indexOf("#### ");
        end = content.indexOf("---");

        String deisgnBlock = content.substring(start, end);

        lines = deisgnBlock.split("\n");

        for(String line: lines) {
            if (line.contains("title:")) {
                labelTitle = line.substring(line.indexOf(":") + 1);
                labelTitle = labelTitle.trim();
                labelTitle = labelTitle.replace("\"", "");
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
