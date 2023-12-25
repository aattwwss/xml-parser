package com.aattwwss;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserTest  {
    @Test
    public void testParse() {
        String input = "<root><A1><A2>A2 Content</A2><A3>A3 Content</A3></A1><B1>B1 Content</B1></root>";
        Node expected = new Node("root", null);
        expected.getChildren().add(new Node("A1", null));
        expected.getChildren().add(new Node("B1", null));
        expected.getChildren().get(0).getChildren().add(new Node("A2", "A2 Content"));
        expected.getChildren().get(0).getChildren().add(new Node("A3", "A3 Content"));

        Node actual = Parser.parse(Lexer.tokenise(input));
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getChildren().size(), actual.getChildren().size());
        for (int i = 0; i < expected.getChildren().size(); i++) {
            assertEquals(expected.getChildren().get(i).getKey(), actual.getChildren().get(i).getKey());
            assertEquals(expected.getChildren().get(i).getValue(), actual.getChildren().get(i).getValue());
            assertEquals(expected.getChildren().get(i).getChildren().size(), actual.getChildren().get(i).getChildren().size());
            for (int j = 0; j < expected.getChildren().get(i).getChildren().size(); j++) {
                assertEquals(expected.getChildren().get(i).getChildren().get(j).getKey(), actual.getChildren().get(i).getChildren().get(j).getKey());
                assertEquals(expected.getChildren().get(i).getChildren().get(j).getValue(), actual.getChildren().get(i).getChildren().get(j).getValue());
            }
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