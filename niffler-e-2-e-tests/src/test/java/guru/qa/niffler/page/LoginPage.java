package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement registerButton = $("#register-button");
  private final SelenideElement loginForm = $("#login-form");
  private final SelenideElement invalidUserAndPasswordLabel = $x("//p[text() = 'Bad credentials']");

  @Nonnull
  @Step("Open Login page")
  public LoginPage openPage() {
    Selenide.open(Config.getInstance().authUrl() + "login");
    return this;
  }

  @Nonnull
  @Step("Fill login data ")
  public LoginPage fillLoginPage(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    return this;
  }

  @Nonnull
  @Step("Login with user {username}")
  public MainPage successLogin(String username, String password) {
    fillLoginPage(username, password);
    submit();
    return new MainPage();
  }

  @Nonnull
  @Step("Click submit button")
  public MainPage submit() {
    submitButton.click();
    return new MainPage();
  }

  @Nonnull
  @Step("Click Create new account")
  public RegisterPage clickCreateNewAccount() {
    registerButton.shouldBe(clickable).click();
    return new RegisterPage();
  }

  @Nonnull
  @Step("Check that error is shown for invalid user data")
  public LoginPage checkInvalidUserAndPasswordError() {
    loginForm.shouldBe(visible);
    invalidUserAndPasswordLabel.shouldBe(visible);
    return this;
  }
}
