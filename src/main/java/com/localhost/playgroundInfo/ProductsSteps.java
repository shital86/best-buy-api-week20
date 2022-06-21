package com.localhost.playgroundInfo;


import com.localhost.constants.EndPoints;
import com.localhost.models.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {
    @Step("Creating Products with name : {0}, type: {1}, price: {2}, shipping: {3}, upc: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name,
                                             String type,
                                             int price,
                                             int shipping,
                                             String upc,
                                             String description,
                                             String manufacturer,
                                             String model,
                                             String url,
                                             String image) {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCTS)
                .then().log().ifValidationFails();
    }

    @Step("Getting the Product information with ID: {0}")
    public HashMap<String,?> getProductInfoByName(int productID) {
        HashMap<String,?> productMap = SerenityRest.given().log().ifValidationFails()
                .when()
                .pathParam("productID",productID)
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().path("");

        return productMap;
    }

    @Step("Updating Products with ID {0},name: {1}, type: {2}, price: {3}, shipping: {4}, upc: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updatingProduct(int productID,
                                               String name,
                                               String type,
                                               int price,
                                               int shipping,
                                               String upc,
                                               String description,
                                               String manufacturer,
                                               String model,
                                               String url,
                                               String image){

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .pathParam("productID",productID)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then().log().ifValidationFails();
    }

    @Step("Deleting Products with ID {0}")
    public ValidatableResponse deleteProduct(int productID){
        return SerenityRest.given().log().ifValidationFails()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then().log().ifValidationFails();
    }

    @Step("Getting Products with ID {0}")
    public ValidatableResponse getProductByID(int productID){
        return SerenityRest.given().log().ifValidationFails()
                .pathParam("productID", productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().ifValidationFails();
    }

    @Step("Getting All Products")
    public ValidatableResponse getAllProduct(){
        return SerenityRest.given().log().ifValidationFails()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().log().ifValidationFails();
    }
}
