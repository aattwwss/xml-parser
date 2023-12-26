package com.aattwwss;

import java.util.List;
import java.util.Stack;

public class Parser {

    public static Node parse(List<Lexer.Token> tokens) {
        Node root = null;
        Stack<String> tagNameStack = new Stack<>();
        Stack<Node> nodeStack = new Stack<>();
        int i = 0;
        String content = null;
        while (i < tokens.size()) {
            Lexer.Token token = tokens.get(i);
            switch (token.type) {
                case L_START_TAG:
                    // end with an open start tag
                    if (i + 1 == tokens.size() || i + 2 == tokens.size()) {
                        throw new RuntimeException("Invalid start tag");
                    }

                    // incomplete start tag
                    if (tokens.get(i + 1).type != Lexer.Type.TAG_NAME || tokens.get(i + 2).type != Lexer.Type.R_TAG) {
                        throw new RuntimeException("Invalid start tag");
                    }

                    Lexer.Token startTagToken = tokens.get(i + 1);
                    tagNameStack.push(startTagToken.value);
                    nodeStack.push(new Node(startTagToken.value, null));
                    i += 3;
                    break;
                case CONTENT:
                    content = token.value;
                    i++;
                    break;
                case L_END_TAG:
                    // end with an open end tag
                    if (i + 1 == tokens.size() || i + 2 == tokens.size()) {
                        throw new RuntimeException("Invalid end tag");
                    }

                    // incomplete end tag
                    if (tokens.get(i + 1).type != Lexer.Type.TAG_NAME || tokens.get(i + 2).type != Lexer.Type.R_TAG) {
                        throw new RuntimeException("Invalid end tag");
                    }

                    String endTagName = tokens.get(i + 1).value;

                    if (tagNameStack.isEmpty()) {
                        throw new RuntimeException(String.format("missing start tag for %s", endTagName));
                    }

                    String startTagName = tagNameStack.pop();
                    if (!startTagName.equals(endTagName)) {
                        throw new RuntimeException("mismatched tag");
                    }

                    Node node = nodeStack.pop();
                    node.setValue(content);
                    if (tagNameStack.isEmpty()) {
                        if (root != null) {
                            throw new RuntimeException("only one root allowed");
                        } else {
                            root = node;
                        }
                    } else {
                        Node parent = nodeStack.peek();
                        parent.getChildren().add(node);
                    }
                    content = null;
                    i += 3;
                    break;
                case R_TAG:
                    i++;
                    break;
            }

        }
        return root;
    }
}