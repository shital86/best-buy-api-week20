package com.localhost.cucumber.steps;


import com.localhost.playgroundInfo.StoresSteps;
import com.localhost.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasValue;

public class StoresCRUDTests {
    static String name = "Kalpesh" + TestUtils.getRandomValue();
    static String type = "giftcards" + TestUtils.getRandomValue();
    static String address = "11 New Road";
    static String address2 = "75001 India";
    static String city = "Mumbai";
    static String state = "Antwerp";
    static String zip = "75411";
    static int lat = 45;
    static int lng = 78;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9;Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;
    static ValidatableResponse response;

    @Steps
    StoresSteps storesSteps;

    @Given("^I am on homepage of stores endpoint$")
    public void iAmOnHomepageOfStoresEndpoint() {
    }

    @When("^I send a GET request to the stores endpoint$")
    public void iSendAGETRequestToTheStoresEndpoint() {
        response = storesSteps.getAllStores().statusCode(200);
    }

    @Then("^I must get a valid response code (\\d+) from stores endpoint$")
    public void iMustGetAValidResponseCodeFromStoresEndpoint(int statusCode) {
        response.statusCode(statusCode);
        response.assertThat().statusCode(statusCode);
    }

    @When("^I send a POST request with a valid payload to the stores endpoint$")
    public void iSendAPOSTRequestWithAValidPayloadToTheStoresEndpoint() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @When("^I send a GET request to newly created store with product ID$")
    public void iSendAGETRequestToNewlyCreatedStoreWithProductID() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoByName(storeID);
    }

    @And("^I verify if the store is created with correct details$")
    public void iVerifyIfTheStoreIsCreatedWithCorrectDetails() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
        Assert.assertThat(storeMap, hasValue(type));
        Assert.assertThat(storeMap, hasValue(city));
    }

    @When("^I send a PUT request to newly created store with product ID$")
    public void iSendAPUTRequestToNewlyCreatedStoreWithProductID() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        storesSteps.updateStore(storeID, name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
    }

    @And("^I verify if the store details is updated with correct details$")
    public void iVerifyIfTheStoreDetailsIsUpdatedWithCorrectDetails() {
        HashMap<String, ?> productList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(productList, hasValue(name));
    }

    @When("^I send a DELETE request to newly created store with product ID$")
    public void iSendADELETERequestToNewlyCreatedStoreWithProductID() {
        storesSteps.deleteStore(storeID).statusCode(200);
    }

    @And("^I verify if the store is deleted$")
    public void iVerifyIfTheStoreIsDeleted() {
        storesSteps.getStoreByID(storeID).statusCode(404);
    }

    @And("^I verify the if the total is equal to (\\d+)$")
    public void iVerifyTheIfTheTotalIsEqualTo(int expected) {
        response = storesSteps.getAllStores();
        int total = response.extract().path("total");
        Assert.assertEquals(expected, total);
    }

    @And("^I verify the if the stores of limit is equal to (\\d+)$")
    public void iVerifyTheIfTheStoresOfLimitIsEqualTo(int expected) {
        response = storesSteps.getAllStores();
        int limit = response.extract().path("limit");
        Assert.assertEquals(expected, limit);
    }

    @And("^I Check the single name \"([^\"]*)\"$")
    public void iCheckTheSingleName(String expected) {
        response = storesSteps.getAllStores();
        List<String> name = response.extract().path("data.name");
        Assert.assertThat(name, hasItem(expected));
    }

    @And("^I Check the multiple names \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void iCheckTheMultipleNames(String name1, String name2, String name3) {
        response = storesSteps.getAllStores();
        List<String> names = response.extract().path("data.name");
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add(name1);
        expectedNames.add(name2);
        expectedNames.add(name3);
        for (String data : expectedNames) {
            Assert.assertThat(names, hasItem(data));
        }
    }


    @And("^I Verify the storied (\\d+) inside storeservices of the third store of second services$")
    public void iVerifyTheStoriedInsideStoreservicesOfTheThirdStoreOfSecondServices(int expected) {
        response = storesSteps.getAllStores();
        int storeID = response.extract().path("data[2].services[1].storeservices.storeId");
        Assert.assertEquals(expected, storeID);
    }

    @And("^I Check hash map values createdAt inside storeservices map where store name is \"([^\"]*)\"$")
    public void iCheckHashMapValuesCreatedAtInsideStoreservicesMapWhereStoreNameIs(String arg0) {
        response = storesSteps.getAllStores();
        List<String> createdAt = response.extract().path("data.findAll{it.name=='Roseville'}.services[0].storeservices.createdAt");
        for (String data : createdAt) {
            Assert.assertEquals("2016-11-17T17:57:09.417Z", data);
        }
    }

    @And("^I Verify the state \"([^\"]*)\" of forth store$")
    public void iVerifyTheStateOfForthStore(String expected) {
        response = storesSteps.getAllStores();
        String stateName = response.extract().path("data[3].state");
        Assert.assertEquals(expected, stateName);
    }

    @And("^I Verify the store name \"([^\"]*)\" of ninth store$")
    public void iVerifyTheStoreNameOfNinthStore(String expected) {
        response = storesSteps.getAllStores();
        String storeName = response.extract().path("data[8].name");
        Assert.assertEquals(expected, storeName);
    }

    @And("^I Verify the storeId is (\\d+) for the sixth store$")
    public void iVerifyTheStoreIdIsForTheSixthStore(int expected) {
        response = storesSteps.getAllStores();
        List<Integer> storeIDSixthStore = response.extract().path("data[5].services.storeservices.storeId");
        for (int data : storeIDSixthStore) {
            Assert.assertEquals(expected, data);
        }
    }

    @And("^Verify the serviceId is (\\d+) for the seventh store of forth service$")
    public void verifyTheServiceIdIsForTheSeventhStoreOfForthService(int expected) {
        response = storesSteps.getAllStores();
        int serviceID = response.extract().path("data[6].services[3].storeservices.serviceId");
        Assert.assertEquals(expected, serviceID);
    }
}
