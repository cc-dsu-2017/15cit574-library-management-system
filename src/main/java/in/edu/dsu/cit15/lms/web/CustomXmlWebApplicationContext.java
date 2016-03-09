package in.edu.dsu.cit15.lms.web;

import com.google.appengine.api.utils.SystemProperty;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by abhishekmunie on 18/01/16.
 */
public class CustomXmlWebApplicationContext extends XmlWebApplicationContext {

  protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
    super.initBeanDefinitionReader(beanDefinitionReader);
    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
      beanDefinitionReader.setValidating(false);
      beanDefinitionReader.setNamespaceAware(true);
    }
  }
}
