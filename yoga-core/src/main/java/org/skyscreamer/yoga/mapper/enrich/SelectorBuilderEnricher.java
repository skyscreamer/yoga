package org.skyscreamer.yoga.mapper.enrich;

import org.skyscreamer.yoga.mapper.HierarchicalModel;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.Selector;

/**
 * Created by IntelliJ IDEA.
 * User: cpage
 * Date: 12/10/11
 * Time: 3:59 PM
 */
public class SelectorBuilderEnricher extends HrefEnricher implements Enricher {
    public static final String FIELD_NAME = "selectorBuilder";

    private String suffix = "yoga";

    @Override
    public void enrich(Object instance, Selector fieldSelector, HierarchicalModel model,
                             Class<?> instanceType, String hrefSuffix, FieldPopulator<?> populator) {
        if (fieldSelector instanceof CoreSelector)
        {
            String href = determineTemplate(instanceType, populator);

            if (href != null) {
                href += "." + suffix;
                model.addSimple(FIELD_NAME, getHref(href, instance));
            }
            return;
        }
    }

    // Spring injection points
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
