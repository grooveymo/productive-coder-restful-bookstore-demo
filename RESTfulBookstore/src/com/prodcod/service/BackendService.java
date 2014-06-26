package com.prodcod.service;

import java.util.List;

import com.prodcod.domain.Customer;
import com.prodcod.domain.Order;

/**
 * Simulates a backend service. In reality this would be replaced by 
 * an EJB tied to a persistence tier.
 * @author BruceWayne
 *
 */
public interface BackendService {

	/**
	 * Returns all Customers of the shop
	 * @return Collection of Customer instances
	 */
	List<Customer> getAllCustomers();
	
	/**
	 * Returns a Specific Customer instance
	 * @param customerId The customer id
	 * @return Customer instance for a given id
	 */
	Customer getCustomer(final int customerId);
	
	/**
	 * 
	 * @param customerId
	 */
	List<Order> getCustomerOrders(final int customerId);
	
	/**
	 * Returns a specific order
	 * @param orderId Id for Order
	 * @return Order matching the id
	 */
	Order getOrder(final int orderId);

	/**
	 * Creates a new Customer.
	 * @param customer The details for a new customer
	 * @return customer id
	 */
	int createCustomer(Customer customer);

	/**
	 * Deletes a Customer
	 * @param id
	 * @return
	 */
	void deleteCustomer(int id);
	
}
