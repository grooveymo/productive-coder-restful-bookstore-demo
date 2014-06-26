package com.prodcod.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.prodcod.domain.Customer;
import com.prodcod.domain.Order;

@Path("/bookstoreService")
public class BookstoreService {

	private static final Logger logger = Logger.getLogger(BookstoreService.class);
	
	private static final BackendService backendService = new BackendServiceImpl();

	@DELETE
	@Path("/customer/{id}")
	public Response deleteCustomer (@PathParam("id") int id) throws URISyntaxException{

		logger.info("SERVER about to delete customer details for customer with id: " + id);

		final Customer existingCustomer = backendService.getCustomer(id);
		
		if(existingCustomer == null) {
			logger.warn("SERVER deleteCustomer NO CUSTOMER FOUND: " + existingCustomer);			
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		

		backendService.deleteCustomer(id);
		
		logger.info("SERVER customer has been deleted");

		return Response.status(200).build();
	}

	
	@PUT
	@Path("/customer/{id}")
	@Consumes("application/*")
	public Response updateCustomer (@PathParam("id") int id, Customer newCustomer) throws URISyntaxException{

		logger.info("SERVER updating customer details for customer with id: " + id);

		final Customer existingCustomer = backendService.getCustomer(id);
		
		if(existingCustomer == null) {
			logger.warn("SERVER updateCustomer NO CUSTOMER FOUND: " + existingCustomer);			
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		logger.info("SERVER old customer details : " + existingCustomer);
		
		existingCustomer.setEmail(newCustomer.getEmail());
		existingCustomer.setMobileNumber(newCustomer.getMobileNumber());
		existingCustomer.setPassword(newCustomer.getPassword());
		
		logger.info("SERVER updated details : " + existingCustomer);
		
		return Response.status(201).contentLocation(new URI("/customer/"+ newCustomer.getUserId())).build();
	}

	
	@POST
	@Path("/customer")
	@Consumes("application/*")
	public Response createCustomer (Customer customer) throws URISyntaxException{
				
		final int customerId = backendService.createCustomer(customer);
		
		logger.debug("SERVER - creating customer : " + customer);
		logger.debug("SERVER - new customer has id : " + customerId);
		
		return Response.status(201).contentLocation(new URI("/customer/"+customerId)).build();
	}
	
	/**
	 * Returns Order for a given order id
	 * 
	 * Invoked using the following url
	 * 		http://localhost:8080/RESTfulBookstore/bookstoreService/order/{id} where id is Order id
	 * 
	 * @param id Order id
	 * @return Instance of Order matching that Order id
	 */
	@GET
	@Path("/order/{id}")
	@Produces("application/*")
	public Order getOrder(@PathParam("id") int id) {
		
		final Order order = backendService.getOrder(id);
		
		if(order == null) {
			logger.warn("SERVER getOrder NO ORDER FOUND: ");						
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		logger.info("SERVER getOrder will return: " + order);

		return order;
	}

	/**
	 * Returns all Orders for a given customer.
	 * 
	 * Invoked using the following url
	 * 		http://localhost:8080/RESTfulBookstore/bookstoreService/customer/orders/{id} where {id} is the customer id
	 * 
	 * @return All Orders are returned for a given customer
	 */
	@GET
	@Path("/customer/orders/{id}")
	@Produces("application/*")
	public List<Order> getCustomerOrders(@PathParam("id") int id) {
		
		final List<Order> customerOrders = backendService.getCustomerOrders(id);

		logger.info("SERVER getCustomerOrders will return: " + customerOrders + " for customer id: " + id);

		return customerOrders;
	}

	/**
	 * Returns all Shop customers
	 * 
	 * Invoked using the following url
	 * 		http://localhost:8080/RESTfulBookstore/bookstoreService/customers
	 * 
	 * @return All customers are returned
	 */
	@GET
	@Path("/customers")
	@Produces("application/*")
	public List<Customer> getAllCustomers() {
		
		final List<Customer> customers = backendService.getAllCustomers();

		logger.info("SERVER getAllCustomers will return: " + customers );

		return customers;
	}
	

	/**
	 * Returns Customer instance for a given customer id
	 * Invoked using the following url
	 * 		http://localhost:8080/RESTfulBookstore/bookstoreService/customer/{id} where id = Customer Id
	 * 
	 * @param id customer id
	 * @return Instance of customer matching that customer id
	 */
	@GET
	@Path("/customer/{id}")
	@Produces("application/*")
	public Customer getCustomer(@PathParam("id") int id) throws WebApplicationException {
		
		final Customer customer = backendService.getCustomer(id);
		
		if(customer == null) {
			logger.warn("SERVER getCustomer NO CUSTOMER FOUND: " + customer);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		logger.info("SERVER getCustomer will return: " + customer);

		return customer;
	}
	
}
