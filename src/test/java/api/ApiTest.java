package api;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApiTest {

    @Test
    public void testToken(){
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();

        requestBody.setEmail("isaevdaiyrr@gmail.com");
        requestBody.setPassword("Daiyr.50534");

        Response response =  RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);
//        response.prettyPrint();

        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }

    @Test
    public void GetSingSeller(){
       String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/" + 4616;
       String token = CashWiseToken.GetToken();

       Response response = RestAssured.given().auth().oauth2(token).get(url);
      String expectedEmail =  response.jsonPath().getString("email");

      Assert.assertFalse(expectedEmail.isEmpty());
      Assert.assertTrue(expectedEmail.endsWith(".com"));

    }

    @Test
    public void GetAllSellers(){
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String , Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page" , 1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200,statusCode);
//        response.prettyPrint();

        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

    }

    @Test
    public void GetAllSellers1(){
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String ,Object> params = new HashMap<>();
        params.put("isArchived" ,false);
        params.put("size" , 10);
        params.put("page",1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

        List<String > ListOfEmail = response.jsonPath().getList("responses.email");

        for (String emails : ListOfEmail){
            Assert.assertFalse(emails.isEmpty());
        }

    }
}