package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.mapper.YogaInstanceContext;

public interface Enricher
{
    void enrich(YogaInstanceContext<?> entityContext);
}
