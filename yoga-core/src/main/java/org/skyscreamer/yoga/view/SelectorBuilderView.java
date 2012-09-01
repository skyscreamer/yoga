package org.skyscreamer.yoga.view;

import org.apache.commons.io.IOUtils;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.Selector;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is the view for the .yoga selector builder
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class SelectorBuilderView extends AbstractYogaView {
    @Override
    protected void render(Selector selector, Object value, YogaRequestContext context, OutputStream out)
            throws Exception
    {
        InputStream in = getClass().getResourceAsStream("/selectorBuilder.html");
        IOUtils.copy(in, out);
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getHrefSuffix() {
        return "yoga";
    }
}
