import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

class AndroidApp {

    String appName = "";

    AndroidApp(String appName) {
        this.appName = appName;
    }

    void fileStructure() {

        File directory;
        // If you require it to make the entire directory path including parents, use directory.mkdirs(); here instead.

        directory = new File("android");
        if (! directory.exists())
            directory.mkdir();

        directory = new File("android/app/src/main/java/edu/depaul");
        if (! directory.exists())
            directory.mkdirs();

        directory = new File("android/app/src/main/res");
        if (! directory.exists())
            directory.mkdir();

        directory = new File("android/app/src/main/res");
        if (! directory.exists())
            directory.mkdir();

        directory = new File("android/app/src/main/res/drawable");
        if (! directory.exists())
            directory.mkdir();

        directory = new File("android/app/src/main/res/layout");
        if (! directory.exists())
            directory.mkdir();

        directory = new File("android/app/src/main/res/values");
        if (! directory.exists())
            directory.mkdir();

    }

    void buildGradle() {
        String content = "";

        content += "// Top-level build file where you can add configuration options common to all sub-projects/modules. \n\n";

        content += "buildscript { \n\n";

        content += "\t repositories { \n";
        content += "\t\t google() \n";
        content += "\t\t jcenter() \n";
        content += "\t } \n";
        content += "\t dependencies { \n";
        content += "\t\t classpath 'com.android.tools.build:gradle:3.1.3' \n\n";


        content += "\t\t // NOTE: Do not place your application dependencies here; they belong \n";
        content += "\t\t // in the individual module build.gradle files \n";
        content += "\t } \n";
        content += "} \n\n";

        content += "\t allprojects { \n";
        content += "\t\t repositories { \n";
        content += "\t\t\t google() \n";
        content += "\t\t\t jcenter() \n";
        content += "\t\t } \n";
        content += "\t } \n\n";

        content += "task clean(type: Delete) { \n";
        content += "\t delete rootProject.buildDir \n";
        content += "} \n";

        UseFile.writeFile("android/build.gradle", content);
    }

    void buildGradleModule() {
        String content = "";

        content += "apply plugin: 'com.android.application' \n\n";

        content += "android { \n";
        content += "\t compileSdkVersion 27 \n";
        content += "\t\t defaultConfig { \n";
        content += "\t\t applicationId \"edu.depaul\" \n";
        content += "\t\t minSdkVersion 27 \n";
        content += "\t\t targetSdkVersion 27 \n";
        content += "\t\t versionCode 1 \n";
        content += "\t\t versionName \"1.0\" \n";
        content += "\t\t testInstrumentationRunner \"android.support.test.runner.AndroidJUnitRunner\" \n";
        content += "\t\t} \n";
        content += "\tbuildTypes { \n";
        content += "\t\t release { \n";
        content += "\t\t\t minifyEnabled false \n";
        content += "\t\t\t proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro' \n";
        content += "\t\t } \n";
        content += "\t} \n";
        content += "} \n\n";

        content += "dependencies { \n";
        content += "implementation fileTree(dir: 'libs', include: ['*.jar']) \n";
        content += "implementation 'com.android.support.constraint:constraint-layout:1.1.2' \n";
        content += "testImplementation 'junit:junit:4.12' \n";
        content += "androidTestImplementation 'com.android.support.test:runner:1.0.2' \n";
        content += "androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2' \n";
        content += "} \n";

        UseFile.writeFile("android/app/build.gradle", content);
    }

    void settingsGradle() {
        String content = "";

        content += "include ':app'";

        UseFile.writeFile("android/settings.gradle", content);
    }

    void localProperties() {
        String content = "";

        content += "sdk.dir=/Volumes/MacBookPro2/Android/SDK";

        UseFile.writeFile("android/local.properties", content);
    }

