package org.skyscreamer.yoga.mapper.enrich;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.skyscreamer.yoga.annotations.URITemplate;
import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.populator.ValueReader;
import org.skyscreamer.yoga.selector.Selector;
import org.skyscreamer.yoga.selector.SelectorParser;
import org.skyscreamer.yoga.uri.URICreator;

public class HrefEnricher implements Enricher {

    private URICreator _uriCreator = new URICreator();

    @Override
    public void enrich(HttpServletResponse response, Object instance, Selector fieldSelector,
                       HierarchicalModel model, Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator) {
        String href = determineTemplate(instanceType, populator);

        if (href != null) {
            if (hrefSuffix != null) {
                href += "." + hrefSuffix;
            }
            model.addSimple(SelectorParser.HREF, getHref(response, href, instance));
        }
    }

    protected String determineTemplate(Class<?> instanceType, FieldPopulator<?> populator) {
        String href = null;
        if (instanceType.isAnnotationPresent(URITemplate.class)) {
            href = instanceType.getAnnotation(URITemplate.class).value();
        } else if (populator != null && populator.getUriTemplate() != null) {
            href = populator.getUriTemplate();
        }
        return href;
    }

    protected Object getHref(HttpServletResponse response, String uriTemplate, final Object instance) {
        return _uriCreator.getHref(uriTemplate, response, new ValueReader() {
            @Override
            public Object getValue(String property) {
                try {
                    return PropertyUtils.getNestedProperty(instance, property);
                } catch (Exception e) {
                    throw new RuntimeException("Could not invoke getter for property " + property
                            + " on class " + instance.getClass().getName(), e);
                }
            }
        });
    }

}
