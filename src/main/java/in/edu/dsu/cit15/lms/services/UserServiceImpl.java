package in.edu.dsu.cit15.lms.services;

import in.edu.dsu.cit15.lms.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by abhishekmunie on 29/01/16.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  @Qualifier("userRepository")
  private UserRepository userRepository;


}
