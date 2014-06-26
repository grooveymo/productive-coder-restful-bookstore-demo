package com.prodcod.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {

	private static final long serialVersionUID = -5757865852221123124L;

	private int userId;

    private String forename;
	
    private String lastname;
	
    private String email;
	
    private String mobileNumber;
	
    private String password;

//	private ShippingAddress shippingAddress;
//	private BillingAddress billingAddress;

    private List<Order> orders;
    
	public Customer() {		
	}

	public Customer(final String forename, final String lastname, final String email, final String mobileNumber, final String password) {
		this.forename  = forename;
		this.lastname = lastname;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.orders = new ArrayList<Order>();
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(final Order order) {
		orders.add(order);
	}

	@Override
	public String toString() {
		return "Customer [userId=" + userId + ", forename=" + forename
				+ ", lastname=" + lastname + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", password=" + password
				+ ", orders=" + orders + "]";
	}
	


}
