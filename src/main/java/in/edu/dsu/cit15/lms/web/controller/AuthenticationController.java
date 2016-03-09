package in.edu.dsu.cit15.lms.web.controller;

import com.google.appengine.api.users.UserServiceFactory;
import in.edu.dsu.cit15.lms.data.entity.User;
import in.edu.dsu.cit15.lms.data.repositories.UserRepository;
import in.edu.dsu.cit15.lms.security.GoogleUserAuthentication;
import in.edu.dsu.cit15.lms.security.UserAuthority;
import in.edu.dsu.cit15.lms.web.form.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by abhishekmunie on 24/01/16.
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  @Qualifier("userRepository")
  private UserRepository userRepository;

  @RequestMapping(value = "/signout", method = RequestMethod.GET)
  public void signout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    request.getSession().invalidate();
    String logoutUrl = UserServiceFactory.getUserService().createLogoutURL("/auth/signin");
    response.sendRedirect(logoutUrl);
  }

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String signupForm(Model model) {
    model.addAttribute(new SignupForm());
    return "auth/signup";
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@Valid SignupForm signupForm, BindingResult result) {
    if (result.hasErrors()) {
      return "auth/signup";
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currentUser = (User) authentication.getPrincipal();
    Set<UserAuthority> roles = EnumSet.of(UserAuthority.USER);

    if (UserServiceFactory.getUserService().isUserAdmin()) {
      roles.add(UserAuthority.ADMIN);
    }

    User user = new User(currentUser.getId(), currentUser.getNickname(),
      currentUser.getEmail(), signupForm.getGivenName(), signupForm.getFamilyName(), roles,
      true);

    userRepository.register(user);

    // Update the context with the full authentication
    SecurityContextHolder.getContext().setAuthentication(new GoogleUserAuthentication(user, authentication.getDetails()));

    return "redirect:/dashboard/";
  }

}
