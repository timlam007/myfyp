package com.ujjaval.ecommerce.commondataservice.controller;

import com.ujjaval.ecommerce.commondataservice.dto.ProductInfoDTO;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.ProductInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProduct;
import com.ujjaval.ecommerce.commondataservice.model.FilterAttributesResponse;
import com.ujjaval.ecommerce.commondataservice.model.HomeTabsDataResponse;
import com.ujjaval.ecommerce.commondataservice.model.MainScreenResponse;
import com.ujjaval.ecommerce.commondataservice.model.SearchSuggestionResponse;
import com.ujjaval.ecommerce.commondataservice.service.interfaces.CommonDataService;
import com.ujjaval.ecommerce.commondataservice.service.interfaces.LoadFakeDataService;
import com.ujjaval.ecommerce.commondataservice.service.interfaces.OrderInfoService;
import com.ujjaval.ecommerce.commondataservice.service.interfaces.VisitedProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;
import java.util.List;

@RestController
public class CommonDataController {

    @Autowired
    Environment environment;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    VisitedProductService visitedProductService;

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    LoadFakeDataService loadFakeDataService;

    public void fillWithTestData() {
        if (Objects.equals(environment.getProperty("ACTIVE_PROFILE"), "dev") && !visitedProductService.anyProductExists()) {
            loadFakeDataService.loadTestData();
        }
    }

    @GetMapping(value = "/products", params = "q")
    public ResponseEntity<?> getProductsByCategories(@RequestParam("q") String queryParams) {

        ProductInfoDTO productInfoDTO = commonDataService.getProductsByCategories(queryParams);

        if (productInfoDTO == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }

        return ResponseEntity.ok(productInfoDTO);
    }

    @GetMapping(value = "/products", params = {"product_id"})
    public ResponseEntity<?> getProductsById(@RequestParam("product_id") String productId, @RequestParam(value="user_id", defaultValue="0") String userId) {

        try {
            if(userId != "0" && userId != null){
                if(!visitedProductService.visitedProductExists(Integer.valueOf(userId), Integer.valueOf(productId))) {
                    VisitedProduct visitedProduct = new VisitedProduct();
                    visitedProduct.setUserId(Integer.valueOf(userId));
                    visitedProduct.setProductId(Integer.valueOf(productId));
                    visitedProductService.saveVisitedProduct(visitedProduct);
                }
            }
        }
        catch(Exception e) {

        }
        
        HashMap<Integer, ProductInfo> resultMap = commonDataService.getProductsById(productId);

        if (resultMap == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping(value = "/home", params = "user_id")
    public ResponseEntity<?> getMainScreenData(@RequestParam("user_id") String userId) {
        if (userId == null || userId == "null"){
            userId = "0";
        }

        MainScreenResponse mainScreenInfoList = commonDataService.getHomeScreenData("homeAPI", userId);
        if (mainScreenInfoList == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(mainScreenInfoList);
    }

    @GetMapping(value = "/orders", params = "user_id")
    public ResponseEntity<List<OrderItemInfo>> getOrdersData(@RequestParam("user_id") String userId) {
        return ResponseEntity.ok(orderInfoService.getUserOrderItems(userId));
    }

    @GetMapping("/tabs")
    public ResponseEntity<?> getHomeTabsDataResponse() {
        HomeTabsDataResponse homeTabsDataResponse = commonDataService.getBrandsAndApparelsByGender("tabsAPI");
        if (homeTabsDataResponse == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(homeTabsDataResponse);
    }

    @GetMapping(value = "/filter", params = "q")
    public ResponseEntity<?> getFilterAttributesByProducts(@RequestParam("q") String queryParams) {

        // TODO: Add support for productname parameter for filter selection.
        String[] splitParams = queryParams.split("=");
        if(splitParams.length >= 1 && splitParams[0].equals("productname")){
            queryParams="category=all";
        }

        FilterAttributesResponse result = commonDataService.getFilterAttributesByProducts(queryParams);

        if (result == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/order-info")
    public ResponseEntity<OrderInfo> saveOrderInfo(@RequestBody OrderInfo orderInfo) {

        for (OrderItemInfo item : orderInfo.getOrderItems()) {
            item.setOrderInfo(orderInfo);
        }

        orderInfoService.saveOrder(orderInfo);

        return ResponseEntity.ok(orderInfo);
    }

    @GetMapping("/search-suggestion-list")
    public ResponseEntity<?> getSearchSuggestionList() {
        SearchSuggestionResponse searchSuggestionList = commonDataService.getSearchSuggestionList();
        if (searchSuggestionList == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(searchSuggestionList);
    }
}
