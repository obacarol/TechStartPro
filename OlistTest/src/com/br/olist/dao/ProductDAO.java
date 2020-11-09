package com.br.olist.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.br.olist.model.Category;
import com.br.olist.model.Product;

public class ProductDAO {

	public boolean updateProduct(Product product) {
		String sql = "UPDATE PRODUCT SET \n" + 
				"NAME  = ?, \n" + 
				"DESCRIPTION = ?, \n" + 
				"VALUE = ? \n" + 
				"WHERE ID = " + product.getId();
		try {
			PreparedStatement ps = ConnectionDerby.getConnection().prepareStatement(sql);

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getValue());

			ps.executeUpdate();

			deleteRelationCategoryProduct(product);

			associateCategorysToProduct(product.getCategories(), product.getId());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void deleteRelationCategoryProduct(Product product) throws SQLException {
		String deleteCategoryProducts = "DELETE FROM CATEGORY_PRODUCT WHERE ID_PRODUCT = " + product.getId();
		PreparedStatement psDelete = ConnectionDerby.getConnection().prepareStatement(deleteCategoryProducts);
		psDelete.executeUpdate();
		
	}
	
	private void associateCategorysToProduct(List<Category> listCategorys, Integer idProduct) throws SQLException {
		if (listCategorys != null && !listCategorys.isEmpty()) {
			String sqlNewCatPrd = "INSERT INTO CATEGORY_PRODUCT (ID_PRODUCT, ID_CATEGORY) \r\n" + "VALUES \r\n";
			for (Category ctr : listCategorys) {
				sqlNewCatPrd += "(" + idProduct + "," + ctr.getId() + "),";
			}
			sqlNewCatPrd = sqlNewCatPrd.substring(0, sqlNewCatPrd.length() - 1);
			
			PreparedStatement psInsertCtrPrd = ConnectionDerby.getConnection().prepareStatement(sqlNewCatPrd);
			psInsertCtrPrd.executeUpdate();
		}
	}
	public boolean insertProduct(Product product) {
		
		String sqlSequence = "SELECT NEXT VALUE FOR PRODUCT_SEQUENCE as seqPrd FROM SYSIBM.SYSDUMMY1";
		String sql = "INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, VALUE) VALUES (?, ?, ?, ?)";
		
		try {
			Statement statement = ConnectionDerby.getConnection().createStatement();
			ResultSet rsSequence = statement.executeQuery(sqlSequence);
			rsSequence.next();
			Integer nextId = rsSequence.getInt("seqPrd");
			
			product.setId(nextId);
			
			PreparedStatement ps = ConnectionDerby.getConnection().prepareStatement(sql);
			ps.setInt(1, product.getId());
			ps.setString(2, product.getName());
			ps.setString(3, product.getDescription());
			ps.setDouble(4, product.getValue());

			ps.executeUpdate();
			
			associateCategorysToProduct(product.getCategories(), product.getId());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Product> searchProduct(Product product) {
		
		String sql = "SELECT\r\n" + 
				"	p.ID as productId,\r\n" + 
				"	p.NAME as productName,\r\n" + 
				"	p.DESCRIPTION as productDescription,\r\n" + 
				"	p.VALUE as productValue,\r\n" + 
				"	c.ID as categoryId,\r\n" + 
				"	c.NAME as categoryName\r\n" + 
				"FROM PRODUCT p\r\n" + 
				"LEFT JOIN CATEGORY_PRODUCT cp ON cp.ID_PRODUCT = p.ID\r\n" + 
				"LEFT JOIN CATEGORY c ON CP.ID_CATEGORY = c.ID\r\n" + 
				"WHERE \r\n" + 
				"	LOWER(p.NAME) LIKE ? \r\n" + 
				"AND 	LOWER(p.DESCRIPTION) LIKE ? \r\n";
		
		if(product.getId() != null) 
			sql += "AND 	p.ID = ?";
		else if(product.getValue() != null) 
				sql += "AND p.VALUE = ?";
		
		try {
			PreparedStatement ps = ConnectionDerby.getConnection().prepareStatement(sql);
			
			ps.setString(1, "%"+product.getName().toLowerCase()+"%");
			ps.setString(2, "%"+product.getDescription().toLowerCase()+"%");
			
			if(product.getValue() != null)
				ps.setDouble(3, product.getValue());
			
			if(product.getId() != null)
				ps.setInt(3, product.getId());
			

			ResultSet rs = ps.executeQuery();
			
			List<Product> allProducts = new ArrayList<Product>();
			while(rs.next()) {
				Product prd = new Product();
				
				Integer idPrd = rs.getInt("productId");
				String namePrd = rs.getString("productName");
				String descriptionPrd = rs.getString("productDescription");
				Double valuePrd = rs.getDouble("productValue");
				Integer idCtr = rs.getInt("categoryId");
				String nameCtr = rs.getString("categoryName");
				
				prd.setId(idPrd);
				prd.setDescription(descriptionPrd);
				prd.setName(namePrd);
				prd.setValue(valuePrd);
				
				Category category = new Category();
				category.setId(idCtr);
				category.setName(nameCtr);
				prd.setCategoryTemp(category);

				allProducts.add(prd);
			}
			
			List<Product> cleanProducts = new ArrayList<Product>();
			for(Product prd : allProducts) {
				HashSet<Category> catPrd = new HashSet<Category>();
				for(Product prdInternal : allProducts) {
					if(prd.equals(prdInternal)) {
						catPrd.add(prdInternal.getCategoryTemp());
					}
				}
				prd.setCategories(new ArrayList<Category>(catPrd));
				cleanProducts.add(prd);
			}
			cleanProducts = new ArrayList<Product>(new HashSet<Product>(cleanProducts));
			return cleanProducts;
		} catch (SQLException e) {
			e.printStackTrace();
			//return false;
		}
		
		return null;
	}

	public boolean deleteAllProducts() {
		
		String sql = "DELETE FROM PRODUCT";
		try {
			PreparedStatement ps = ConnectionDerby.getConnection().prepareStatement(sql);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(int idProduct) {
		String sql = "DELETE FROM PRODUCT WHERE id = ?";
		try {
			PreparedStatement ps = ConnectionDerby.getConnection().prepareStatement(sql);
			ps.setInt(1, idProduct);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
