package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

public class PojoTest {
    Faker faker = new Faker();

    @Test
    public void CreateCategory() throws JsonProcessingException {

        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody =new RequestBody();

        requestBody.setCategory_title("IT");
        requestBody.setCategory_description("IT");
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token).
                contentType(ContentType.JSON).body(requestBody).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201,status);


        ObjectMapper mapper = new ObjectMapper();
       CustomResponse customResponse =  mapper.readValue(response.asString(), CustomResponse.class);
        System.out.println(customResponse.getCategory_id());


    }


    @Test
    public void createGetCategory() throws JsonProcessingException {

        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();


        RequestBody requestBody =new RequestBody();

        requestBody.setCategory_title("Computer");
        requestBody.setCategory_description("Good");
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201,status);


        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        int categoryId = customResponse.getCategory_id();
        String categoryTitle = response.jsonPath().getString("category_title");
        System.out.println("Category ID   " + categoryId);
        System.out.println("Category Title  " + categoryTitle);


        String getCategoryUrl = url + "/" + categoryId;


        Response getCategoryResponse = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .get(getCategoryUrl);


        int getStatus = getCategoryResponse.statusCode();
        Assert.assertEquals(200, getStatus);

        String retrievedCategoryTitle = getCategoryResponse.jsonPath().getString("category_title");
        Assert.assertEquals("Computer", retrievedCategoryTitle);


    }

    }





