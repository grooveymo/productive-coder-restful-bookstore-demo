package com.prodcod.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = -5362053016861873420L;

	private int orderId;

	private List<OrderItem> orderItems;
	
	public Order() {
		orderItems = new ArrayList<OrderItem>();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public float getTotalCost() {
		float total = 0.0f;
		
		for (OrderItem orderItem : orderItems) {
			total += orderItem.getTotalPrice();
		}
		
		return total;
	}
	
	public void setOrderItems(final List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "\n Order [orderId=" + orderId + " total Order cost: " + getTotalCost()+ ", orderItems=" + orderItems  + "]";
	}
}

