package dataAccess.memory;

import java.util.ArrayList;
import java.util.List;

import dataAccess.Dao;
import dataAccess.DaoException;
import model.Product;

public class ProductDao implements Dao<Product>{

	List<Product> products = new ArrayList<>();
	
	
	
	public ProductDao() {
		super();

		products.add(new Product("Americano", "Espresso shots topped with hot water create a light layer of crema culminating in this wonderfully rich cup with depth and nuance.", 32.50));
        products.add(new Product("Dark Roast Coffee", "This full-bodied dark roast coffee with bold, robust flavors showcases our roasting and blending artistry—an essential blend of balanced and lingering flavors.", 32.50));
        products.add(new Product("Misto", "A one-to-one combination of fresh-brewed coffee and steamed milk add up to one distinctly delicious coffee drink remarkably mixed.", 32.50));
        products.add(new Product("Cappuccino","Dark, rich espresso lies in wait under a smoothed and stretched layer of thick milk foam. An alchemy of barista artistry and craft. ", 29.50));
        products.add(new Product("Espresso","Our smooth signature Espresso Roast with rich flavor and caramelly sweetness is at the very heart of everything we do.", 19.50));
        products.add(new Product("Latte",  "Our dark, rich espresso balanced with steamed milk and a light layer of foam. A perfect milk-forward warm-up.", 29.50));
        products.add(new Product("Cinnamon Dolce Latte", "We add freshly steamed milk and cinnamon dolce-flavored syrup to our classic espresso, topped with sweetened whipped cream and a cinnamon dolce topping to bring you specialness in a treat.", 32.50));
        products.add(new Product("Flat White", "Smooth ristretto shots of espresso get the perfect amount of steamed whole milk to create a not-too-strong, not-too-creamy, just-right flavor.", 29.50));
        products.add(new Product("Caramel Macchiato", "Freshly steamed milk with vanilla-flavored syrup marked with espresso and topped with a caramel drizzle for an oh-so-sweet finish.", 29.50));

	}

	@Override
	public Product create(Product product) {
		// Not needed
		throw new DaoException("Create product is not available");
	}

	@Override
	public List<Product> read() {
		// returns all products
		return products;
	}

	@Override
	public Product update(Product product) {
		// Not needed
		throw new DaoException("Update product is not available");
	}

	@Override
	public boolean delete(Product product) {
		// Not needed
		throw new DaoException("Delete product is not available");
	}
}
