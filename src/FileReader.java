import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


public class FileReader {

    //Путь к каталогу с отчетами
    private static final Path resourcesDir = Paths.get(System.getProperty("user.dir"),"resources");

    public String readFileContentsOrNull(String fileName)
    {
        try {
            return Files.readString(resourcesDir.resolve(fileName));
        } catch (IOException e) {
            return null;
        }
    }
}
