package steps;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class ApiSteps {
    Faker faker = new Faker();
    String email;
    String sellerName;
    int sellerId;
    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endpoint) {
        APIRunner.runGET(endpoint,5747);
         sellerId = APIRunner.getCustomResponse().getSeller_id();

    }
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
//        String email = APIRunner.getCustomResponse().getEmail();
//        Assert.assertFalse(email.isEmpty());

    }





    @Given("user get all seller api with {string}")
    public void user_get_all_seller_api_with(String endpoint) {
       Map<String , Object>params= new HashMap<>();
       params.put("page",1);
       params.put("size",110);
       params.put("isArchived",false);
       APIRunner.runGET(endpoint,params);
    }
    @Then("verify seller ids are not equal to {int}")
    public void verify_seller_ids_are_not_equal_to(Integer int1) {
        int size = APIRunner.getCustomResponse().getResponses().size();

        for (int i = 0; i<size; i++){
            int sellerId = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(0, sellerId);
        }

    }










    @Then("user hits put api with {string}")
    public void user_hits_put_api_with(String endpoint) {

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().streetAddress());

        APIRunner.runPUT(endpoint,requestBody,5504);
        email = APIRunner.getCustomResponse().getEmail();
        sellerName = APIRunner.getCustomResponse().getSeller_name();

    }
    @Then("verify useremail wes updated")
    public void verify_useremail_wes_updated() {
        Assert.assertFalse(email.isEmpty());

    }
    @Then("verify user first name was updated")
    public void verify_user_first_name_was_updated() {
        Assert.assertFalse(sellerName.isEmpty());

    }









    @Then("user archive seller")
    public void user_archive_seller(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerId);
        params.put("archive", true);
        APIRunner.runPOST(endpoint,params);


    }
    @Then("verify seller is archived")
    public void verify_seller_is_archived() {

    }

    @Then("user hits archive seller api {string}")
    public void user_hits_archive_seller_api(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page",1);
        params.put("size",110);
        params.put("isArchived",true);

        APIRunner.runGET(endpoint , params);

        boolean isPresent = false;
        int size = APIRunner.getCustomResponse().getResponses().size();

        for (int i = 0; i< size; i++){
            int ids = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if(sellerId == ids){
                isPresent = true;
                break;

            }
        }
        Assert.assertTrue(isPresent);

    }




    @Given("user hits post api with {string}")
    public void user_hits_post_api_with(String endpoint) {
        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name("dsdsds");
        requestBody.setSeller_name("sdsdsdsds");
        requestBody.setEmail(faker.internet().emailAddress());


    }
    @Then("verify seller id was genereted")
    public void verify_seller_id_was_genereted() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("delete the seller with {string}")
    public void delete_the_seller_with(String endpoint) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("verify that delte in no in list")
    public void verify_that_delte_in_no_in_list() {
      
    }






}
