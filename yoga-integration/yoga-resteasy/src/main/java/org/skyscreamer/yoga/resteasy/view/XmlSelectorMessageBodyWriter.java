package org.skyscreamer.yoga.resteasy.view;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.skyscreamer.yoga.springmvc.view.AbstractYogaView;
import org.skyscreamer.yoga.springmvc.view.XmlSelectorView;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlSelectorMessageBodyWriter extends AbstractSelectorMessageBodyWriter
{
   @Override
   protected AbstractYogaView getView()
   {
      XmlSelectorView view = new XmlSelectorView();
      view.setResultTraverser( resultTraverser );
      return view;
   }
}
