package org.skyscreamer.yoga.resteasy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.spring.ResteasyRegistration;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

public class RESTEasySpringListener implements SmartApplicationListener
{
   @Autowired
   ConfigurableListableBeanFactory beanFactory;

   @Autowired
   protected Registry registry;

   @Autowired
   protected ResteasyProviderFactory providerFactory;

   @Autowired
   protected Dispatcher dispatcher;

   @Override
   public void onApplicationEvent(ApplicationEvent event)
   {
      beanFactory.registerResolvableDependency(Registry.class, registry);
      beanFactory.registerResolvableDependency(ResteasyProviderFactory.class, providerFactory);
      if (dispatcher != null)
      {
         beanFactory.registerResolvableDependency(Dispatcher.class, dispatcher);
      }
      Collection<String> ignoreList = createIgnoreList(beanFactory);

      List<ResourceFactory> springResourceFactories = new ArrayList<ResourceFactory>();
      for (String name : beanFactory.getBeanDefinitionNames())
      {
         if (ignoreList.contains(name))
            continue;

         BeanDefinition beanDef = beanFactory.getBeanDefinition(name);
         if (beanDef.getBeanClassName() == null || beanDef.isAbstract())
            continue;

         Class<?> beanClass = null;
         try
         {
            beanClass = Thread.currentThread().getContextClassLoader()
                  .loadClass(beanDef.getBeanClassName());
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
         
         if (beanClass.isAnnotationPresent(Provider.class))
         {
            providerFactory.registerProviderInstance(getBean(name, beanClass));
         }

         if (GetRestful.isRootResource(beanClass))
         {
            springResourceFactories.add(new SpringResourceFactory(name, beanFactory, beanClass));
         }
      }

      for (ResourceFactory resourceFactory : springResourceFactories)
      {
         registry.addResourceFactory(resourceFactory);
      }
   }

   public Object getBean(String name, Class<?> beanClass)
   {
      Object bean = beanFactory.getBean(name);
      providerFactory.getInjectorFactory().createPropertyInjector(beanClass).inject(bean);
      return bean;
   }

   @Override
   public int getOrder()
   {
      return 1000;
   }

   @Override
   public boolean supportsEventType(Class<? extends ApplicationEvent> eventType)
   {
      return eventType == ContextRefreshedEvent.class;
   }

   @Override
   public boolean supportsSourceType(Class<?> sourceType)
   {
      return ApplicationContext.class.isAssignableFrom(sourceType);
   }

   private Collection<String> createIgnoreList(final ConfigurableListableBeanFactory beanFactory)
   {
      Map<String, ResteasyRegistration> registries = beanFactory
            .getBeansOfType(ResteasyRegistration.class);

      final Collection<String> ignoreBeansList = new HashSet<String>();
      for (ResteasyRegistration registration : registries.values())
      {
         String beanName = registration.getBeanName();
         ignoreBeansList.add(beanName);
         BeanDefinition beanDef = beanFactory.getBeanDefinition(beanName);
         Class<?> beanClass = null;
         try
         {
            beanClass = Thread.currentThread().getContextClassLoader()
                  .loadClass(beanDef.getBeanClassName());
            SpringResourceFactory reg = new SpringResourceFactory(beanName, beanFactory, beanClass);
            registry.addResourceFactory(reg, registration.getContext());
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
      }
      return ignoreBeansList;
   }
}
