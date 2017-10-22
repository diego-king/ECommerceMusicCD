package ca.edu.uottawa.csi5380.api;

import ca.edu.uottawa.csi5380.model.Cd;
import ca.edu.uottawa.csi5380.service.ProductCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Provides REST services for product category related queries.
 *
 * @author Kenny Byrd
 */
@RestController
@RequestMapping("/api/product")
@Api(value = "/api/product")
public class ProductCatController {

    private static final Logger LOGGER = LogManager.getLogger(ProductCatController.class);

    private final ProductCatService productCatService;

    @Autowired
    public ProductCatController(ProductCatService productCatService) {
        this.productCatService = productCatService;
    }

    @ApiOperation(value = "Get List of Category.", notes = "Get List of Category.", response = List.class)
    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCategoryList() {
        LOGGER.info(String.format("Call getCategoryList() function"));
        return productCatService.getCategoryList();
        
    }

    @ApiOperation(value = "Get all product List ", notes = "Get all product List", response = List.class)
    @RequestMapping(value = "/productList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cd> getProductList() {
        LOGGER.info(String.format("getProductList() with no parameters."));
        return productCatService.getProductList("");
    }

    @ApiOperation(value = "Get product List based on category.", notes = "Returns product List based on category.", response = List.class)
    @RequestMapping(value = "/productList/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cd> getProductList(@PathVariable("category") String category) {
        LOGGER.info(String.format("getProductList() called with category "+ category));
        return productCatService.getProductList(category);
    }

    @ApiOperation(value = "Get product Info based on id.", notes = "Returns product Info based on id.", response = Cd.class)
    @RequestMapping(value = "/productInfo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Cd getProductInfo(@PathVariable("id") String id) {
        LOGGER.info(String.format("getProductInfo() called with id "+ id));
        return productCatService.getProductInfo(id);
    }
}
