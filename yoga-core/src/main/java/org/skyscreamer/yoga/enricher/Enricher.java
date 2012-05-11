package org.skyscreamer.yoga.enricher;

import org.skyscreamer.yoga.listener.RenderingEvent;

public interface Enricher
{
    void enrich( RenderingEvent event );
}
