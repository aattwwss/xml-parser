package com.aattwwss;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static enum Type {
        L_START_TAG,
        L_END_TAG,
        R_TAG,
        TAG_NAME,
        CONTENT
    }

    public static class Token {
        public final Type type;
        public final String value;

        public Token(Type type, String data) {
            this.type = type;
            this.value = data;
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", type.name(), value);
        }
    }

    public static List<Token> tokenise(String input) {
        final List<Token> tokens = new ArrayList<>();
        int i = 0;
        boolean inTag = false;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '<' && i != input.length() - 1 && input.charAt(i + 1) != '/') {
                tokens.add(new Token(Type.L_START_TAG, "<"));
                inTag = true;
                i++;
            } else if (c == '<' && i != input.length() - 1 && input.charAt(i + 1) == '/') {
                tokens.add(new Token(Type.L_END_TAG, "</"));
                inTag = true;
                i += 2;
            } else if (c == '>' ) {
                tokens.add(new Token(Type.R_TAG, ">"));
                inTag = false;
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < input.length() && input.charAt(i) != '<' && input.charAt(i) != '>') {
                    sb.append(input.charAt(i));
                    i++;
                }
                tokens.add(new Token(inTag ? Type.TAG_NAME: Type.CONTENT, sb.toString()));
            }
        }
        return tokens;
    }
}
