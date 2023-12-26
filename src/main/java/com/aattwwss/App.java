package com.aattwwss;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        for (String s : args) {
            System.out.println(isXML(s));
        }
    }
    private static boolean isXML(String input) {
        try {
            Node root = Parser.parse(input);
        } catch (ParserException e) {
            return false;
        }
        return true;
    }
}
