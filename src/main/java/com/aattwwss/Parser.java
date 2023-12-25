package com.aattwwss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Parser {

    public static Node parse(List<Lexer.Token> tokens) {
        Node root = null;
        Map<String, List<Node>> adjList = new HashMap<>();

        Stack<String> stack = new Stack<>();
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

                    stack.push(tokens.get(i + 1).value);
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

                    if (stack.isEmpty()) {
                        throw new RuntimeException("missing start tag");
                    }

                    String startTagName = stack.pop();
                    if (!startTagName.equals(tokens.get(i + 1).value)) {
                        throw new RuntimeException("mismatched tag");
                    }

                    Node node = new Node(startTagName, content);
                    if (stack.isEmpty()) {
                        if (root != null) {
                            throw new RuntimeException("only one root allowed");
                        } else {
                            root = node;
                        }
                    } else {
                        String parent = stack.peek();
                        List<Node> children = adjList.getOrDefault(parent, new ArrayList<>());
                        children.add(node);
                        adjList.put(parent, children);
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
