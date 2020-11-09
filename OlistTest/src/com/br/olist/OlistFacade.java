package com.br.olist;

import java.util.List;

import com.br.olist.dao.CategoryDAO;
import com.br.olist.dao.ProductDAO;
import com.br.olist.model.Category;
import com.br.olist.model.Product;

public class OlistFacade {

	private ProductDAO productDao;
	private CategoryDAO categoryDao;

	public OlistFacade() {
		productDao = new ProductDAO();
		categoryDao = new CategoryDAO();
	}
	
	public boolean insertProduct(Product product) {
		return getProductDao().insertProduct(product);
	}
	
	public boolean deleteAllProducts() {
		return getProductDao().deleteAllProducts();
	}
	
	public boolean deleteProduct(int idProduct) {
		return getProductDao().deleteProduct(idProduct);
	}
	
	public List<Product> searchProduct(Product product) {
		return getProductDao().searchProduct(product);
	}
	
	public boolean updateProduct(Product product) {
		return getProductDao().updateProduct(product);
	}

	public boolean insertCategories(List<Category> categories) {
		return getCategoryDao().insertCategories(categories);
	}
	
	public ProductDAO getProductDao() {
		return productDao;
	}

	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}

}
