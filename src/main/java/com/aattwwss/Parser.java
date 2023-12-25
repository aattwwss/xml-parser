package com.aattwwss;

import java.util.List;
import java.util.Stack;

public class Parser {

    public static class Tag {
        public final String name;
        public final boolean isStart;

        public Tag(String name, boolean isStart) {
            this.name = name;
            this.isStart = isStart;
        }
    }
    public static Node parse(List<Lexer.Token> tokens) {
        Node root = new Node("root", "");

        Stack<String> stack = new Stack<>();
        int i = 0;
        while (i < tokens.size()) {
            String content = null;
            Lexer.Token token = tokens.get(i);
            if (token.type != Lexer.Type.L_START_TAG && token.type != Lexer.Type.L_END_TAG && token.type != Lexer.Type.CONTENT) {
                i++;
                continue;
            }
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

                    String key = stack.pop();
                    if (!key.equals(tokens.get(i + 1).value)) {
                        throw new RuntimeException("mismatched tag");
                    }
                    Node node = new Node(key, key);
                    i += 3;
                    break;
            }

        }
        return root;
    }
}
