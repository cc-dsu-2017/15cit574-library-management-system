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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * {@link OfyService} builder typically used when having a Java-based container
 * configuration setup.
 * <p/>
 * <p/>
 * The builder can scan for {@link Entity} annotated classes by adding base packages. Instead of scanning for entities it is also possible to register them
 * manually.
 *
 * @author Marcel Overdijk
 * @since 0.2
 */
public class OfyServiceBuilder {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private List<String> basePackages = new ArrayList<String>();
  private List<Class<?>> entityClasses = new ArrayList<Class<?>>();
  private List<TranslatorFactory<?, ?>> translatorFactories = new ArrayList<TranslatorFactory<?, ?>>();

  /**
   * Add base package to scan for com.abhishekmunie.epls.data.entity classes.
   */
  public OfyServiceBuilder addBasePackage(String basePackage) {
    basePackages.add(basePackage);
    return this;
  }

  /**
   * Register com.abhishekmunie.epls.data.entity class.
   */
  public OfyServiceBuilder registerEntity(Class<?> clazz) {
    entityClasses.add(clazz);
    return this;
  }

  /**
   * Register {@link TranslatorFactory}.
   */
  public OfyServiceBuilder registerTranslatorFactory(TranslatorFactory<?, ?> translatorFactory) {
    translatorFactories.add(translatorFactory);
    return this;
  }

  /**
   * Build and return the {@link OfyService}.
   */
  public OfyService build() {
    long startTime = System.currentTimeMillis();

    // create the ofy service
    OfyService ofyService = new OfyService();

    // register translator factories (important: needs to be done before registering com.abhishekmunie.epls.data.entity classes)
    for (TranslatorFactory<?, ?> translatorFactory : translatorFactories) {
      ofyService.factory().getTranslators().add(translatorFactory);
      if (logger.isInfoEnabled()) {
        logger.info("Registered translator factory [" + translatorFactory.getClass().getName() + "]");
      }
    }

    // scan base packages for com.abhishekmunie.epls.data.entity classes
    entityClasses.addAll(scanBasePackages());

    // register com.abhishekmunie.epls.data.entity classes
    for (Class<?> clazz : entityClasses) {
      ofyService.factory().register(clazz);
      if (logger.isInfoEnabled()) {
        logger.info("Registered com.abhishekmunie.epls.data.entity class [" + clazz.getName() + "]");
      }
    }

    if (this.logger.isInfoEnabled()) {
      long elapsedTime = System.currentTimeMillis() - startTime;
      this.logger.info("Building objectify service completed in " + elapsedTime + " ms");
    }
    return ofyService;
  }

  protected List<Class<?>> scanBasePackages() {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    for (String basePackage : basePackages) {
      if (this.logger.isInfoEnabled()) {
        this.logger.info("Scanning package [" + basePackage + "]");
      }
      ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
      scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
      Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);
      for (BeanDefinition candidate : candidates) {
        Class<?> clazz = ClassUtils.resolveClassName(candidate.getBeanClassName(), ClassUtils.getDefaultClassLoader());
        classes.add(clazz);
      }
    }
    return classes;
  }
}
