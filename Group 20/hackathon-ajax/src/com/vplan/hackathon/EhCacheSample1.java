
package com.vplan.hackathon;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheSample1{
	
	public static void main(String[] args) {
		
		/*//1. Create a cache manager
		CacheManager cm = CacheManager.newInstance();
		
		//2. Get a cache called "cache1", declared in ehcache.xml
		Cache cache = cm.getCache("cache1");
		
		//3. Put few elements in cache
		cache.put(new Element("1","Jan"));
		cache.put(new Element("2","Feb"));
		cache.put(new Element("3","Mar"));
		cache.put(new Element("4","Mar"));
		
		//4. Get element from cache
		Element ele = cache.get("4");
		System.out.println("ELEMENT " + ele.getObjectValue().toString());
		
		//5. Print out the element
		String output = (ele == null ? null : ele.getObjectValue().toString());
		System.out.println(output);
		
		//6. Is key in cache?
		System.out.println(cache.isKeyInCache("3"));
		System.out.println(cache.isKeyInCache("10"));
		
		//7. shut down the cache manager
		cm.shutdown();*/
		
		
		System.out.println(1/1000);
	}
	
}