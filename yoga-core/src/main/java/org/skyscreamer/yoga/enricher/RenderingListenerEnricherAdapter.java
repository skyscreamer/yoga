package org.skyscreamer.yoga.enricher;

import java.util.Arrays;
import java.util.List;

import org.skyscreamer.yoga.listener.RenderingEvent;
import org.skyscreamer.yoga.listener.RenderingEventType;
import org.skyscreamer.yoga.listener.RenderingListener;

public class RenderingListenerEnricherAdapter implements RenderingListener
{

    private static final List<Enricher> DEFAULT_ENRICHERS = Arrays.asList( new HrefEnricher(),
            new ModelDefinitionBuilder(), new NavigationLinksEnricher() );

    private List<Enricher> _enrichers;

    public RenderingListenerEnricherAdapter()
    {
        this( DEFAULT_ENRICHERS );
    }

    public RenderingListenerEnricherAdapter( List<Enricher> _enrichers )
    {
        this._enrichers = _enrichers;
    }

    @Override
    public void eventOccurred( RenderingEvent event )
    {
        if (event.getType() != RenderingEventType.POJO_CHILD)
        {
            return;
        }

        for (Enricher enricher : _enrichers)
        {
            enricher.enrich( event );
        }
    }

}
