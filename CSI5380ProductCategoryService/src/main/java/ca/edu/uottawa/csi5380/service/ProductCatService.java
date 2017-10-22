package ca.edu.uottawa.csi5380.service;

import ca.edu.uottawa.csi5380.database.agent.product.ProductCatDao;
import ca.edu.uottawa.csi5380.database.agent.product.ProductCatDaoImpl;
import ca.edu.uottawa.csi5380.model.Cd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCatService implements ProductCatDao {

    private final ProductCatDaoImpl productCatDaoImpl;

    @Autowired
    public ProductCatService(ProductCatDaoImpl productCatDaoImpl) {
        this.productCatDaoImpl = productCatDaoImpl;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCategoryList() {
        return productCatDaoImpl.getCategoryList();
    }

	@Override
    @Transactional(readOnly = true)
	public List<Cd> getProductList(String category) {
		return productCatDaoImpl.getProductList(category);
	}

	@Override
    @Transactional(readOnly = true)
	public Cd getProductInfo(String productid) {
		return productCatDaoImpl.getProductInfo(productid);
	}

}
