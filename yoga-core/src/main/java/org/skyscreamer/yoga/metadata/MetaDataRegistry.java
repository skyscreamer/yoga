package org.skyscreamer.yoga.metadata;

import java.util.Collection;

import org.skyscreamer.yoga.selector.CoreSelector;

public interface MetaDataRegistry
{
    Collection<String> getTypes();

    TypeMetaData getMetaData( String type, String suffix );

    String getMetadataHref( Class<?> type, String suffix );

    void setCoreSelector( CoreSelector coreSelector );

	void registerClasses( Class<?> ... classes );

	void setRootMetaDataUrl( String rootMetaDataUrl );
}
