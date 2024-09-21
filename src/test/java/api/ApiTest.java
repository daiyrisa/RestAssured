package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.it.Ma;
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

    Faker faker = new Faker();

    @Test
    public void testToken() {
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();

        requestBody.setEmail("isaevdaiyrr@gmail.com");
        requestBody.setPassword("Daiyr.50534");

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);
//        response.prettyPrint();

        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }

    @Test
    public void GetSingSeller() {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/" + 4616;
        String token = CashWiseToken.GetToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        String expectedEmail = response.jsonPath().getString("email");

        Assert.assertFalse(expectedEmail.isEmpty());
        Assert.assertTrue(expectedEmail.endsWith(".com"));

    }

    @Test
    public void GetAllSellers() {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page", 1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
//        response.prettyPrint();

        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

    }

    @Test
    public void GetAllSellers1() {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page", 1);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);

        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());

        List<String> ListOfEmail = response.jsonPath().getList("responses.email");

        for (String emails : ListOfEmail) {
            Assert.assertFalse(emails.isEmpty());
        }

    }


    @Test
    public void createSeller() {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        for (int i = 1; i <= 15; i++) {
            RequestBody requestBody = new RequestBody();
            requestBody.setCompany_name(faker.company().name());
            requestBody.setSeller_name(faker.name().fullName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().cellPhone());
            requestBody.setAddress(faker.address().fullAddress());

            Response response = RestAssured.given().auth().oauth2(token).
                    contentType(ContentType.JSON).body(requestBody).post(url);

            int status = response.statusCode();
            Assert.assertEquals(201, status);

            String id = response.jsonPath().getString("seller_id");
            String url2 = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/" + id;

            Response response1 = RestAssured.given().auth().oauth2(token).get(url2);
            Assert.assertEquals(200, response1.getStatusCode());

        }
    }


    @Test
    public void getAllSellers() throws JsonProcessingException {

        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int status = response.statusCode();
        Assert.assertEquals(200, status);
        System.out.println(response.getStatusCode());
        System.out.println(response.prettyPrint());


        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();


    }


    @Test
    public void createSellersWithoutEmail() {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();


        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().fullName());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().fullAddress());

        Response response = RestAssured.given().auth().oauth2(token).
                contentType(ContentType.JSON).body(requestBody).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201, status);

        String id = response.jsonPath().getString("seller_id");
        String url2 = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/" + id;

        System.out.println(response.prettyPrint());


    }


    @Test
    public void ArchivedTest() throws JsonProcessingException {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();


        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", params);
        params.put("archive", true);

        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);
        int status1 = response.statusCode();
        Assert.assertEquals(200, status1);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


    }


    @Test
    public void ArchivedAllSellers() throws JsonProcessingException {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 110);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();

        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        String urlToArchive = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getSeller_id();

            Map<String, Object> paramsToArchive = new HashMap<>();

            paramsToArchive.put("sellersIdsForArchive", id);
            paramsToArchive.put("archive", true);

            Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

            int status1 = response1.statusCode();

            Assert.assertEquals(200, status1);
        }


    }


    @Test
    public void GetAllActivate() throws JsonProcessingException {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", true);
        params.put("page", 1 );
        params.put("size", 110 );

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        String urlToArchive = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();

        for(int i = 0; i < size; i ++ ){
            if(customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com")){
                int id = customResponse.getResponses().get(i).getSeller_id();

                Map<String, Object> paramsToArchive = new HashMap<>();

                paramsToArchive.put("sellersIdsForArchive",id );
                paramsToArchive.put("archive", false);

                Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

                int status1 = response1.statusCode();

                Assert.assertEquals(200, status1);
            }
        }


    }

    @Test
    public void getArchAllSellers() throws JsonProcessingException {
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 110);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();

        Assert.assertEquals(200, status);

        System.out.println(response.prettyPrint());

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


    }






    @Test
    public void CreateGetSeller() throws JsonProcessingException {
        Faker faker = new Faker();

        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("CatWise");
        requestBody.setSeller_name("Meayw ");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("31274325234");
        requestBody.setAddress("Earth");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.statusCode();

        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int ExpectedSellerId = customResponse.getSeller_id();


        String url2 = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 1000 );
        params.put("page", 1 );

        Response response1 = RestAssured.given().auth().oauth2(token).params(params).get(url2);
        int statusCode = response.statusCode();
        Assert.assertEquals(201, statusCode);

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse1.getResponses().size();

        boolean isPresent = false;

        for(int i = 0; i < size; i ++ ){
            if(customResponse1.getResponses().get(i).getSeller_id() == ExpectedSellerId){
                isPresent = true;
                break;
            }
        }

        Assert.assertTrue(isPresent);

    }





    @Test
    public void DeleteSeller() throws JsonProcessingException {
        Faker faker = new Faker();


        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();


        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("CatWiseee");
        requestBody.setSeller_name("Meayw");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("31274325234");
        requestBody.setAddress("Earth");


        Response createResponse = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);

        int createStatus = createResponse.statusCode();
        Assert.assertEquals(201, createStatus);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(createResponse.asString(), CustomResponse.class);


        int sellerId = createResponse.jsonPath().getInt("seller id");


        Response deleteResponse = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .delete(url + sellerId);

        int deleteStatus = deleteResponse.statusCode();


        Assert.assertEquals(200, deleteStatus);
    }





    @Test
    public void UpdateSeller() throws JsonProcessingException {
        Faker faker = new Faker();

        // Set up the URL and token
        String url = Config.getProperties("cashWaiseUrl") + "/api/myaccount/sellers/";
        String token = CashWiseToken.GetToken();

        // Create a seller first to ensure we have a seller to update
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("daiyr");
        requestBody.setSeller_name("isa");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("31274325234");
        requestBody.setAddress("Earth");

        // Post the seller
        Response createResponse = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);

        int createStatus = createResponse.statusCode();
        Assert.assertEquals(201, createStatus);

        // Get the ID of the created seller from the response
        int sellerId = createResponse.jsonPath().getInt("seller_id");


        RequestBody updateRequestBody = new RequestBody();
        updateRequestBody.setCompany_name("CatWiseUpdated");
        updateRequestBody.setSeller_name("Meow Updated");
        updateRequestBody.setEmail(faker.internet().emailAddress());
        updateRequestBody.setPhone_number("31274325235");
        updateRequestBody.setAddress("Mars");

        // Now, perform the PUT operation to update the seller
        Response updateResponse = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .put(url + sellerId);

        int updateStatus = updateResponse.statusCode();

        // Assert that the PUT request was successful
        Assert.assertEquals(200, updateStatus);


    }

}



//    @Test
//    public void createSeller2() {
//        String url = Config.getProperties("cashWaiseUrl") + "api/myaccount/sellers/";
//        String token = CashWiseToken.GetToken();
//
//
//        RequestBody requestBody = new RequestBody();
//        requestBody.setCompany_name("YouTobe");
//        requestBody.setSeller_name("Jaison");
//        requestBody.setEmail("Jaison@gmail.com");
//        requestBody.setPhone_number("3902390293092");
//        requestBody.setAddress("noth");
//
//        Response response = RestAssured.given().auth().oauth2(token).
//                contentType(ContentType.JSON).body(requestBody).post(url);
//
//        int status = response.statusCode();
//        Assert.assertEquals(201, status);
//        System.out.println(response.prettyPrint());
//
//    }
//
//    @Test
//    public void getSeller() {
//        String url = Config.getProperties("cashWaiseUrl") + "api/myaccount/sellers/";
//        String token = CashWiseToken.GetToken();
//
//        Response response = RestAssured.given()
//                .auth()
//                .oauth2(token)
//                .get(url + "api/myaccount/sellers/4616");
//        System.out.println(response.getStatusCode());
//        System.out.println(response.prettyPrint());
//
//
//    }
