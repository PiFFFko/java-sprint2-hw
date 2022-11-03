import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;


public class FileReader {

    //Путь к каталогу с отчетами
    private static final String directoryName = System.getProperty("user.dir") + "\\resources\\";;

    public String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(directoryName + path));
        } catch (IOException e) {
            return null;
        }
    }
}
