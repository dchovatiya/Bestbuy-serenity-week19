package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
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
//@RunWith(SerenityRunner.class)
public class CategoryCURDTestWithSteps extends TestBase
{
    @BeforeClass
    public static void endPath()
    {
        RestAssured.basePath= Path.CATEGORY;

    }
    static String id = "abcat" + TestUtils.getRandomValue();
    static String name="Epic Gifts";

    @Steps
    CategorySteps categorySteps;
    @Title("This will create a new category")
    @Test
    public void test001()
    {
        ValidatableResponse response=categorySteps.createCategory(id,name);
                response.log().all().statusCode(201);
    }
    @Title("Verify if the category was added to the application")
    @Test
    public void test002()
    {
        HashMap<String, Object> value = categorySteps.getCategoryInfoByName(name,id);
        Assert.assertThat(value, hasValue(name));
    }
    @Title("Update the category information and verify the updated information")
    @Test
    public void test003()
    {
        name=name+"_updated";
        categorySteps.updateCategory(id, name).log().all().statusCode(200);
        HashMap<String, Object> value = categorySteps.getCategoryInfoByName(name,id);
        Assert.assertThat(value, hasValue(name));
    }
    @Title("Delete the category and verify if the category is deleted!")
    @Test
    public void test004() {
        categorySteps.deleteCategory(id).statusCode(200);
        categorySteps.getCategoryByID(id) .statusCode(404);
    }
}
