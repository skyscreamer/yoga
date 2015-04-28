package org.skyscreamer.yoga.util;

import java.util.Map;

public class JsonPropertyNode {

    public static final String YOGA_SYMBOL_ALIAS = "$";
    public static final String YOGA_SYMBOL_VARIABLE = "@";

    protected String name;
    private String resolvedValue;

    public JsonPropertyNode(String name) {
        this.name = name;
    }

    public Boolean isAlias() {
        return this.name.startsWith(YOGA_SYMBOL_ALIAS);
    }

    public Boolean isVariable() {
        return this.name.startsWith(YOGA_SYMBOL_VARIABLE);
    }

    public boolean isValid() {
        return isAlias() || isVariable();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        if(this.isVariable()) {
            if(this.resolvedValue == null) {
                throw new RuntimeException("The variable " + this.name + " has not corresponding value !");
            } else {
                return this.resolvedValue;
            }
        }
        return this.name;
    }

    public void compute(Map<String, JsonObjectNode> entities) {
        if(isVariable()) {
            resolve(entities.get(getName()));
        }
    }

    private void resolve(JsonObjectNode objectNode) {
        if(objectNode == null) {
            throw new RuntimeException("The variable " + this.name + " has not corresponding value !");
        }
        this.resolvedValue = objectNode.toString();
    }

}

