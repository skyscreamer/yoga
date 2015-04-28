package org.skyscreamer.yoga.selector.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.skyscreamer.yoga.exceptions.ParseSelectorException;
import org.skyscreamer.yoga.util.JsonObjectNode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AliasJsonSelectorResolver implements AliasSelectorResolver {

    private final static String DIRECTORY = "/yoga_aliases";
    private final static String PATTERN_END_FILE = ".json";

    private Map<String, String> aliases;

    public AliasJsonSelectorResolver() throws IOException, URISyntaxException {
        aliases = compute();
    }

    @Override
    public String resolveSelector(String aliasSelectorExpression) throws ParseSelectorException {
        return aliases.get(aliasSelectorExpression);
    }

    private Map<String, String> compute() throws IOException, URISyntaxException {
        AliasJsonSelectorParser parser = new AliasJsonSelectorParser();
        String[] fileList = new File(getClass().getResource(DIRECTORY).toURI()).list();
        Map<String, JsonObjectNode> entities = new HashMap<String, JsonObjectNode>();
        for(String fileName : fileList) {
            if(fileName.endsWith(PATTERN_END_FILE)) {
                JsonParser jsonParser = new JsonFactory().createParser(new File(getClass().getResource(DIRECTORY + "/" + fileName).toURI()));
                entities.putAll(parser.extractYogaAlias(jsonParser));
            } else {
                Logger.getLogger("AliasJsonSelectorResolver").warning("The filename " + fileName + " not end with " + PATTERN_END_FILE + " : it's unused!");
            }
        }
        Map<String, String> aliases = new HashMap<String, String>();
        for(JsonObjectNode objectNode : entities.values()) {
            objectNode.compute(entities);
            aliases.put(objectNode.getName(), objectNode.toString());
        }
        return aliases;
    }

}
