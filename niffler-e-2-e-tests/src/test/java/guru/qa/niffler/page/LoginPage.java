package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;

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
  public LoginPage openPage() {
    Selenide.open(Config.getInstance().authUrl() + "login");
    return this;
  }

  @Nonnull
  public LoginPage fillLoginPage(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    return this;
  }

  @Nonnull
  public MainPage successLogin(String username, String password) {
    fillLoginPage(username, password);
    submit();
    return new MainPage();
  }

  @Nonnull
  public MainPage submit() {
    submitButton.click();
    return new MainPage();
  }

  @Nonnull
  public RegisterPage clickCreateNewAccount() {
    registerButton.shouldBe(clickable).click();
    return new RegisterPage();
  }

  @Nonnull
  public LoginPage checkInvalidUserAndPasswordError() {
    loginForm.shouldBe(visible);
    invalidUserAndPasswordLabel.shouldBe(visible);
    return this;
  }
}
