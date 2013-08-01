package org.skyscreamer.yoga.view;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.skyscreamer.yoga.mapper.YogaRequestContext;
import org.skyscreamer.yoga.selector.parser.SelectorParser;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

/**
 * This is the view for the .yoga selector builder
 *
 * @author Carter Page <carter@skyscreamer.org>
 */
public class SelectorBuilderView extends AbstractYogaView {
    private String _selectorBuilderHTML = null;

    public synchronized String getSelectorBuilderHTML() {
        if (_selectorBuilderHTML == null) {
            // Initialize velocity engine
            VelocityEngine engine = new VelocityEngine();
            engine.setProperty(Velocity.RESOURCE_LOADER, "classpath");
            engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            engine.init();

            // Set properties
            Context velocityContext = new VelocityContext();
            SelectorParser selectorParser = getSelectorResolver().getSelectorParser();
            velocityContext.put("selectorJavascriptURL", selectorParser.getSelectorJavascriptURL());
            velocityContext.put("selectorType", selectorParser.getSelectorType());
            Template velocityTemplate = engine.getTemplate("/selectorBuilder.vm.html");

            // Draw output
            StringWriter stringWriter = new StringWriter();
            velocityTemplate.merge(velocityContext, stringWriter);
            _selectorBuilderHTML = stringWriter.toString();
        }
        return _selectorBuilderHTML;
    }

    @Override
    protected void render(Object value, YogaRequestContext context, OutputStream out)
            throws Exception
    {
        OutputStreamWriter pageWriter = new OutputStreamWriter(out);
        pageWriter.write(getSelectorBuilderHTML());
        pageWriter.flush();
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
