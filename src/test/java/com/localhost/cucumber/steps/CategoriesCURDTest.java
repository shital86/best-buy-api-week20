package com.localhost.cucumber.steps;


import com.localhost.playgroundInfo.CategoriesSteps;
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

public class CategoriesCURDTest {
    static String name = "Gift Cards" + TestUtils.getRandomValue();
    static String id = "abcat0010001" + TestUtils.getRandomValue();
    static String categoryID;
    static ValidatableResponse response;

    @Steps
    CategoriesSteps categoriesSteps;

    @Given("^I am on homepage of categories endpoint$")
    public void iAmOnHomepageOfCategoriesEndpoint() {

    }

    @When("^I send a GET request to the categories endpoint$")
    public void iSendAGETRequestToTheCategoriesEndpoint() {
        response = categoriesSteps.getAllCategory().statusCode(200);
    }


    @Then("^I must get a valid response code (\\d+) from categories endpoint$")
    public void iMustGetAValidResponseCodeFromCategoriesEndpoint(int statusCode) {
        response.assertThat().statusCode(statusCode);
    }

    @When("^I send a POST request with a valid payload to the categories endpoint$")
    public void iSendAPOSTRequestWithAValidPayloadToTheCategoriesEndpoint() {
        response = categoriesSteps.createCategory(name, id);
        response.log().all().statusCode(201);
        categoryID = response.log().all().extract().path("id");
    }

    @When("^I send a GET request to newly created category with product ID$")
    public void iSendAGETRequestToNewlyCreatedCategoryWithProductID() {
        HashMap<String, ?> categoryMap = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(categoryMap, hasValue(name));
    }

    @And("^I verify if the category is created with correct details$")
    public void iVerifyIfTheCategoryIsCreatedWithCorrectDetails() {
        HashMap<String, ?> categoryMap = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(categoryMap, hasValue(name));
        Assert.assertThat(categoryMap, hasValue(id));
    }

    @When("^I send a PUT request to newly created category with product ID$")
    public void iSendAPUTRequestToNewlyCreatedCategoryWithProductID() {
        name = name + "_updated";
        categoriesSteps.updatingCategory(categoryID,name, id);

    }

    @And("^I verify if the category details is updated with correct details$")
    public void iVerifyIfTheCategoryDetailsIsUpdatedWithCorrectDetails() {
        HashMap<String, ?> productList = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(productList, hasValue(name));
    }

    @When("^I send a DELETE request to newly created category with product ID$")
    public void iSendADELETERequestToNewlyCreatedCategoryWithProductID() {
        categoriesSteps.deleteCategory(categoryID).statusCode(200);
    }

    @And("^I verify if the category is deleted$")
    public void iVerifyIfTheCategoryIsDeleted() {
        categoriesSteps.getCategoryByID(categoryID).statusCode(404);

    }
}
