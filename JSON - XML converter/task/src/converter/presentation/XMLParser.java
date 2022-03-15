package converter.presentation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
    private Pattern pattern;
    private Matcher matcher;
    private int i = 0;

    //Is safe to use on nested elements
    public String extractName(String element) {
        pattern = Pattern.compile("(?<=<).+?(?=\\s|/|>)");
        matcher = pattern.matcher(element);
        if (matcher.find()) {
            String content = matcher.group();

            if (content.endsWith("/")) {
                //System.out.println(content);
                return content.substring(0, content.length() - 1);
            } else {
                //System.out.println(element);
                return content;
            }
        }

        return "null";
    }

    public String parseAttributes(String element) {
        String content = element.replaceFirst("<.+?\\s", "");
        pattern = Pattern.compile(".+?(?=/>|>)");
        matcher = pattern.matcher(content);

        if (matcher.find()) {
            //System.out.println(s);
            String s = matcher.group().trim();

            return s;
        }

        return "null";//checked
    }

    public String getContent(String element, String name) {

        String originalPattern = String.format("(?<=>).*(?=</%s>)", name);
        pattern = Pattern.compile(originalPattern);
        matcher = pattern.matcher(element);


        if (matcher.find()) {
            String s = matcher.group().replaceAll("</.+?>", "");

            return s;//checked
        } else {
            return null;//checked
        }
    }

    public boolean isParent(String line) {
        if (line.contains("/>")) {
            return false;
        }

        long count = line.chars().filter(ch -> ch == '<').count();
        return count == 1;
    }
}
