package com.aattwwss;

import java.util.List;
import java.util.Stack;

public class Parser {

    public static Node parse(String s) throws ParserException {
        List<Lexer.Token> tokens = Lexer.tokenise(s);
        return parse(tokens);
    }

    public static Node parse(List<Lexer.Token> tokens) throws ParserException {
        Node root = null;
        Stack<Node> nodeStack = new Stack<>();

        int i = 0;
        while (i < tokens.size()) {
            Lexer.Token token = tokens.get(i);
            switch (token.type) {
                case L_START_TAG:
                    // end with an open start tag
                    if (i + 1 == tokens.size() || i + 2 == tokens.size()) {
                        throw new ParserException("Invalid start tag");
                    }

                    // incomplete start tag
                    if (tokens.get(i + 1).type != Lexer.Type.TAG_NAME || tokens.get(i + 2).type != Lexer.Type.R_TAG) {
                        throw new ParserException("Invalid start tag");
                    }

                    Lexer.Token startTagToken = tokens.get(i + 1);
                    nodeStack.push(new Node(startTagToken.value, null));
                    i += 3;
                    break;
                case CONTENT:
                    if (nodeStack.isEmpty()) {
                        throw new ParserException("content must be between start and end tags");
                    }
                    nodeStack.peek().setValue(token.value);
                    i++;
                    break;
                case L_END_TAG:
                    // end with an open end tag
                    if (i + 1 == tokens.size() || i + 2 == tokens.size()) {
                        throw new ParserException("Invalid end tag");
                    }

                    // incomplete end tag
                    if (tokens.get(i + 1).type != Lexer.Type.TAG_NAME || tokens.get(i + 2).type != Lexer.Type.R_TAG) {
                        throw new ParserException("Invalid end tag");
                    }

                    String endTagName = tokens.get(i + 1).value;

                    if (nodeStack.isEmpty()) {
                        throw new ParserException(String.format("missing start tag for %s", endTagName));
                    }

                    Node node = nodeStack.pop();
                    if (!node.getKey().equals(endTagName)) {
                        throw new ParserException("mismatched tag");
                    }

                    if (nodeStack.isEmpty()) {
                        if (root != null) {
                            throw new ParserException("only one root allowed");
                        } else {
                            root = node;
                        }
                    } else {
                        Node parent = nodeStack.peek();
                        parent.getChildren().add(node);
                    }
                    i += 3;
                    break;
                case R_TAG:
                    // starting with close
                    if (i==0 || i==1) {
                        throw new ParserException("missing <");
                    }

                    // close without opening
                    if (tokens.get(i - 1).type != Lexer.Type.TAG_NAME || tokens.get(i - 2).type != Lexer.Type.L_END_TAG || tokens.get(i - 2).type != Lexer.Type.L_START_TAG) {
                        throw new ParserException("missing <");
                    }

                    i++;
                    break;
            }

        }
        if (!nodeStack.isEmpty()) {
            throw new ParserException("missing end tag for " + nodeStack.pop().getKey());
        }
        return root;
    }
}