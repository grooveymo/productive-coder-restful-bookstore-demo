package com.prodcod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prodcod.domain.Customer;
import com.prodcod.domain.Order;

public class BookstoreServiceTest {

	private static final Logger logger = Logger.getLogger(BookstoreServiceTest.class);

	private Client client;
	
	@Before
	public void setUp() throws Exception {

		logger.debug("=====================================================================");
		logger.debug(" ");
		
		client = ClientBuilder.newClient();

	}

	@After
	public void tearDown() throws Exception {
		client = null;
	}

	/**
	 * Tests that we can retrieve a specific customer using customer id
	 */
	@Test
	public void testGetSpecificCustomer() {

		final int specificCustomerId = 2;

        WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/"+String.valueOf(specificCustomerId));

		Response response = target.request().get();

		Customer customer = response.readEntity(Customer.class);

		logger.debug("Retrieved Customer: "  + customer + " for customer id: "+ specificCustomerId);

		response.close(); 

		assertEquals(200, response.getStatus());

	}


	/**
	 * Tests that we can retrieve all customers
	 */
	@Test
	public void testGetAllCustomer() {

        WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customers");

		Response response = target.request().get();

		List<Customer> customers = response.readEntity(new GenericType<List<Customer>>() {});

		logger.debug("Retrieved " + customers.size() + " Customers " );

		for(Customer c : customers) {
			logger.debug("id: " + c.getUserId() + " name: " + c.getForename() + " " + c.getLastname());
			logger.debug("orders: " + c.getOrders());
		}
		response.close();  

		assertEquals(200, response.getStatus());

	}

	/**
	 * Tests that we can retrieve all orders for a given customer
	 */
	@Test
	public void testGetCustomerOrders() {

		final int customerId = 2;

		WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/orders/" + String.valueOf(customerId));

		Response response = target.request().get();

		List<Order> customerOrders = response.readEntity(new GenericType<List<Order>>() {});

		logger.debug("Retrieved "  + customerOrders.size() + " orders for Customer with id:" + customerId);

		for(Order c : customerOrders) {
			logger.debug("id: " + c.getOrderId() + " cost: " + c.getTotalCost());
			logger.debug("orders: ");
		}
		response.close();  

		assertEquals(200, response.getStatus());

	}

	/**
	 * Tests that we can retrieve a specific order 
	 * Demonstrates GET operation
	 */
	@Test
	public void testSpecificOrder() {

		final int orderId = 2;

		WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/order/" + String.valueOf(orderId));

		Response response = target.request().get();

		final Order order = response.readEntity(Order.class);

		logger.debug(" Retrieved "  + order + " order with id:" + orderId);

		response.close();  

		assertEquals(200, response.getStatus());

	}


	/**
	 * Tests that we can add a new customer
	 * 
	 * Demonstrates POST operation
	 */
	@Test
	public void testAddCustomer() {

		WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/");

		final Customer newCustomer = new Customer("Fred", "Bloggs", "fbloggs@internet.com", "07851 444 555", "fbloggs");

		Response response = target.request().post(Entity.entity(newCustomer, "application/*+xml"));

		response.close();  

		assertEquals(201, response.getStatus());

	}

	
		/**
		 * Tests that we can update a new customer
		 * 
		 * Demonstrates PUT operation
		 */
		@Test
		public void testUpdateCustomer() {

			logger.debug("Retrieving details for customer 1");
			
			//Get the existing details for customer 1
			final int customerId = 1;
			Customer customer = null;
			Response response;
			
			try{
				
				WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/" + String.valueOf(customerId));
				
				response = target.request().get();
				customer = response.readEntity(Customer.class);
				response.close(); 						
			}
			catch(Exception we) {
				fail("Failed to retrieve customer for testUpdateCustomer");
			}

			if(customer != null) {

				logger.debug("old customer email : " + customer.getEmail());
				
				logger.debug("=================================================");
				logger.debug("Updating details for customer 1");

				//modify customer details
				customer.setEmail("myemailhaschanged@internet.com");

				WebTarget target2 = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/"+String.valueOf(customerId));
				Response response2 = target2.request().put(Entity.entity(customer, "application/*+xml"));
			      
				//Read output in string format
				logger.debug("Client Side => updateCustomer returns code: " + response2.getStatus());
				response2.close();  
				assertEquals(201, response2.getStatus());

			}

		}

		
		
		
		/**
		 * Tests that we can update a new customer
		 * 
		 * Demonstrates  operation
		 */
		@Test
		public void testDeleteCustomer() {

			logger.debug("Retrieving details for customer 1");
			
			//Get the existing details for customer 1
			final int customerId = 1;

			//			Client client = ClientBuilder.newClient();
			WebTarget target = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/" + String.valueOf(customerId));
			
			Response response = target.request().get();
			Customer customer = response.readEntity(Customer.class);

			assertEquals("failed to retrieve correct customer",customer.getUserId(), customerId);
			
			response.close();  
			
			logger.debug("=================================================");
			logger.debug("invoking delete on customer 1");


			WebTarget target2 = client.target("http://localhost:8080/RESTfulBookstore/bookstoreService/customer/"+String.valueOf(customerId));
			Response response2 = target2.request().delete();
	
		      
			//Read output in string format
			logger.debug("Client Side => updateCustomer returns code: " + response2.getStatus());
			response2.close();  

			assertEquals(200, response2.getStatus());

		}

	}
