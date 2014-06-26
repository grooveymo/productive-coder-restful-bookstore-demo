package com.prodcod.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.prodcod.domain.Book;
import com.prodcod.domain.Customer;
import com.prodcod.domain.Order;
import com.prodcod.domain.OrderItem;

public class BackendServiceImpl implements BackendService {

	private static final String[] bookTitles = new String[]{"Dummies Guide to Dummies","Release the hounds","How to be happy"};
	private static final float[] bookPrice = new float[]{10.45f, 22.00f, 5.50f};
	private static final String[] bookAuthors = new String[]{"Man Equine","Rufus Ruff","Oona Appy"};
	private static final Random random = new Random();
	
	//Create collection of books
	private static final List<Book> bookList = new ArrayList<Book>();
	static {
		 for (int index = 0; index < bookTitles.length; index++) {
				bookList.add(createBook(index));			 
		 }
	}
	
	//Simulate persistence storage
	private Map<Integer, Customer> customers = new ConcurrentHashMap<Integer, Customer>();

	//Simulate persistence storage
	private Map<Integer, Order> orders = new ConcurrentHashMap<Integer, Order>();

	private AtomicInteger customerIdCounter = new AtomicInteger();

	private AtomicInteger orderIdCounter = new AtomicInteger();

	public BackendServiceImpl() {
		initialiseCustomers();
	}
	
	private static Book createBook(int index) {
		
		final Book book = new Book(bookTitles[index], "Red Penguin", bookPrice[index], 2014, bookAuthors[index]);
		
		return book;
	}
	
	private OrderItem createOrderItem() {
				
		int index = random.nextInt(bookTitles.length);

		final Book book = bookList.get(index);

		final OrderItem orderItem = new OrderItem(book);
		
		final int quantity = random.nextInt(3);
		for(int c = 0; c < quantity; c++) {
			orderItem.incrementQuantity();			
		}
		
		return orderItem;
	}
	
	private Order createOrder() {
		
		final Order order = new Order();

		final int numberOrderItems = random.nextInt(2) + 1;

		Set<OrderItem> set = new HashSet<OrderItem>();
		
		int count = set.size();
		while(count < numberOrderItems) {
			set.add(createOrderItem());
			count = set.size();
		}
		
		order.setOrderItems(new ArrayList<OrderItem>(set));

		final int index = orderIdCounter.getAndIncrement();
		
		order.setOrderId(index);
		
		orders.put(index, order);
		
		return order;
	}
	
	private List<Order> createCustomerOrders() {

		final int numberOrders = random.nextInt(3) + 1;		
		
		final List<Order> orders = new ArrayList<Order>();
		
		for (int index = 0; index < numberOrders; index++) {
			orders.add(createOrder());
		}
		return orders;
	}
	/*
	 * Utility method to create a customer and add to collection
	 */
	private int addCustomer(final String firstName, final String lastName, final String email, final String mobileNumber, final String password) {
		final Customer customer   = new Customer(firstName, lastName, email, mobileNumber, password);
		int id = customerIdCounter.incrementAndGet();
		customer.setUserId(id);
		
		final List<Order> customerOrders = createCustomerOrders();
		
		//Add all orders for this customer
		for(Order order : customerOrders) {
			customer.addOrder(order);
		}
		
		customers.put(id, customer);

		return id;

	}
	/*sets up dummy customers
	 * 
	 */
	private void initialiseCustomers() {
		addCustomer("homer", "simpson", "hsimpson@springfield.com", "0781 123 456", "marge");
		
		addCustomer("ned", "flanders", "nflanders@springfield.com", "0781 777 888", "maude");

		addCustomer("monty", "burns", "mburns@springfield.com", "0781 $$$ $$$", "release the hounds");
	}
	
	@Override
	public List<Customer> getAllCustomers() {		
		List<Customer> customersList = new ArrayList<Customer>(customers.values());
		return customersList;
	}

	@Override
	public Customer getCustomer(int customerId) {
		return customers.get(customerId);
	}

	@Override
	public List<Order> getCustomerOrders(int customerId) {
		final Customer customer = customers.get(customerId);
		return customer.getOrders();
	}

	@Override
	public Order getOrder(int orderId) {
		return orders.get(orderId);
	}
	
	public static void main (final String[] args) {
		final BackendService service = new BackendServiceImpl();
		
		final Customer customer = service.getCustomer(1);
		
		System.out.println("Customer: " + customer);
	}

	@Override
	public int createCustomer(Customer customer) {
		return addCustomer(customer.getForename(), customer.getLastname(), customer.getEmail(), customer.getMobileNumber(), customer.getPassword());
	}

	@Override
	public void deleteCustomer(int id) {		
		customers.remove(id);		
	}
	
	
}
