package in.edu.dsu.cit15.lms.security;

import in.edu.dsu.cit15.lms.data.entity.User;
import in.edu.dsu.cit15.lms.data.repositories.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by abhishekmunie on 26/01/16.
 */
public class GoogleUserAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

  protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  private UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    com.google.appengine.api.users.User googleUser = (com.google.appengine.api.users.User) authentication.getPrincipal();

    User user = userRepository.findById(googleUser.getUserId());

    if (user == null) {
      // User not in registry. Needs to register
      user = new User(googleUser.getUserId(), googleUser.getNickname(),
        googleUser.getEmail());
    }

    if (!user.isEnabled()) {
      throw new DisabledException("Account is disabled");
    }

    return new GoogleUserAuthentication(user, authentication.getDetails());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
  }

  @Override
  public void setMessageSource(MessageSource messageSource) {
    this.messages = new MessageSourceAccessor(messageSource);
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}

