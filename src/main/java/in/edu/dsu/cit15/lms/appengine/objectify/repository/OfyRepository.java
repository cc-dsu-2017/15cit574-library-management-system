/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.edu.dsu.cit15.lms.appengine.objectify.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Objectify specific extension of {@link Repository}.
 *
 * @author Marcel Overdijk
 * @since 0.2
 */
@NoRepositoryBean
public interface OfyRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.data.repositories.CrudRepository#findAll()
   */
  List<T> findAll();

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.data.repositories.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
   */
  List<T> findAll(Sort sort);

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.data.repositories.CrudRepository#findAll(java.lang.Iterable)
   */
  List<T> findAll(Iterable<ID> ids);

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.data.repositories.CrudRepository#save(java.lang.Iterable)
   */
  <S extends T> List<S> save(Iterable<S> entities);
}
