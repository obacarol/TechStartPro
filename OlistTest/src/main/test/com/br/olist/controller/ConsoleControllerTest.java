package com.br.olist.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.br.olist.model.Product;

public class ConsoleControllerTest {
	
	private ConsoleController consoleController;
	
	public ConsoleControllerTest(){
		this.consoleController = new ConsoleController();
	}
	
	
	@Test
	public void importCategoryFromCsvFile_InvalidPath() {
		String path = "c:\\temp\\category.csv";

		List<String> categories = consoleController.convertCsvToArray(path);
		Assert.assertNull(categories);
	}
	
	@Test
	public void importCategoryFromCsvFile_ValidPath() {
		String path = "D:\\OneDrive\\GIT\\TesteTecnico_Olist\\TechStartPro\\OlistTest\\resources\\exemplo.csv";

		List<String> categories = consoleController.convertCsvToArray(path);
		Assert.assertNotNull(categories);
	}
	
	@Test
	public void insertProduct_NameIsNull() {
		String name = null;
		String description = "Bed";
		Double value = 12.33;
		
		boolean success = getConsoleController().checkInputProduct(name, description, value);
		Assert.assertFalse("The name product is required", success);
	}
	
	@Test
	public void searchProduct_bigDescription() {
		Integer id = -1;
		String name = "";
		String description = "Lorem ipsum dolor sit amet, qui ut mollis facilisi, est sale assueverit eu. Pri in meis ponderum, maiorum accommodare mei ei. Cu mei nostro voluptatibus, cum ei rebum vivendum persecuti, pro fabulas officiis ne. An eam quem mollis impetus. Minim maiestatis ea qui, te semper laboramus sadipscing quo. Pri dicta delicata mediocrem id, sit et aeque percipitur conclusionemque.\r\n"
				+ "Te veniam postulant assueverit qui. Et eos dicant maluisset, debitis facilisi ea eum, autem epicuri per ut. An mea legendos qualisque. Eum alii apeirian an, constituto cotidieque suscipiantur mea id. Sit cetero scribentur in. Eu porro liber quo.\r\n"
				+ "Pro falli hendrerit ex, in duo latine laoreet albucius. Sit eu veniam assueverit, nostrum postulant maiestatis sit at, odio omittam ponderum in ius. Sed et atomorum intellegat. Ex mea natum dolorum, ad vero vulputate sed. Eu mel eros nusquam democritum, no sit dicunt nominavi, senserit intellegam consectetuer ea mea.\r\n"
				+ "Ut ius etiam platonem pericula. Vis eu viderer mediocrem democritum. Vix oratio prompta interesset eu, munere postea quaestio mel ut. Ut eam ludus graeco pericula. His ignota invidunt et, vix ad putent iuvaret.\r\n"
				+ "An nec ignota admodum minimum, malis platonem expetendis ut vis, his quando fabulas in. Etiam scripserit at mea, mollis iuvaret mea id, sit agam tollit probatus ei. Cu est illud virtute theophrastus, oportere erroribus te usu. Cu amet commune euripidis sit, an cum veniam euripidis, ei est ocurreret suscipiantur. Mei officiis neglegentur ad, vim eu altera mentitum splendide, unum delectus per at. Tation appellantur mel id. Vel ea aliquando scripserit referrentur, delenit consulatu eam ad, labore commodo eleifend ut cum.";
		String valueStr = "12.12";
		String categories = "";
		
		 List<Product> products = getConsoleController().searchProduct(id, name, description, valueStr, categories);
		 Assert.assertTrue(products.isEmpty());
	}
	
	@Test
	public void searchProduct_ValidValue() {
		Integer id = -1;
		String name = "";
		String description = "";
		String valueStr = "12.9";
		String categories = "";
		
		 List<Product> products = getConsoleController().searchProduct(id, name, description, valueStr, categories);
		 Assert.assertTrue(products.isEmpty());
	}
	
	
	public ConsoleController getConsoleController() {
		return consoleController;
	}
	public void setConsoleController(ConsoleController consoleController) {
		this.consoleController = consoleController;
	}
		
}
