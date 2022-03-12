package converter;

import converter.presentation.UserInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserInput userInput = new UserInput();

        // File path is passed as parameter
        File file = new File("test.txt");
        //File file = new File("C:\\Users\\eneye\\Documents\\Abdulmumin\\test.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        // delete the last new line separator
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        String content = stringBuilder.toString();
        //System.out.println(content);

        userInput.run(content);


    }
}
