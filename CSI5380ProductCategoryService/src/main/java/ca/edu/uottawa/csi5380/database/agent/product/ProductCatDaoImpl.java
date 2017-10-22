package ca.edu.uottawa.csi5380.database.agent.product;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
import ca.edu.uottawa.csi5380.exception.RestDaoException;
import ca.edu.uottawa.csi5380.model.Cd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCatDaoImpl implements ProductCatDao {

    // SQL SELECT KEYS
    private static final String SelectCategorySQL = "sql.select.category";
    private static final String SelectProductListByCategory = "sql.select.productList.by.category";
    private static final String SelectProductById = "sql.select.product.by.id";
    private static final String SelectAllProductList = "sql.select.productList";

    private final DataAgent dataAgent;

    @Autowired
    public ProductCatDaoImpl(DataAgent dataAgent) {
        this.dataAgent = dataAgent;
    }

	@Override
	public List<String> getCategoryList() {
		return DataUtils.getCategoryListFromResult(dataAgent.getQueryResult(SelectCategorySQL, new Object[]{}));
	}

	@Override
	public List<Cd> getProductList(String category) {
		if ("".equals(category) || category == null) {
			return DataUtils.getProductList(dataAgent.getQueryResult(SelectAllProductList, new Object[]{}));
		} else {
			return DataUtils.getProductList(dataAgent.getQueryResult(SelectProductListByCategory, new Object[]{category}));
		}
	}

	@Override
	public Cd getProductInfo(String productid) {
		return DataUtils.getProductFromResult(dataAgent.getQueryResult(SelectProductById, new Object[]{productid}));
	}

}
