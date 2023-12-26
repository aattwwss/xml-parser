package com.aattwwss;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ParserTest {
    @Test
    public void testParse() {
        String input = "<root><A1><A2>A2 Content</A2><A3>A3 Content</A3><A4><A5>A5 Content</A5></A4></A1><B1>B1 Content</B1><B1>B1 Content 2</B1></root>";
        Node root = Parser.parse(Lexer.tokenise(input));

        assertEquals("root", root.getKey());
        Node nodeA = root.getChildren().stream().filter(node -> node.getKey().equals("A1")).findFirst().orElse(null);
        assertNotNull(nodeA);
        assertEquals("A1", nodeA.getKey());

        Node nodeA2 = nodeA.getChildren().stream().filter(node -> node.getKey().equals("A2")).findFirst().orElse(null);
        assertNotNull(nodeA2);
        assertEquals("A2", nodeA2.getKey());
        assertEquals("A2 Content", nodeA2.getValue());

        Node nodeA3 = nodeA.getChildren().stream().filter(node -> node.getKey().equals("A3")).findFirst().orElse(null);
        assertNotNull(nodeA3);
        assertEquals("A3", nodeA3.getKey());
        assertEquals("A3 Content", nodeA3.getValue());

        Node nodeA4 = nodeA.getChildren().stream().filter(node -> node.getKey().equals("A4")).findFirst().orElse(null);
        assertNotNull(nodeA4);
        assertEquals("A4", nodeA4.getKey());

        Node nodeA5 = nodeA4.getChildren().stream().filter(node -> node.getKey().equals("A5")).findFirst().orElse(null);
        assertNotNull(nodeA5);
        assertEquals("A5", nodeA5.getKey());
        assertEquals("A5 Content", nodeA5.getValue());

        Node nodeB = root.getChildren().stream().filter(node -> node.getKey().equals("B1")).findFirst().orElse(null);
        assertNotNull(nodeB);
        assertEquals("B1", nodeB.getKey());
        assertEquals("B1 Content", nodeB.getValue());

        Node nodeB2 = root.getChildren().stream().filter(node -> node.getKey().equals("B1")).skip(1).findFirst().orElse(null);
        assertNotNull(nodeB2);
        assertEquals("B1", nodeB2.getKey());
        assertEquals("B1 Content 2", nodeB2.getValue());

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