package in.edu.dsu.cit15.lms.web;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by abhishekmunie on 28/01/16.
 */
public class WebUtils {

  public static String getCompleteOriginalURL(HttpServletRequest request) {
    String originatingRequestUri = new UrlPathHelper().getOriginatingRequestUri(request);
    String originatingQueryString = new UrlPathHelper().getOriginatingQueryString(request);
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(originatingRequestUri)
      .query(originatingQueryString);
    return uriComponentsBuilder.toUriString();
  }

  public static String getEncodedCompleteOriginalURL(HttpServletRequest request) throws UnsupportedEncodingException {
    return UriUtils.encode(getCompleteOriginalURL(request), "UTF-8");
  }
}
