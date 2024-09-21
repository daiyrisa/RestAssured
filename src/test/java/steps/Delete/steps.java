//package steps.Delete;
//
//import entities.CustomResponse;
//import entities.RequestBody;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.junit.Assert;
//import utilities.CashWiseToken;
//
//public class steps {
//    RequestSpecification request;
//    CustomResponse customResponse = new CustomResponse();
//    RequestBody requestbody = new RequestBody();
//    Response response;
//
////    @Given("base url {string}")
////    public void base_url(String string) {
////        request = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
////                .baseUri(url);
//
//    }
//    @When("I provide {string} authorization token")
//    public void i_provide_authorization_token(String string) {
//        request = RestAssured.given().auth().oauth2(CashWiseToken.GetToken());
//
//    }
//    @When("I provide {string}")
//    public void i_provide(String string) {
//
//    }
//    @When("I hit DELETE endpoint {string}")
//    public void i_hit_delete_endpoint(String string) {
//        response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).body(requestbody)
//                .delete(endpoint);
//
//    }
//    @Then("verify status code is {int}")
//    public void verify_status_code_is(Integer int1) {
//        Assert.assertEquals(status, response.statusCode());
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    @When("I provide {string} with {string}")
//    public void i_provide_with(String string, String string2) {
//        requestBody.put(key, value);
//        request = request.body(requestBody.toString());
//
//    }
//    @When("I hit PUT endpoint {string}")
//    public void i_hit_put_endpoint(String string) {
//        response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).body(requestbody)
//                .put(endpoint);
//
//    }
//    @Then("verify response body conatins {string} with {string}")
//    public void verify_response_body_conatins_with(String string, String string2) {
//        Assert.assertEquals(status, response.statusCode());
//
//    }
//    @Then("verify request body conatins {string} with {string}")
//    public void verify_request_body_conatins_with(String string, String string2) {
//        String expectedValue = response.jsonPath().get(key);
//        Assert.assertEquals(expectedValue, value);
//
//    }
//
//
//

//}
