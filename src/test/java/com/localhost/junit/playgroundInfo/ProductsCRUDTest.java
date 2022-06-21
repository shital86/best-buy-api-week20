package com.localhost.junit.playgroundInfo;


import com.localhost.playgroundInfo.ProductsSteps;
import com.localhost.testbase.TestBase;
import com.localhost.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.annotation.Order;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBase {
    static String name = "Kim" + TestUtils.getRandomValue();
    static String type = "giftcards" + TestUtils.getRandomValue();
    static Integer price = 4;
    static Integer shipping = 18;
    static String upc = "039876655";
    static String description = "Silver Services";
    static String manufacturer = "Chessington";
    static String model = "Happy-89";
    static String url = "www.happy.com";
    static String image = "www.happy.com/images/12";
    static int productID;

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create a New product")
    @Test
    @Order(1)
    public void test001() {
        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");
        System.out.println(productID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    @Order(2)
    public void test002() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Update the Product information")
    @Test
    @Order(3)
    public void test003() {
        name = name + "_updated";
        productsSteps.updatingProduct(productID,name, type, price, shipping, upc, description, manufacturer, model, url, image);
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Delete the Product by ID")
    @Test
    @Order(4)
    public void test004() {
        productsSteps.deleteProduct(productID).statusCode(200);
        productsSteps.getProductByID(productID).statusCode(404);
    }

}
