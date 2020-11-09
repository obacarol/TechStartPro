package com.br.olist.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.br.olist.controller.ConsoleController;
import com.br.olist.model.Product;
import com.br.olist.util.OlistUtil;

public class Console {

	public static void main(String[] args) {
		
		ConsoleController consoleController = new ConsoleController();
	
		Console console = new Console();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Integer optionMain = -1;

		while (optionMain != 6) {
			console.showMainOptions();
			optionMain = console.readInputLine(reader);

			switch (optionMain) {
			case 1:
				console.processImportCsvFile(reader, consoleController);
				break;
			
			case 2:
				console.processInsertProductOption(reader, consoleController);
				break;
				
			case 3:
				console.processReadProductOption(reader, consoleController);
				break;
				
			case 4:
				console.processUpdateProductOption(reader, consoleController);
				break;
				
			case 5:
				console.showDeleteProductOption();
				Integer option = console.readInputLine(reader);
				consoleController.deleteProduct(option);
				break;
				
			case 6:
				break;
			default:
				System.out.println("Invalid option!");
				break;
			}
		}

		System.out.println("Bye!");
	}

	
	public void processImportCsvFile(BufferedReader reader, ConsoleController consoleController) {
		try {
			System.out.println("Enter the path to the CSV file:");
			String filePath = reader.readLine();
			consoleController.importCategoryFromCsvFile(filePath);
		}catch (IOException e) {
			System.out.println("Exception to read input values.");
		}
	}
	
	
	public void processInsertProductOption(BufferedReader reader, ConsoleController consoleController) {
		try {
				Product product = new Product();
				System.out.println("Enter the name of the new product:");
				String name = reader.readLine();
				product.setName(name);
				
				System.out.println("Enter the description of the new product:");
				String description = reader.readLine();
				product.setDescription(description);
				
				System.out.println("Enter the value of the new product:");
				String valueStr = reader.readLine();
				if(valueStr != null && !valueStr.isEmpty()) {
					Double value = OlistUtil.convertStringToDouble(valueStr);
					product.setValue(value);
				}
				
				System.out.println("Enter the categories of the new product separated by commas:");
				String categorysStr = reader.readLine();
				consoleController.insertProduct(product, categorysStr);
				
		}catch (IOException e) {
			System.out.println("Exception to read input values.");
		}
	}

	
	public void processReadProductOption(BufferedReader reader, ConsoleController consoleController) {
		try {
			showReadProductOption();
			System.out.println("Enter the name or enter to ignore:");
			String name = reader.readLine();
			System.out.println("Enter the description or enter to ignore:");
			String description = reader.readLine();;
			System.out.println("Enter the value or enter to ignore:");
			String valueStr = reader.readLine();
			List<Product> list = consoleController.searchProduct(null, name, description, valueStr, null);
			
			if(list != null && !list.isEmpty()) {
				System.out.println("The result is:");
				for(Product prd : list) {
					System.out.println(prd);
				}
			}else {
				System.out.println("Product not found.");
			}
			
		}catch (IOException e) {
			System.out.println("Exception to read input values.");
		}
	}
	
	
	public void processUpdateProductOption(BufferedReader reader, ConsoleController consoleController) {
		try {
			System.out.println("Enter the product ID:");
			Integer idToUpdate = readInputLine(reader);
			List<Product> list = consoleController.searchProduct(idToUpdate, "", "", "", null);
			Product product = list.get(0);
			if(list != null && !list.isEmpty()) {
				System.out.println("The current product is: " + product);
				
				System.out.println("Enter the new name or enter to ignore");
				String name = reader.readLine();
				if(name != null && !name.isEmpty())
					product.setName(name);
				
				System.out.println("Enter the new description or enter to ignore:");
				String description = reader.readLine();
				if(description != null && !description.isEmpty())
					product.setDescription(description);
				
				System.out.println("Enter the new value or enter to ignore:");
				String valueStr = reader.readLine();
				if(valueStr != null && !valueStr.isEmpty()) {
					Double value = OlistUtil.convertStringToDouble(valueStr);
					product.setValue(value);
				}
				
				System.out.println("Enter the new categories or enter to ignore:");
				String categorysStr = reader.readLine();
				consoleController.updateProduct(product, categorysStr);
				
			}else {
				System.out.println("No product with ID:" + idToUpdate);
			}
		}catch (IOException e) {
			System.out.println("Exception to read input values.");
		}
	}
	

	private void showReadProductOption() {
		System.out.println("To read a product complete the form:");
	}

	public Integer readInputLine(BufferedReader reader) {
		try {
			String optionNum = reader.readLine();
			return Integer.valueOf(optionNum);
		} catch (Exception e) {
			//EU PODERIA COLOCAR UM IllegalArgumentException ????
			return -1;
		}
	}

	public void showMainOptions() {
		System.out.println("\nChoose the option:");
		System.out.println("1 - Import categories from CSV file");
		System.out.println("2 - Insert a new product");
		System.out.println("3 - Read a product");
		System.out.println("4 - Update a product");
		System.out.println("5 - Delete a product");
		System.out.println("6 - To exit");
	}
	
	public void showDeleteProductOption() {
		System.out.println("\n");
		System.out.println("Enter the product ID you want to delete:");
	}
	
}
