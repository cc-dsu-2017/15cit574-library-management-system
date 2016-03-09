package in.edu.dsu.cit15.lms.data.repositories;

import in.edu.dsu.cit15.lms.appengine.objectify.repository.OfyRepository;
import in.edu.dsu.cit15.lms.data.entity.User;

/**
 * Created by abhishekmunie on 26/01/16.
 */
public interface UserRepository extends OfyRepository<User, String> {

  User findById(String id);

  void register(User newUser);

}
