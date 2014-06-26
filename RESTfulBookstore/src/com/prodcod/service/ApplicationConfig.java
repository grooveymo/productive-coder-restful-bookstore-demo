package com.prodcod.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


//'per-request' approach
@ApplicationPath("/")
public class ApplicationConfig extends Application {
    @SuppressWarnings("unchecked")
	public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(BookstoreService.class));
   }
}



//this deploys service as Singleton //@ApplicationPath("/")
//public class ApplicationConfig extends Application
//{
//   private Set<Object> singletons = new HashSet<Object>();
//
//   public ApplicationConfig()
//   {
//      singletons.add(new BookstoreService());
//   }
//
//   @Override
//   public Set<Object> getSingletons()
//   {
//      return singletons;
//   }
//}