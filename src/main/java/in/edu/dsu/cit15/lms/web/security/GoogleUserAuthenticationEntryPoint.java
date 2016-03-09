package in.edu.dsu.cit15.lms.web.security;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by abhishekmunie on 24/01/16.
 */
public class GoogleUserAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    UserService userService = UserServiceFactory.getUserService();
    response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
  }
}
