package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();
    @Given("the user is on {string}")
    public void the_user_is_on(String url) {
        Driver.getDriver().get(Config.getProperties("sauceDemo"));

    }
    @When("user enter the username {string} and password {string}")
    public void user_enter_the_username_and_password(String username, String password) {
        loginPage.usernameInput.sendKeys(Config.getProperties("username"));
        loginPage.passwordInput.sendKeys(Config.getProperties("password"));

    }
    @When("user click on the login button")
    public void user_click_on_the_login_button() {
        loginPage.login.click();

    }

}


