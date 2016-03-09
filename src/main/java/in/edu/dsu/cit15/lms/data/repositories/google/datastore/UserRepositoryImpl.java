package in.edu.dsu.cit15.lms.data.repositories.google.datastore;

import in.edu.dsu.cit15.lms.appengine.objectify.repository.SimpleOfyRepository;
import in.edu.dsu.cit15.lms.data.entity.User;
import in.edu.dsu.cit15.lms.data.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * Created by abhishekmunie on 26/01/16.
 */
@Repository("userRepository")
public class UserRepositoryImpl extends SimpleOfyRepository<User, String> implements UserRepository {

  @Override
  @Cacheable("user")
  public User findById(String id) {
    try {
      return this.findOne(id);
    } catch (NullPointerException ex) {
      return null;
    }
  }

  @Override
  public void register(User newUser) {
    this.save(newUser);
  }
}
