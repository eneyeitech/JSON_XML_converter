package converter.presentation;

import java.util.Scanner;
import java.util.Stack;

public class UserInput {
    private final Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        String input = scanner.nextLine();
        char c = input.charAt(0);
        switch (c) {
            case '<':
                convertToJSON(input);
                break;
            case '{':
                convertToXML(input);
                break;
            default:
        }
    }

    public void convertToXML(String json) {
        String openingTag = "<";
        String openingClosingTag = "</";
        String selfClosingTag = "/>";
        String closingTag = ">";
        String[] a = json.split(":");
        if (a.length == 2) {
            String b = a[0].replace('{', ' ');
            b = b.replace('"', ' ');
            b = b.strip();


            String c = a[1].replace('}', ' ');
            c = c.replace('"', ' ');
            c = c.strip();


            if (c.equalsIgnoreCase("null")) {
                openingTag += b;
                openingTag += selfClosingTag;
                System.out.println(openingTag);
            } else {
                openingTag += b;
                openingTag += closingTag;
                openingClosingTag += b;
                openingClosingTag += closingTag;
                openingTag += c + openingClosingTag;
                System.out.println(openingTag);
            }




        }

    }

    public void convertToJSON(String xml) {
        String openingBraces = "{";
        String semiColon = ":";
        String nullValue = " null";
        String closingBraces = "}";
        String quotes = "\"";
        String[] a = xml.split(">");
        String key = null;
        String value = null;
        if(a.length == 2) {

            String[] a2 = a[1].split("</");
            if (a2.length == 2) {
                value = a2[0];
                key = a2[1];
            }

            openingBraces += quotes + key + quotes + semiColon;
            openingBraces += quotes + value + quotes + closingBraces;
            System.out.println(openingBraces);
        } else if (a.length == 1) {

            String s = a[0].replace('<', ' ');
            s = s.replace('/', ' ');
            s = s.strip();
            openingBraces += quotes + s + quotes + semiColon;
            openingBraces += nullValue + closingBraces;
            System.out.println(openingBraces);
        }
    }
}
