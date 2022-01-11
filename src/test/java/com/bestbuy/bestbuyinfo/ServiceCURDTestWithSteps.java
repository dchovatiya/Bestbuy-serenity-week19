package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

/**
 * By Dimple Patel
 **/
@RunWith(SerenityRunner.class)
public class ServiceCURDTestWithSteps extends TestBase
{
    @BeforeClass
    public static void endPath()
    {
        RestAssured.basePath= Path.SERVICES;
    }
    static String name="Off Licence Business";
    static int serviceId;
    @Steps
    ServiceSteps serviceSteps;
    @Title("This will create a new service")
    @Test
    public void test001() {

        ValidatableResponse response = serviceSteps.createService(name);
        response.log().all().statusCode(201);
    }
    @Title("Verify if the service was added to the application")
    @Test
    public void test002() {

        HashMap<String, Object> value = serviceSteps.getServiceInfoByName(name);
        Assert.assertThat(value, hasValue(name));
        serviceId = (int) value.get("id");
    }
    @Title("Update the service information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        serviceSteps.updateService(serviceId,name).log().all().statusCode(200);
        HashMap<String, Object> value = serviceSteps.getServiceInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }
    @Title("Delete the service and verify if the service is deleted!")
    @Test
    public void test004() {
        serviceSteps.deleteService(serviceId).statusCode(200);
        serviceSteps.getServiceById(serviceId) .statusCode(404);
    }
}
