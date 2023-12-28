package com.aattwwss;

/**
 * Utility class for handling XML.
 */
public class XMLUtil {

    /**
     * Checks if the given string is valid XML.
     *
     * @param input The string to check.
     * @return True if the string is valid XML, false otherwise.
     */
    public static boolean isValidXML(String input) {
        try {
            Node root = Parser.parse(input);
        } catch (ParserException e) {
            return false;
        }
        return true;
    }
}
