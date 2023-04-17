package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String customerName;
	private Date date;
	private String status;
	private List<OrderLine> orderlines;

	public static final String STATUS_NEW = "New";
	public static final String STATUS_ACTIVE = "Processing";
	public static final String STATUS_READY = "Ready";
	public static final String STATUS_FINISHED = "Finished";

	public String getCustomerName() {
		return customerName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderLine> getOrderlines() {
		return orderlines;
	}

	public void addOrderline(OrderLine ol) {
		orderlines.add(ol);
	}

	public Order(String customerName) {
		super();
		this.customerName = customerName;
		this.date = new Date();
		this.status = STATUS_NEW;
		this.orderlines = new ArrayList<OrderLine>();
	}

	@Override
	public String toString() {
		int qty = 0;
		for (OrderLine ol : orderlines) {
			qty += ol.getQuantity();
		}
		return customerName + ": " + qty + " cups - " + status;
	}

	public float getTotalPrice() {
		float result = 0;
		for (OrderLine ol : orderlines) {
			result += ol.getQuantity() * ol.getProduct().getPrice();
		}
		return result;
	}
}
