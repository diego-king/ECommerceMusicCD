package ca.edu.uottawa.csi5380.database.agent.product;
import java.util.List;
import ca.edu.uottawa.csi5380.model.Cd;

public interface ProductCatDao {
    List<String> getCategoryList();
    List<Cd> getProductList(String categoryid);
    Cd getProductInfo(String productid);
}
