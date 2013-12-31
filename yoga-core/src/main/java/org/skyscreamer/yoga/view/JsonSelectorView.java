package org.skyscreamer.yoga.view;

import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.model.HierarchicalModel;
import org.skyscreamer.yoga.model.ObjectListHierarchicalModelImpl;
import org.skyscreamer.yoga.model.ObjectMapHierarchicalModelImpl;
import org.skyscreamer.yoga.util.ClassUtil;
import org.skyscreamer.yoga.view.json.serializer.Jackson2Serializer;
import org.skyscreamer.yoga.view.json.serializer.JacksonSerializer;
import org.skyscreamer.yoga.view.json.serializer.JsonSerialiazer;

import java.io.IOException;
import java.io.OutputStream;

public class JsonSelectorView extends AbstractYogaView {

    private JsonSerialiazer serialiazer;

    public JsonSelectorView() {
        if (ClassUtil.jacksonPresent) {
            serialiazer = new JacksonSerializer();
        } else if (ClassUtil.jackson2Present) {
            serialiazer = new Jackson2Serializer();
        } else {
            throw new IllegalStateException("Jackson library not in classpath.");
        }
    }

    /**
     * Initialize this view with a json serializer.
     * @param serialiazer
     */
    public JsonSelectorView(JsonSerialiazer serialiazer) {
        this.serialiazer = serialiazer;
    }

    @Override
    public void render(Object value, YogaRequestContext requestContext, OutputStream outputStream) throws IOException {
        HierarchicalModel<?> model = null;
        if (value instanceof Iterable<?>) {
            ObjectListHierarchicalModelImpl listModel = new ObjectListHierarchicalModelImpl();
            _resultTraverser.traverseIterable((Iterable<?>) value, requestContext.getSelector(), listModel, requestContext);
            model = listModel;
        } else {
            ObjectMapHierarchicalModelImpl mapModel = new ObjectMapHierarchicalModelImpl();
            _resultTraverser.traversePojo(value, requestContext.getSelector(), mapModel, requestContext);
            model = mapModel;
        }
        serialiazer.serialize(outputStream, model.getUnderlyingModel());
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getHrefSuffix() {
        return "json";
    }

    /**
     * Set this views json serializer.
     * @see org.skyscreamer.yoga.view.json.serializer.JacksonSerializer
     * @see org.skyscreamer.yoga.view.json.serializer.Jackson2Serializer
     * @param serialiazer
     */
    public void setSerialiazer(JsonSerialiazer serialiazer) {
        this.serialiazer = serialiazer;
    }
}
