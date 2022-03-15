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
                    //convertToJSON2(input);
                } else {
                    //convertToJSON(input);
                }

                nestingConverter(input);

                break;
            case '{':
                if (input.contains("#")) {
                    //convertToXML2(input);
                } else {
                    //convertToXML(input);
                }

                nestingConverterXML(input);
                break;
            default:
        }
    }

    public void nestingConverterXML(String json){
        //System.out.println(json);
        Deque<String> t = addToStack(json);
        processJSON(t);
    }

    public void processJSON(Deque<String> tokens) {
        List<String> paths = new ArrayList<>();
        int i = 0;
        while(true) {
            String firstString = tokens.peekFirst();
            String lastString = tokens.peekLast();
            String path = "", value = "";
            if (firstString.equalsIgnoreCase("{") && lastString.equalsIgnoreCase("}")) {
                tokens.removeLast();
                tokens.removeFirst();
            } else {
                String currentString = tokens.poll().replace("\\+", "");
                String[] line = currentString.split(":");

                if (line.length == 2) {
                    String line1 = line[0].strip().replace("\"", "");
                    String line2 = line[1].strip().replace("\\+", "");
                    //System.out.printf("%s, %s", line1, line2);
                    if(line2.equals("{")){
                        System.out.println("Element:");
                        if(!paths.contains(line1)){
                            paths.add(line1);
                            path = line1;
                        }
                        printPaths(paths, "");
                        boolean status = false;
                        String nextString = tokens.peek().replace("\\+", "");

                        Map<String, String> attr = new HashMap<>();
                        boolean same = false;
                        boolean dontPrint = false;

                        while(nextString.contains("@") || nextString.contains("#")) {
                            currentString = tokens.poll().replace("\\+", "");
                            line = currentString.split(":");
                            line1 = line[0].strip().replace("\"", "");
                            line2 = line[1].strip().replace("\\+", "").replace("\"", "");
                            if (line1.contains("@")) {
                                String key = line1.replace("@","").replace("\"","");
                                value = line2.replace("\"","").replace(",","");
                                if(key.length()>1 && !value.equalsIgnoreCase("{")){
                                    //printAttributesJSON(key, value);
                                    attr.put(key, value);
                                } else {
                                    dontPrint = true;
                                }

                            } else {
                                String key = line1.replace("#","").replace("\"","");
                                value = line2.replace("\"","").replace(",","");
                                //printValue(value);
                                if(path.equalsIgnoreCase(key)){
                                    same=true;
                                }
                            }
                            nextString = tokens.peek().replace("\\+", "");
                            status = true;
                        }
                        if(same){
                            if(!dontPrint){
                                if ("null".equalsIgnoreCase(value)) {
                                    printValue(null);
                                } else if (value.contains("{") && value.contains("}")){
                                    printValue("");
                                } else {
                                    printValue(value);
                                }
                            }

                            for (Map.Entry<String, String> it: attr.entrySet()) {
                                printAttributesJSON(it.getKey(),it.getValue());
                            }
                        }
                        System.out.println();
                        if(status){
                            paths.remove(path);
                        }

                    } else {
                        path = line1.replace("\"", "");
                        value = line2.replace("\"", "").replace(",", "");
                        System.out.println("Element:");
                        printPaths(paths, path);
                        if ("null".equalsIgnoreCase(value)) {
                            printValue(null);
                        } else if (value.contains("{") && value.contains("}")){
                            printValue("");
                        } else {
                            printValue(value);
                        }



                        System.out.println();
                    }
                } else {

                }

            }

            if (i++ == 53){
                break;
            }

        }
        //tokens.stream().forEach(System.out::println);
    }

    public void processJSON11(Deque<String> tokens) {

        //tokens.stream().forEach(System.out::println);
        List<String> paths = new ArrayList<>();
        int i = 0;
        while(true) {
            //if (!tokens.isEmpty()) {
            //System.out.println("working");
            String firstString = tokens.peekFirst();
            String lastString = tokens.peekLast();
            String path = "", value = "";
            if (firstString.equalsIgnoreCase("{") && lastString.equalsIgnoreCase("}")) {
                tokens.removeLast();
                tokens.removeFirst();
                //processJSON(tokens);
            } else {
                String currentString = tokens.poll().replace("\\+", "");
                String[] line = currentString.split(":");

                if (line.length == 2) {
                    String line1 = line[0].strip();
                    String line2 = line[1].strip().replace("\\+", "");
                    if (!line1.contains("#") && !line1.contains("@") && line2.equalsIgnoreCase("{")) {
                        path = line1.replace("\"", "");
                        if(!paths.contains(path)){
                            paths.add(path);
                        }
                        String nextString = tokens.peek().replace("\\+", "");
                        if(nextString.contains("@")){
                            processJSON(tokens);
                        } else {

                        }
                        System.out.println("Element:");
                        printPaths(paths, "");

                        System.out.println();
                    } else if (line1.contains("#") || line1.contains("@")){
                        if (line1.contains("@")) {
                            String key = line1.replace("@","").replace("\"","");
                            value = line2.replace("\"","").replace(",","");
                            printAttributesJSON(key, value);
                        } else {
                            value = line2.replace("\"","").replace(",","");
                            printValue(value);
                        }
                    } else {
                        path = line1.replace("\"","");
                        value = line2.replace("\"","").replace(",","");
                        if(!paths.contains(path)){
                            paths.add(path);
                        }
                        System.out.println("Element:");
                        printPaths(paths, "");
                        printValue(value);
                        System.out.println();


                    }

                } else {
                    if(currentString.equalsIgnoreCase("},")){
                        //System.out.println(currentString);
                    }

                }
                //processJSON(tokens);
            }

            if (i++ == 5){
                break;
            }

        }
        //tokens.stream().forEach(System.out::println);
    }

    public void nestingConverter(String xml){
        //System.out.println(xml);
        Deque<String> t = addToStack(xml);
        processXML(t);
    }

    public void processXML(Deque<String> tokens) {
        //System.out.println("#*#*#*#*#*#*#*####*#*#*#*#*#");
        //tokens.stream().forEach(System.out::println);
        List<String> paths = new ArrayList<>();
        int i = 0;
        while(true) {

            String firstString = tokens.peekFirst().replace("<","").replace(">","");
            String lastString = tokens.peekLast().replace("</","").replace(">","");
            String path = "", value = "";
            if (firstString.equalsIgnoreCase(lastString)) {
                tokens.removeLast();
                tokens.removeFirst();
                System.out.println("Element:");
                path = firstString;

                if(!paths.contains(path)){
                    paths.add(path);
                }

                printPaths(paths, "");
                System.out.println();
            } else {
                String currentString = tokens.poll();

                if (currentString.contains("</")) {
                    System.out.println("Element:");
                    String[] s = currentString.split(">");
                    String[] s2 = s[1].split("</");

                    if (s2.length == 2) {
                        value = s2[0];
                        path = s2[1];
                    }
                    if (value.isEmpty()){
                        //value = "\"\"";
                    }

                    //System.out.println(currentString);
                    /*if (s[0].contains("=")){
                        String[] s3 = s[0].split(" ");
                        for(int j = 1; j < s3.length; j++){
                            System.out.println(s3[j]);
                        }
                    }*/

                    printPaths(paths, path);
                    //System.out.println(path);
                    printValue(value);
                    if (s[0].contains("=")){
                        printAttributes(s[0]);
                    }
                    System.out.println();
                } else if (currentString.contains("/>")){
                    System.out.println("Element:");
                    String s = currentString.replace("/>", "").replace("<","").strip();

                    //value = null;
                    if (s.contains("=")){
                        String[] s2 = s.split(" ");
                        path = s2[0].strip();
                    }else{
                        path = s;
                    }
                    //System.out.println(currentString);
                    //System.out.println(path);
                    printPaths(paths, path);
                    printValue(null);
                    if (s.contains("=")){
                        printAttributes(s);
                    }
                    System.out.println();
                }
            }

            if (i++ == 14){
                break;
            }

        }
        //tokens.stream().forEach(System.out::println);
    }

    public void printValue(String v){
        System.out.print("value = ");
        if(v == null){
            System.out.println(v);
        }else{
            System.out.println("\""+v+"\"");
        }
    }

    public void printAttributes(String s){
        String[] s3 = s.split(" ");

        if(s3.length>0){
            System.out.println("attributes:");
            for(int j = 1; j < s3.length; j++){
                System.out.println(s3[j]);
            }
        }
    }

    public void printAttributesJSON(String k, String v){
        System.out.println("attributes:");
        System.out.println(k+"=\""+v+"\"");

    }

    public void printPaths(List<String> list, String p){
        System.out.print("path = ");
        String s = "";
        Set<String> l = Set.of();
        //ArrayList<String> list = new ArrayList<>(l);
        for(int i = 0; i < list.size(); i++) {
            if (i == list.size()-1){
                s += list.get(i);
            }else{
                s += list.get(i) + ", ";
            }
        }
        if(p.isEmpty()){
            System.out.println(s);
        }else{
            System.out.println(s+", "+p);
        }

    }

    public Deque<String> addToStack(String xml){
        Deque<String> tokens = new LinkedList<>();
        String ls = System.getProperty("line.separator");
        String[] lines = xml.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tokens.add(lines[i].strip());
        }
        return tokens;
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
