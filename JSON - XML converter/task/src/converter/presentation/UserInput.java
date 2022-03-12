package converter.presentation;

import java.util.*;

public class UserInput {
    private final Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public void run(String input) {
        //String input = scanner.nextLine();
        char c = input.charAt(0);
        switch (c) {
            case '<':

                if(input.contains("=")){
                    convertToJSON2(input);
                } else {
                    convertToJSON(input);
                }

                break;
            case '{':
                if (input.contains("#")) {
                    convertToXML2(input);
                } else {
                    convertToXML(input);
                }

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

    public void convertToXML2(String json) {
        if(json.contains("null")) {
            xml1(json);
        } else {
            xml2(json);
        }
    }

    public void xml1(String json) {
        String openingTag = "<";
        String openingClosingTag = "</";
        String selfClosingTag = "/>";
        String closingTag = ">";
        String[] a = json.split(":");
        String xml = "<";

        Queue<String> tokens = new LinkedList<>();

        //Arrays.stream(a).forEach(System.out::println);
        //Arrays.stream(d).forEachOrdered(t -> tokens.add(t));
        for (int i = 0; i< a.length; i++) {
            if(a[i].contains(",")){
                String[] s = a[i].split(",");
                for (int j = 0; j< s.length; j++) {
                    tokens.add(s[j]);
                }
            } else {
                tokens.add(a[i]);
            }
        }
        //tokens.stream().forEach(System.out::println);

        String key = null;
        String value = null;
        String obj = null;
        boolean exit = false;
        while(true){

            String s = tokens.poll();

            if (s == null) {
                break;
            }
            if (s.contains("{") && !s.contains("@")){
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                obj = s;
                xml +=  s + " ";
            } else if(s.contains("\"@")) {
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                s = s.replace('@', ' ');
                s = s.strip();
                key = s;
                value = tokens.poll();
                value = value.replace('"', ' ');
                value = value.strip();
                xml += key + " = \"" + value + "\" ";
            } else if (s.contains("\"#")){
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                s = s.replace('#', ' ');
                s = s.strip();

                value = tokens.poll();
                value = value.replace('"', ' ');
                value = value.strip();
                value = value.replace('}', ' ');
                value = value.strip();
                //xml += ">"+value+"</"+obj+">";
                xml += "/>";
                exit = true;
            } else {
                key = s;
            }

            if(exit){
                break;
            }

        }
        System.out.println(xml);
    }

    public void xml2(String json) {
        String openingTag = "<";
        String openingClosingTag = "</";
        String selfClosingTag = "/>";
        String closingTag = ">";
        String[] a = json.split(":");
        String xml = "<";

        Queue<String> tokens = new LinkedList<>();

        //Arrays.stream(a).forEach(System.out::println);
        //Arrays.stream(d).forEachOrdered(t -> tokens.add(t));
        for (int i = 0; i< a.length; i++) {
            if(a[i].contains(",")){
                String[] s = a[i].split(",");
                for (int j = 0; j< s.length; j++) {
                    tokens.add(s[j]);
                }
            } else {
                tokens.add(a[i]);
            }
        }
        //tokens.stream().forEach(System.out::println);

        String key = null;
        String value = null;
        String obj = null;
        boolean exit = false;
        while(true){

            String s = tokens.poll();

            if (s == null) {
                break;
            }
            if (s.contains("{") && !s.contains("@")){
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                obj = s;
                xml +=  s + " ";
            } else if(s.contains("\"@")) {
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                s = s.replace('@', ' ');
                s = s.strip();
                key = s;
                value = tokens.poll();
                value = value.replace('"', ' ');
                value = value.strip();
                xml += key + " = \"" + value + "\" ";
            } else if (s.contains("\"#")){
                s = s.replace('{', ' ');
                s = s.strip();
                s = s.replace('"', ' ');
                s = s.strip();
                s = s.replace('#', ' ');
                s = s.strip();

                value = tokens.poll();
                value = value.replace('"', ' ');
                value = value.strip();
                value = value.replace('}', ' ');
                value = value.strip();
                xml += ">"+value+"</"+obj+">";
                exit = true;
            } else {
                key = s;
            }

            if(exit){
                break;
            }

        }
        System.out.println(xml);
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

    public void convertToJSON2(String xml) {
       if(xml.contains("</")) {
            json2(xml);
       } else {
           json1(xml);
       }
    }
    public void json2(String xml) {
        String[] a = xml.split(">");
        String[] d = a[0].split(" ");

        String json = "{";

        Queue<String> tokens = new LinkedList<>();

        //Arrays.stream(d).forEach(System.out::println);
        //Arrays.stream(d).forEachOrdered(t -> tokens.add(t));
        for (int i = 0; i< d.length; i++) {
            tokens.add(d[i]);
        }
        tokens.add(a[1]);
        //tokens.stream().forEach(System.out::println);
        String key = null;
        String value = null;
        String obj = null;
        boolean exit = false;
        while(true){

            String s = tokens.poll();

            if (s == null) {
                break;
            }
            if (s.contains("<") && !s.contains("</")){
                s = s.replace('<', ' ');
                s = s.strip();
                obj = s;
                json += "\"" + s + "\":{";
            } else if(s.equals("=")) {
                value = tokens.poll();
                json += "\"@" + key + "\":" + value + ",";
            } else if (s.contains("\">")){

            }else if (s.contains("\"")){

            }else if (s.contains("</")){
                exit = true;
                String[] c = s.split("</");
                json += "\"#" + obj + "\":\"" + c[0]+"\"}}";
            }else if (s.contains("/>")) {
                exit = true;
                json += "\"#" + obj + "\":" + "null}}";
            } else {
                key = s;
            }

            if(exit){
                break;
            }

        }
        System.out.println(json);
    }

    public void json1(String xml) {
        String[] a = xml.split("<");
        String[] b = xml.split("/>");
        String[] c = xml.split("=");
        String[] d = xml.split(" ");

        String openingBraces = "{";
        String semiColon = ":";
        String nullValue = " null";
        String closingBraces = "}";
        String quotes = "\"";
        String json = "{";

        Queue<String> tokens = new LinkedList<>();

        //Arrays.stream(d).forEach(System.out::println);
        //Arrays.stream(d).forEachOrdered(t -> tokens.add(t));
        for (int i = 0; i< d.length; i++) {
            tokens.add(d[i]);
        }
        String key = null;
        String value = null;
        String obj = null;
        boolean exit = false;
        while(true){

            String s = tokens.poll();

            if (s == null) {
                break;
            }
            if (s.contains("<")){
                s = s.replace('<', ' ');
                s = s.strip();
                obj = s;
                json += "\"" + s + "\":{";
            } else if(s.equals("=")) {
                value = tokens.poll();
                json += "\"@" + key + "\":" + value + ",";
            } else if (s.contains("\">")){

            }else if (s.contains("\"")){

            }else if (s.contains("</")){
                exit = true;
            }else if (s.contains("/>")) {
                exit = true;
                json += "\"#" + obj + "\":" + "null}}";
            } else {
                key = s;
            }

            if(exit){
                break;
            }

        }
        System.out.println(json);
    }
}