    void manifest() {
        String content = "";

        content += "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
        content += "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\" " +
                "package=\"edu.depaul\"> \n\n";

        content += "<application \n";
        content += "\t android:allowBackup=\"true\" \n";
        content += "\t android:label=\"First App\" \n";
        content += "\t android:supportsRtl=\"true\"> \n\n";

        content += "\t <activity android:name=\".MainActivity\"> \n";
        content += "\t\t <intent-filter> \n";
        content += "\t\t\t <action android:name=\"android.intent.action.MAIN\" /> \n";
        content += "\t\t\t\t <category android:name=\"android.intent.category.LAUNCHER\" /> \n";
        content += "\t\t </intent-filter> \n";
        content += "\t </activity> \n";
        content += "</application> \n\n";

        content += "</manifest> \n";

        UseFile.writeFile("android/app/src/main/AndroidManifest.xml", content);
    }

    String createLabelProgramatically(String label) {

        searchAPI("TextView");

        String code = "";
        code += "\t\t RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl); \n";
        code += "\t\t TextView tv = new TextView(getApplicationContext()); \n";
        code += "\t\t tv.setText(\"" + label + "\"); \n";
        code += "\t\t rl.addView(tv); \n\n";

        return code;
    }

    void mainActivity(String label, Boolean create) {
        String content = "";

        content += "package edu.depaul; \n\n";

        content += "import android.app.Activity; \n";
        content += "import android.os.Bundle; \n";
        content += "import android.widget.RelativeLayout; \n";
        content += "import android.widget.TextView; \n\n";

        content += "public class MainActivity extends Activity { \n\n";

        content += "\t @Override \n";
        content += "\t protected void onCreate(Bundle savedInstanceState) { \n";
        content += "\t\t super.onCreate(savedInstanceState); \n";
        content += "\t\t setContentView(R.layout.activity_main); \n";

        // Added label in layout programatically
        if(create)
            content += createLabelProgramatically(label);

        content += "\t } \n";
        content += "} \n";

        UseFile.writeFile("android/app/src/main/java/edu/depaul/MainActivity.java", content);
    }

    String createLabelLayout(String label) {

        String code = "";
        code += "\n <TextView ";
        code += "\n android:layout_width=\"wrap_content\" ";
        code += "\n android:layout_height=\"wrap_content\" ";
        code += "\n android:layout_centerHorizontal=\"true\" ";
        code += "\n android:layout_centerVertical=\"true\" ";
        //code += "\n android:text=\"@string/hello_world\" ";
        code += "\n android:text=\"" + label + "\"";
        code += "\n tools:context=\".MainActivity\" /> ";

        return code;
    }

    void mainActivityLayout(String label, Boolean create) {
        String content = "";

        content += "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n\n";

        content += "<RelativeLayout \n";
        content += "\t xmlns:android=\"http://schemas.android.com/apk/res/android\" \n";
        content += "\t xmlns:tools=\"http://schemas.android.com/tools\" \n";
        content += "\t android:id=\"@+id/rl\" \n";
        content += "\t android:layout_width=\"match_parent\" \n";
        content += "\t android:layout_height=\"match_parent\" \n";
        content += "\t android:padding=\"16dp\" \n";
        content += "\t tools:context=\".MainActivity\" \n";
        content += "\t android:background=\"@android:color/white\" \n";
        content += "\t > \n\n";

        // Added label in layout
        if(create)
            content += createLabelLayout(label);

        content += "\n </RelativeLayout> \n";

        UseFile.writeFile("android/app/src/main/res/layout/activity_main.xml", content);
    }

    void searchAPI(String file) {

        try {
            Yaml yaml = new Yaml();
            Reader yamlFile = new FileReader("api/android/" + file + ".yaml");

            Map<String, Object> yamlMaps = (Map<String, Object>) yaml.load(yamlFile);

            final Map<String, Object> module_name = (Map<String, Object>) yamlMaps.get("api");
            //System.out.println(module_name);

            final Map<String, Object> module_name3 = (Map<String, Object>) module_name.get("public_methods");
            //System.out.println(module_name3);

            final List<Map<String, Object>> module_name4 = (List<Map<String, Object>>) module_name3.get("method");
            //System.out.println(module_name4);

            for (int i = 0; i < module_name4.size(); i++) {

                if (module_name4.get(i).get("name").toString().contains("setText(CharSequence text)"))
                    System.out.println(module_name4.get(i).get("name"));
            }
        } catch (FileNotFoundException e) {

        }
    }

    void androidProject() {

        try {
            Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "cd android; gradle wrapper"});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
