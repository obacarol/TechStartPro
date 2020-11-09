package com.br.olist.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.br.olist.OlistFacade;
import com.br.olist.model.Category;
import com.br.olist.model.Product;
import com.br.olist.util.OlistUtil;

public class ConsoleController {

	private OlistFacade facade;
	
	public ConsoleController() {
		facade = new OlistFacade();
	}
	
	public void importCategoryFromCsvFile(String filePath) {
		List<String> categoryLines = convertCsvToArray(filePath);
		
		List<Category> categories = new ArrayList<Category>();
		
		if(categoryLines == null || categoryLines.isEmpty()) {
			System.out.println("Exception to import CSV file.");
			return;
		}
		
		
		for(String line : categoryLines) {
			Category ctr = new Category();
			ctr.setName(line);
			categories.add(ctr);
		}
		
		boolean success = getFacade().insertCategories(categories);
		if(success)
			System.out.println("Success to import categories!");
		else
			System.out.println("EXCEPTION to import categories.");
	}
	
	public void insertProduct(Product product, String categoriesStr) {
		
		boolean checkInput = checkInputProduct(product.getName(), product.getDescription(), product.getValue());
		if(checkInput) {
			List<Category> categories = convertCsvToCategory(product, categoriesStr);
			
			product.setCategories(categories);
			getFacade().insertProduct(product);
			
			boolean success = facade.insertProduct(product);
			if(success)
				System.out.println("The product has been successfully inserted.");
			else
				System.out.println("Exception to insert the product.");
		}
	}

	public void updateProduct(Product product, String categoriesStr) {
		List<Category> categories = convertCsvToCategory(product, categoriesStr);
		
		product.setCategories(categories);
		
		boolean success = getFacade().updateProduct(product);
		if(success)
			System.out.println("The product has been successfully inserted.");
		else
			System.out.println("Exception to insert the product.");
	}

	private List<Category> convertCsvToCategory(Product product, String categoriesStr) {
		if(categoriesStr != null && !categoriesStr.trim().isBlank()) {
			categoriesStr = categoriesStr.replaceAll(" ", "");
			String [] splitCategory = categoriesStr.split(",");
			
			List<Category> categories = new ArrayList<Category>();
			for(String newCategory: splitCategory) {
				Category category = new Category();
				category.setId(Integer.valueOf(newCategory));
				categories.add(category);
			}
			
			return categories;
		}
		
		return null;
	}
	public void deleteProduct(Integer idProduct) {
		if(idProduct < 0) {
			System.out.println("The product ID is invalid.");
		}
		
		boolean success = facade.deleteProduct(idProduct);
		if(success)
			System.out.println("The product was deleted.");
		else
			System.out.println("Exception to delete product.");
	}
	
	protected boolean checkInputProduct(String name, String description, Double valueDouble) {
		
		if(name == null || name.trim().isEmpty()) {
			System.out.println("The name is required.");
			return false;
		}else if(description == null || description.trim().isEmpty()) {
			System.out.println("The description is required.");
			return false;
		}else if(valueDouble == null || valueDouble < 0) {
			System.out.println("The value is required.");
			return false;
		}
		
		return true;
	}
	
	public List<Product> searchProduct(Integer id, String name, String description, String valueStr, String categories) {
		
		Double value = OlistUtil.convertStringToDouble(valueStr); 
		if(value == -1d)
			value = null;

		
		Product p = new Product();
		p.setId(id);
		p.setName(name);
		p.setDescription(description);
		p.setValue(value);
		
		List<Product> list = getFacade().searchProduct(p);
		
		return list;
	}
	
	
	public List<String> convertCsvToArray(String filePath) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(filePath));
			
			List<String> fileLines = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				fileLines.add(line);
			}
			return fileLines;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public OlistFacade getFacade() {
		return facade;
	}

	public void setFacade(OlistFacade facade) {
		this.facade = facade;
	}

}
