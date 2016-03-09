package in.edu.dsu.cit15.lms.data.objectify;

import com.googlecode.objectify.impl.translate.TranslatorFactory;
import in.edu.dsu.cit15.lms.appengine.objectify.OfyService;
import in.edu.dsu.cit15.lms.appengine.objectify.OfyServiceBuilder;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by abhishekmunie on 27/01/16.
 */
public class OfyServiceFactoryBean extends AbstractFactoryBean<OfyService> {

  private String basePackage;
  private List<Class<?>> entityClasses;
  private List<TranslatorFactory<?, ?>> translatorFactories;

  @Override
  public Class<?> getObjectType() {
    return OfyService.class;
  }

  @Override
  protected OfyService createInstance() throws Exception {
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
    OfyService ofyService = builder.build();
    return ofyService;
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
