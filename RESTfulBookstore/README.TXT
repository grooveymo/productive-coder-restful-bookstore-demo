Problem
----------
How to access JBOSS Admin console

Solution
---------
http://localhost:9990/console/App.html#deployments

=========================================================================================================================================
=========================================================================================================================================
=========================================================================================================================================
=========================================================================================================================================
21.05.14
---------
Implemented Customer domain class and BookstoreService (JAX-RS webservice).

Getting following exception when running test classe

javax.ws.rs.ProcessingException: Unable to find a MessageBodyReader of content-type text/html;charset=utf-8 and type class com.prodcod.domain.Customer
	at org.jboss.resteasy.core.interception.ClientReaderInterceptorContext.throwReaderNotFound(ClientReaderInterceptorContext.java:39)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.getReader(AbstractReaderInterceptorContext.java:73)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.proceed(AbstractReaderInterceptorContext.java:50)
	at org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor.aroundReadFrom(GZIPDecodingInterceptor.java:59)
	at org.jboss.resteasy.core.interception.AbstractReaderInterceptorContext.proceed(AbstractReaderInterceptorContext.java:53)
	at org.jboss.resteasy.client.jaxrs.internal.ClientResponse.readFrom(ClientResponse.java:244)
	at org.jboss.resteasy.client.jaxrs.internal.ClientResponse.readEntity(ClientResponse.java:178)
	at org.jboss.resteasy.specimpl.BuiltResponse.readEntity(BuiltResponse.java:217)
	at com.prodcod.service.BookstoreServiceTest.testGetCustomer(BookstoreServiceTest.java:37)


=========================================================================================================================================
20.05.14
-----------
Unfortunately the client code is using ClientRequest and ClientResponse classes which are now deprecated. They were used by earlier versions
of resteasy (e.g. 2.x.y)

        final ClientRequest clientCreateRequest = new ClientRequest("http://localhost:8080/RESTfulBookstore/dateservice/getdate");
        final ClientResponse<String> clientCreateResponse = clientCreateRequest.get(String.class);

Recommended Solution is to use new classes

		Client client = ClientBuilder.newClient();
		// or use
		//        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/dateservice/getdate");
        Response response = target.request().get();

However this does not work if you are using resteasy (JBOSS implementation of JAX-RS) and gives an error relating to glassfish.jersey

java.lang.RuntimeException: java.lang.ClassNotFoundException: org.glassfish.jersey.client.JerseyClientBuilder
	at javax.ws.rs.client.ClientBuilder.newBuilder(ClientBuilder.java:103)
	at javax.ws.rs.client.ClientBuilder.newClient(ClientBuilder.java:114)
	at com.prodcod.TestDateServiceUsingNewerClasses.testGetDateForRESTEasy3(TestDateServiceUsingNewerClasses.java:37)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
Caused by: java.lang.ClassNotFoundException: org.glassfish.jersey.client.JerseyClientBuilder
	at java.net.URLClassLoader$1.run(URLClassLoader.java:202)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:190)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:306)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:301)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:247)
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:171)
	at javax.ws.rs.client.FactoryFinder.newInstance(FactoryFinder.java:113)
	at javax.ws.rs.client.FactoryFinder.find(FactoryFinder.java:206)
	at javax.ws.rs.client.ClientBuilder.newBuilder(ClientBuilder.java:86)
	... 25 more



Solution is to add the jar resteasy-client-3.0.5.Final.jar and this error goes away.


Another solution would be to use the following instead

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/RESTfulBookstore/dateservice/getdate");
		Response response = target.request().get();


=========================================================================================================================================
20.05.14
----------
 added client code in form on integration test (written and executed using junit4)
 Requires the following jar files

	commons-io-2.4.jar
	commons-logging-1.1.1.jar
	httpclient-4.2.1.jar
	httpcore-4.2.1.jar

=========================================================================================================================================

20.05.14
----------

References 
----------

Starting points

	> http://karanbalkar.com/2013/09/tutorial-54-getting-started-with-resteasy-using-eclipse/
	
	This also instructed which jars need to be imported into the project to get enable the source
	code to compile within eclipse
	
Which is a partial summary of

	http://nuteck.wordpress.com/2012/04/02/resteasy-tutorial-with-eclipse-and-tomcat/
	
Also
	http://java.dzone.com/articles/restful-web-services-java

shows how the RESTful url is made up


Problem
--------
When deploying get the following error
	
	13:32:57,170 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-1) JBAS015876: Starting deployment of "RESTfulBookstore.war" (runtime-name: "RESTfulBookstore.war")
	13:32:57,312 INFO  [org.jboss.web] (ServerService Thread Pool -- 59) JBAS018210: Register web context: /RESTfulBookstore
	13:32:57,342 ERROR [org.apache.catalina.core.ContainerBase.[jboss.web].[default-host].[/RESTfulBookstore]] (ServerService Thread Pool -- 59) JBWEB000287: Exception sending context initialized event to listener instance of class org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap: java.lang.RuntimeException: Unable to find a public constructor for class org.jboss.resteasy.core.AsynchronousDispatcher
	at org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory.registered(POJOResourceFactory.java:35) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
	
	Caused by: org.jboss.msc.service.StartException in anonymous service: JBAS018040: Failed to start context
		at org.jboss.as.web.deployment.WebDeploymentService.doStart(WebDeploymentService.java:161)
		at org.jboss.as.web.deployment.WebDeploymentService.access$000(WebDeploymentService.java:60)
		at org.jboss.as.web.deployment.WebDeploymentService$1.run(WebDeploymentService.java:93)
		... 7 more

	13:32:57,561 ERROR [org.jboss.as.server] (HttpManagementService-threads - 3) JBAS015870: Deploy of deployment "RESTfulBookstore.war" was rolled back with the following failure message: 
	{"JBAS014671: Failed services" => {"jboss.web.deployment.default-host./RESTfulBookstore" => "org.jboss.msc.service.StartException in service jboss.web.deployment.default-host./RESTfulBookstore: org.jboss.msc.service.StartException in anonymous service: JBAS018040: Failed to start context
	    Caused by: org.jboss.msc.service.StartException in anonymous service: JBAS018040: Failed to start context"}}
	13:32:57,567 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-7) JBAS015877: Stopped deployment RESTfulBookstore.war (runtime-name: RESTfulBookstore.war) in 6ms


Solution
------------
see: http://stackoverflow.com/questions/9378106/jboss-7-1-0-error-unable-to-find-a-public-constructor-for-class-org-jboss-rest

Fix is to ensure that no RESTeasy jars are rolled into the war file.
Do this in the build.xml, e.g.

				<exclude name="WEB-INF/lib/*.jar" />


NOTE : When deploying under JBOSS 7 don't need to bundle resteasy jars into war since these are included in the JBOSS distribution
	*******************************************************************************
	To access web service, enter the following address in the browser address bar

	http://localhost:8080/RESTfulBookstore/dateservice/getdate
	*******************************************************************************

=========================================================================================================================================
	