package com.aattwwss;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String key;
    private String value;
    private List<Node> children;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
