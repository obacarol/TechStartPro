package com.br.olist.dao;

import java.sql.PreparedStatement;
import java.util.List;
import com.br.olist.model.Category;

public class CategoryDAO {

	public boolean insertCategories(List<Category> categories) {
		
		try {
			String deleteCategories = "DELETE FROM CATEGORY ";
			PreparedStatement psDelete = ConnectionDerby.getConnection().prepareStatement(deleteCategories);
			psDelete.executeUpdate();
			
			for(Category category: categories) {
				String sql = "INSERT INTO CATEGORY VALUES ((NEXT VALUE FOR CATEGORY_SEQUENCE), ?)";
				
				PreparedStatement psInsertCtrPrd = ConnectionDerby.getConnection().prepareStatement(sql);
				psInsertCtrPrd.setString(1, category.getName());
				
				psInsertCtrPrd.executeUpdate();
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
