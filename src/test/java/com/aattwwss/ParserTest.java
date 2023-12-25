package com.aattwwss;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest  {
    @Test
    public void testParse() {
        String input = "<Design><Code>hello world</Code></Design>";
        Node expected = new Node("Design", "");
        expected.getChildren().add(new Node("Code", "hello world"));

        Node actual = Parser.parse(Lexer.tokenise(input));
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getChildren().size(), actual.getChildren().size());
        for (int i = 0; i < expected.getChildren().size(); i++) {
            assertEquals(expected.getChildren().get(i).getKey(), actual.getChildren().get(i).getKey());
            assertEquals(expected.getChildren().get(i).getValue(), actual.getChildren().get(i).getValue());
        }
    }

}