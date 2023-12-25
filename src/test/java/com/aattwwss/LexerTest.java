package com.aattwwss;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test
    public void testTokenise() {
        String input = "<Design><Code>hello world</Code></Design>";
        Lexer.Token[] expected = {
                new Lexer.Token(Lexer.Type.L_OPEN_TAG, "<"),
                new Lexer.Token(Lexer.Type.CONTENT, "Design"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.L_OPEN_TAG, "<"),
                new Lexer.Token(Lexer.Type.CONTENT, "Code"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.CONTENT, "hello world"),
                new Lexer.Token(Lexer.Type.L_CLOSE_TAG, "</"),
                new Lexer.Token(Lexer.Type.CONTENT, "Code"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.L_CLOSE_TAG, "</"),
                new Lexer.Token(Lexer.Type.CONTENT, "Design"),
                new Lexer.Token(Lexer.Type.R_TAG, ">")
        };

        Lexer.Token[] actual = Lexer.tokenise(input).toArray(new Lexer.Token[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].type, actual[i].type);
            assertEquals(expected[i].value, actual[i].value);
        }
    }

    @Test
    public void testTokenise2() {
        String input = "<Design><Code>hello world<Code></Design>";
        Lexer.Token[] expected = {
                new Lexer.Token(Lexer.Type.L_OPEN_TAG, "<"),
                new Lexer.Token(Lexer.Type.CONTENT, "Design"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.L_OPEN_TAG, "<"),
                new Lexer.Token(Lexer.Type.CONTENT, "Code"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.CONTENT, "hello world"),
                new Lexer.Token(Lexer.Type.L_OPEN_TAG, "<"),
                new Lexer.Token(Lexer.Type.CONTENT, "Code"),
                new Lexer.Token(Lexer.Type.R_TAG, ">"),
                new Lexer.Token(Lexer.Type.L_CLOSE_TAG, "</"),
                new Lexer.Token(Lexer.Type.CONTENT, "Design"),
                new Lexer.Token(Lexer.Type.R_TAG, ">")
        };

        Lexer.Token[] actual = Lexer.tokenise(input).toArray(new Lexer.Token[0]);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].type, actual[i].type);
            assertEquals(expected[i].value, actual[i].value);
        }
    }

}