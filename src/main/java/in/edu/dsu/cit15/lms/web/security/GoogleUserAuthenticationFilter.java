package in.edu.dsu.cit15.lms.web.security;

import com.google.appengine.api.users.UserServiceFactory;
import in.edu.dsu.cit15.lms.data.entity.User;
import in.edu.dsu.cit15.lms.security.UserAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by abhishekmunie on 26/01/16.
 */
public class GoogleUserAuthenticationFilter extends GenericFilterBean {

  private static final String REGISTRATION_URL = "/auth/signup";

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
  private AuthenticationManager authenticationManager;
  private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext()
      .getAuthentication();
    com.google.appengine.api.users.User googleUser = UserServiceFactory.getUserService().getCurrentUser();

    if (authentication != null
      && !loggedInUserMatchesGAEUser(authentication, googleUser)) {
      SecurityContextHolder.clearContext();
      authentication = null;
      ((HttpServletRequest) request).getSession().invalidate();
    }

    if (authentication == null) {
      if (googleUser != null) {
        logger.debug("Currently logged on to GAE as user " + googleUser);
        logger.debug("Authenticating to Spring Security");
        // User has returned after authenticating via GAE. Need to authenticate
        // through Spring Security.
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
          googleUser, null);
        token.setDetails(ads.buildDetails((HttpServletRequest) request));

        try {
          authentication = authenticationManager.authenticate(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);

          if (authentication.getAuthorities().contains(UserAuthority.NEW_USER)) {
            logger.debug("New user authenticated. Redirecting to registration page");
            ((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);
            return;
          }

        } catch (AuthenticationException e) {
          failureHandler.onAuthenticationFailure((HttpServletRequest) request,
            (HttpServletResponse) response, e);
          return;
        }
      }
    }

    chain.doFilter(request, response);
  }


  private boolean loggedInUserMatchesGAEUser(Authentication authentication,
                                             com.google.appengine.api.users.User googleUser) {
    assert authentication != null;

    if (googleUser == null) {
      // User has logged out of GAE but is still logged into application
      return false;
    }

    User user = (User) authentication.getPrincipal();

    if (!user.getEmail().equals(googleUser.getEmail())) {
      return false;
    }

    return true;
  }

  @Override
  public void afterPropertiesSet() throws ServletException {
    Assert.notNull(authenticationManager, "AuthenticationManager must be set");
  }

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
    this.failureHandler = failureHandler;
  }
}
