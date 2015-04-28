package org.skyscreamer.yoga.selector.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.skyscreamer.yoga.util.JsonObjectNode;
import org.skyscreamer.yoga.util.JsonPropertyNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AliasJsonSelectorParser {

    private static final String JSON_NODE_TYPE_VALUE_STRING = JsonToken.VALUE_STRING.name();
    private static final String JSON_NODE_TYPE_END_ARRAY = JsonToken.END_ARRAY.name();
    private static final String JSON_NODE_TYPE_FIELD_NAME = JsonToken.FIELD_NAME.name();

    public Map<String, JsonObjectNode> extractYogaAlias(JsonParser jsonParser) throws IOException {
        jsonParser.nextToken();
        JsonObjectNode objectNode = null;
        Map<String, JsonObjectNode> entities = new HashMap<String, JsonObjectNode>();
        while(jsonParser.hasCurrentToken()) {
            String nodeType = jsonParser.getCurrentToken().name();
            if(enterInObject(nodeType)) {
                String name = jsonParser.getCurrentName();
                objectNode = new JsonObjectNode(name, objectNode);
            } else if(isAllChildrenComputed(nodeType)) {
                if(objectNode != null && objectNode.isRootNode()) {
                    objectNode.setAllChildrenComputed(true);
                    if(objectNode.isValid()) {
                        entities.put(objectNode.getName(), objectNode);
                    } else {
                        throw new RuntimeException("The yoga alias '"+objectNode.getName()+" must start by "+JsonPropertyNode.YOGA_SYMBOL_ALIAS+" (or "+JsonPropertyNode.YOGA_SYMBOL_VARIABLE+" for a variable) !!");
                    }
                    objectNode = null;
                } else if(objectNode != null) {
                    objectNode = objectNode.getParentNode();
                }
            } else if(isAValueProperty(nodeType)) {
                String value = jsonParser.getValueAsString();
                JsonPropertyNode property = new JsonPropertyNode(value);
                objectNode.addChild(property);
            }
            jsonParser.nextToken();
        }
        return entities;
    }

    private boolean isAValueProperty(String nodeType) {
        return JSON_NODE_TYPE_VALUE_STRING.equals(nodeType);
    }

    private boolean isAllChildrenComputed(String nodeType) {
        return JSON_NODE_TYPE_END_ARRAY.equals(nodeType);
    }

    private boolean enterInObject(String nodeType) {
        return JSON_NODE_TYPE_FIELD_NAME.equals(nodeType);
    }

}

