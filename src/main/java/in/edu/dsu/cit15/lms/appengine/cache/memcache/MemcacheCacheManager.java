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
package in.edu.dsu.cit15.lms.appengine.cache.memcache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * {@link CacheManager} implementation backed by Google App Engine {@link MemcacheService}.
 *
 * @author Marcel Overdijk
 * @since 0.1
 */
public class MemcacheCacheManager extends AbstractTransactionSupportingCacheManager {

  @Override
  protected Collection<? extends Cache> loadCaches() {
    return Collections.emptySet();
  }

  @Override
  public Cache getCache(String name) {
    Cache cache = super.getCache(name);
    if (cache == null) {
      MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService(name);
      cache = new MemcacheCache(memcacheService);
      addCache(cache);
    }
    return cache;
  }
}
