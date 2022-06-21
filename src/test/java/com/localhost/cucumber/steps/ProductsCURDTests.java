package com.localhost.cucumber.steps;


import com.localhost.playgroundInfo.ProductsSteps;
import com.localhost.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductsCURDTests {
    static String name = "Kalpesh" + TestUtils.getRandomValue();
    static String type = "giftcards" + TestUtils.getRandomValue();
    static Integer price = 6;
    static Integer shipping = 28;
    static String upc = "0123456789";
    static String description = "Available in 10, 20 & 30 USD";
    static String manufacturer = "Disney";
    static String model = "DIS_EU_10-20-30";
    static String url = "www.disney.com";
    static String image = "www.disney.com/images/diseu102030";
    static int productID;
    static ValidatableResponse response;

    @Steps
    ProductsSteps productsSteps;

    @Given("^I am on homepage of products endpoint$")
    public void iAmOnHomepageOfProductsEndpoint() {
    }

    @When("^I send a GET request to the products endpoint$")
    public void iSendAGETRequestToTheProductsEndpoint() {
        response = productsSteps.getAllProduct();
        response.statusCode(200);
    }

    @Then("^I must get a valid response code (\\d+) from products endpoint$")
    public void iMustGetAValidResponseCodeFromProductsEndpoint(int statusCode) {
        response.statusCode(statusCode);
        response.assertThat().statusCode(statusCode);
    }

    @When("^I send a POST request with a valid payload to the products endpoint$")
    public void iSendAPOSTRequestWithAValidPayloadToTheProductsEndpoint() {
        response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");
        System.out.println(productID);
    }

    @When("^I send a GET request to newly created product with product ID$")
    public void iSendAGETRequestToNewlyCreatedproductWithproductID() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @And("^I verify if the product is created with correct details$")
    public void iVerifyIfTheproductIsCreatedWithCorrectDetails() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        Assert.assertThat(productMap, hasValue(type));
        System.out.println(productMap);
    }

    @When("^I send a PUT request to newly created product with product ID$")
    public void iSendAPUTRequestToNewlyCreatedproductWithproductID() {
        name = name + "_updated";
        productsSteps.updatingProduct(productID, name, type, price, shipping, upc, description, manufacturer, model, url, image);

    }

    @And("^I verify if the product details is updated with correct details$")
    public void iVerifyIfTheproductDetailsIsUpdatedWithCorrectDetails() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @When("^I send a DELETE request to newly created product with product ID$")
    public void iSendADELETERequestToNewlyCreatedproductWithproductID() {
        productsSteps.deleteProduct(productID).statusCode(200);
    }

    @And("^I verify if the product is deleted$")
    public void iVerifyIfTheproductIsDeleted() {
        productsSteps.getProductByID(productID).statusCode(404);

    }

}
