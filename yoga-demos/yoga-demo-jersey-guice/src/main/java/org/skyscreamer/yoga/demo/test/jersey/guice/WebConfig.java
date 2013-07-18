package org.skyscreamer.yoga.demo.test.jersey.guice;

import java.util.HashMap;
import java.util.Map;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.test.jersey.resources.AbstractController;
import org.skyscreamer.yoga.jersey.config.URIExtensionsConfig;
import org.skyscreamer.yoga.jersey.view.JsonSelectorMessageBodyWriter;
import org.skyscreamer.yoga.jersey.view.SelectorBuilderMessageBodyWriter;
import org.skyscreamer.yoga.jersey.view.XmlSelectorMessageBodyWriter;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.metadata.MetaDataRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.util.ClassFinderStrategy;
import org.skyscreamer.yoga.view.JsonSelectorView;
import org.skyscreamer.yoga.view.SelectorBuilderView;
import org.skyscreamer.yoga.view.XmlSelectorView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class WebConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				ApplicationContext spring = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
				Map<String, String> params = new HashMap<String, String>();
				params.put("javax.ws.rs.Application", URIExtensionsConfig.class.getName());
				params.put("com.sun.jersey.config.property.packages", AbstractController.class.getPackage().getName());

				bindAll(spring, GenericDao.class, 
						XmlSelectorView.class, JsonSelectorView.class, SelectorBuilderView.class, 
						CoreSelector.class, MetaDataRegistry.class, ClassFinderStrategy.class,
						SelectorParser.class, RenderingListenerRegistry.class );
				
				bind(JsonSelectorMessageBodyWriter.class);
				bind(SelectorBuilderMessageBodyWriter.class);
				bind(XmlSelectorMessageBodyWriter.class);
				serve("*.yoga", "*.json", "*.xml", "*.xhtml").with(GuiceContainer.class, params);
			}
			
			private void bindAll(ApplicationContext spring, Class<?> ... types) 
			{
				for(Class<?> type : types) 
					bind(spring, type);
			}

			private <T> void bind(ApplicationContext spring, Class<T> type)
			{
				bind(type).toInstance(spring.getBean(type));
			}
		});
	}

}
