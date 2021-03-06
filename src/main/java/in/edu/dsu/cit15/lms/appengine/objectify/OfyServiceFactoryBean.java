/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.edu.dsu.cit15.lms.appengine.objectify;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@link FactoryBean} that creates an {@link OfyService}.
 * <p/>
 * <p>The simplest way to use this class is to provide a <code>basePackage</code>
 * to scan for classes annotated with {@link Entity}. Found classes will be
 * registered in the master ObjectifyFactory.
 * <p/>
 * <p>Example configuration:
 * <p/>
 * <pre class="code">
 * &lt;bean id="ofyService" class="com.googlecode.spring.appengine.objectify.OfyServiceFactoryBean"
 * p:basePackage="com.mycompany.domain" /&gt;</pre>
 * <p/>
 * <p>Multiple <code>basePackages</code> can be provided as well:
 * <p/>
 * <pre class="code">
 * &lt;bean id="ofyService" class="com.googlecode.spring.appengine.objectify.OfyServiceFactoryBean"
 * p:basePackage="com.mycompany.domain;com.mycompany.other.domain" /&gt;</pre>
 * <p/>
 * <p>Instead of scanning for entities it is also possible to register them manually.
 * <p/>
 * <p>Example configuration:
 * <p/>
 * <pre class="code">
 * &lt;bean id="ofyService" class="com.googlecode.spring.appengine.objectify.OfyServiceFactoryBean"&gt;
 * &lt;property name="entityClasses"&gt;
 * &lt;list&gt;
 * &lt;value&gt;com.mycompany.domain.Car&lt;/value&gt;
 * &lt;value&gt;com.mycompany.domain.Person&lt;/value&gt;
 * &lt;/list&gt;
 * &lt;/property&gt;
 * &lt;/bean&gt;</pre>
 * <p/>
 * When having a Java-based container configuration setup the {@link OfyServiceBuilder}
 * is more suitable to create the {@link OfyService}.
 *
 * @author Marcel Overdijk
 * @since 0.2
 */
public class OfyServiceFactoryBean implements FactoryBean<OfyService>, InitializingBean {

  private OfyService ofyService;

  private String basePackage;
  private List<Class<?>> entityClasses;
  private List<TranslatorFactory<?, ?>> translatorFactories;

  @Override
  public OfyService getObject() throws Exception {
    return ofyService;
  }

  @Override
  public Class<?> getObjectType() {
    return OfyService.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    OfyServiceBuilder builder = new OfyServiceBuilder();
    if (StringUtils.hasText(basePackage)) {
      String[] basePackages = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
      for (String basePackage : basePackages) {
        builder.addBasePackage(basePackage);
      }
    }
    if (entityClasses != null) {
      for (Class<?> clazz : entityClasses) {
        builder.registerEntity(clazz);
      }
    }
    if (translatorFactories != null) {
      for (TranslatorFactory<?, ?> translatorFactory : translatorFactories) {
        builder.registerTranslatorFactory(translatorFactory);
      }
    }
    this.ofyService = builder.build();
  }

  /**
   * Set the base package(s) to scan for com.abhishekmunie.epls.data.entity classes.
   */
  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  /**
   * Set the com.abhishekmunie.epls.data.entity classes.
   */
  public void setEntityClasses(List<Class<?>> entityClasses) {
    this.entityClasses = entityClasses;
  }

  /**
   * Set the translator factories.
   */
  public void setTranslatorFactories(List<TranslatorFactory<?, ?>> translatorFactories) {
    this.translatorFactories = translatorFactories;
  }

}
