package ca.edu.uottawa.csi5380.database.agent.utils;

import ca.edu.uottawa.csi5380.model.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.util.ArrayList;
import java.util.List;

public final class DataUtils {

    private DataUtils() {
    }

    public static List<String> getCategoryListFromResult(SqlRowSet r) {
    	List<String> categoryList = new ArrayList<String>();
    	while (r.next()) {
    		categoryList.add(r.getString("category"));
    	}
    	return categoryList;
    }
    
    public static List<Cd> getProductList(SqlRowSet r) {
    	List<Cd> prodList = new ArrayList<Cd>();
    	while(r.next()) {
            CdCategory cdCategory = CdCategory.valueOf(r.getString("category"));
            prodList.add(new Cd(
                    r.getString("id"),
                    r.getString("title"),
                    r.getString("artist"),
                    r.getString("year"),
                    r.getString("description"),
                    r.getBigDecimal("price"),
                    r.getString("label"),
                    cdCategory,
                    r.getString("img_url")
                    ));
    	}
    	return prodList;
    }
    
    public static Cd getProductFromResult(SqlRowSet r) {
        if (!r.next()) {
        	return null;
        }
        CdCategory cdCategory = CdCategory.valueOf(r.getString("category"));
        return new Cd(
                r.getString("id"),
                r.getString("title"),
                r.getString("artist"),
                r.getString("year"),
                r.getString("description"),
                r.getBigDecimal("price"),
                r.getString("label"),
                cdCategory,
                r.getString("img_url")
                );
    }
}
