package com.aattwwss;

public class XMLUtil {
    public static boolean isValidXML(String input) {
        try {
            Node root = Parser.parse(input);
        } catch (ParserException e) {
            return false;
        }
        return true;
    }
}
