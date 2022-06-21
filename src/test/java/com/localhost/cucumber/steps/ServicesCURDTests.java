package com.localhost.cucumber.steps;


import com.localhost.playgroundInfo.ServicesSteps;
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

public class ServicesCURDTests {
    static String name = "QA Tester" + TestUtils.getRandomValue();
    static int serviceID;
    static ValidatableResponse response;


    @Steps
    ServicesSteps servicesSteps;

    @Given("^I am on homepage of services endpoint$")
    public void iAmOnHomepageOfServicesEndpoint() {
    }


    @When("^I send a GET request to the services endpoint$")
    public void iSendAGETRequestToTheServicesEndpoint() {
        response = servicesSteps.getAllService().statusCode(200);
    }

    @Then("^I must get a valid response code (\\d+) from services endpoint$")
    public void iMustGetAValidResponseCodeFromServicesEndpoint(int statusCode) {
        response.assertThat().statusCode(statusCode);

    }

    @When("^I send a POST request with a valid payload to the services endpoint$")
    public void iSendAPOSTRequestWithAValidPayloadToTheServicesEndpoint() {
        response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
        serviceID = response.log().all().extract().path("id");
        System.out.println(serviceID);
    }

    @When("^I send a GET request to newly created service with product ID$")
    public void iSendAGETRequestToNewlyCreatedServiceWithProductID() {
        HashMap<String, ?> storeMap = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(storeMap, hasValue(name));
    }


    @And("^I verify if the service is created with correct details$")
    public void iVerifyIfTheServiceIsCreatedWithCorrectDetails() {
        HashMap<String, ?> storeMap = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(storeMap, hasValue(name));

    }

    @When("^I send a PUT request to newly created service with product ID$")
    public void iSendAPUTRequestToNewlyCreatedServiceWithProductID() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        servicesSteps.updateService(serviceID,name);

    }

    @And("^I verify if the service details is updated with correct details$")
    public void iVerifyIfTheServiceDetailsIsUpdatedWithCorrectDetails() {
        HashMap<String, ?> productList = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @When("^I send a DELETE request to newly created service with product ID$")
    public void iSendADELETERequestToNewlyCreatedServiceWithProductID() {
        servicesSteps.deleteService(serviceID).statusCode(200);

    }

    @And("^I verify if the service is deleted$")
    public void iVerifyIfTheServiceIsDeleted() {
        servicesSteps.getServiceByID(serviceID).statusCode(404);

    }
}
