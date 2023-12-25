package com.aattwwss;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserTest  {
    @Test
    public void testParse() {
        String input = "<Design><Code>hello world</Code></Design>";
        Node expected = new Node("root", "");
        expected.getChildren().add(new Node("Code", "hello world"));
        expected.getChildren().add(new Node("Design", null));

        Node actual = Parser.parse(Lexer.tokenise(input));
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getChildren().size(), actual.getChildren().size());
        for (int i = 0; i < expected.getChildren().size(); i++) {
            assertEquals(expected.getChildren().get(i).getKey(), actual.getChildren().get(i).getKey());
            assertEquals(expected.getChildren().get(i).getValue(), actual.getChildren().get(i).getValue());
        }
    }

    @Test
    public void testParse_whenError() {
        String input = "<Design><Code>hello world<Code></Design>";

        try {
            Parser.parse(Lexer.tokenise(input));
            fail("should throw exception");
        } catch (RuntimeException e) {
            assertEquals("mismatched tag", e.getMessage());
        }
    }

}