import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UseFile {

    UseFile() {
    }

    static void createDirectoryFile(String fileName) {

        File file;

        file = new File(fileName);
        file.getParentFile().mkdir();

        file.delete(); // delete file in directory

        try {
            file.createNewFile(); // create same empty file again
        } catch(IOException e) {
            System.out.println("Error = " + e.getMessage());
        }

    }

    static void writeFile(String file, String content) {

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to create file: " + file);
        }
    }

    static String readFile(String file) {

        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read file: " + file);
        }

        return content;
    }
}
