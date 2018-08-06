package com.chan.study.cloud.demo;

import java.util.List;

public class SelectTreeNode {

    private String value;
    private String label;
    private List<SelectTreeNode> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<SelectTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SelectTreeNode> children) {
        this.children = children;
    }
}
