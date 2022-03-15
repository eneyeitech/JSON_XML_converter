package converter.presentation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Converter {
    private String content;

    private void parseXML() {
        JSONDirector director = new JSONDirector(content);
        director.startConversion();
    }

    private void parseJSON() {
        XMLDirector director = new XMLDirector(content);
        director.startConversion();
    }

    public void start() {
        try {
            Path path = Paths.get("test.txt");
            //Path path = Paths.get("C:\\Users\\eneye\\Documents\\Abdulmumin\\test.txt");
            content = Files
                    .readString(path, StandardCharsets.UTF_8)
                    .trim();
            //System.out.println(content);
            long c = System.currentTimeMillis();
            if (content.startsWith("<")) {
                parseXML();
            } else {
                parseJSON();
            }
            long d = System.currentTimeMillis() - c;
        } catch (IOException e) {
            System.out.println("Error while reading file occurred.");
            System.out.println(e.getMessage());
        }
    }
}
