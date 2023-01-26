package model;

public class Orderline {

	private int quantity;
	private Product item;
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return item;
	}
	
	public void setItem(Product item) {
		this.item = item;
	}
	
	public Orderline(int quantity, Product item) {
		super();
		this.quantity = quantity;
		this.item = item;
	}
	
	@Override
	public String toString() {
		return quantity + " cups "+ item.getName() +", unit price "+ item.getPrice();
	}
	
}
