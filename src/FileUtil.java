import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), "UTF-8");
    }

    public static void writeFile(String filename, String content) throws IOException {
        Files.write(Paths.get(filename), content.getBytes("UTF-8"));
    }
}
