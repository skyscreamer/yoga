package org.skyscreamer.yoga.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonObjectNode extends JsonPropertyNode {

    private JsonObjectNode parentNode;
    private List<JsonPropertyNode> children = new ArrayList<JsonPropertyNode>();
    private Boolean allChildrenComputed = false;

    public JsonObjectNode(String name, JsonObjectNode parentNode) {
        super(name);
        this.parentNode = parentNode;
        if(parentNode != null) {
            parentNode.addChild(this);
        }
    }

    public Boolean isRootNode() {
        return this.parentNode == null;
    }

    public JsonObjectNode addChild(JsonPropertyNode child) {
        children.add(child);
        return this;
    }

    public JsonObjectNode getParentNode() {
        return this.parentNode;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if(!this.isRootNode()) {
            res.append(this.name).append("(");
        }
        res.append(StringUtils.join(children, ","));
        if(!this.isRootNode()) {
            res.append(")");
        }
        return res.toString();
    }

    @Override
    public void compute(Map<String, JsonObjectNode> entities) {
        for(JsonPropertyNode property : children) {
            property.compute(entities);
        }
    }

    public Boolean isAllChildrenComputed() {
        return allChildrenComputed;
    }

    public void setAllChildrenComputed(Boolean allChildrenComputed) {
        this.allChildrenComputed = allChildrenComputed;
    }

}
