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
public class RegisterPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitPasswordInput = $("#passwordSubmit");
    private final SelenideElement signUpButton = $("#register-button");
    private final SelenideElement registrationSuccessfulLabel =
            $x("//p[contains(text(), \"Congratulations! You've registered!\")]");
    private final SelenideElement signInButton = $(".form_sign-in");
    private final String errorUserRegisteredLabel = "//span[text() = 'Username `%s` already exists']";
    private final SelenideElement errorPasswordsCoincideLabel = $x("//span[text() = 'Passwords should be equal']");

    @Nonnull
    public RegisterPage openPage() {
        Selenide.open(Config.getInstance().authUrl() + "register");
        return this;
    }

    @Nonnull
    public RegisterPage setUsername(String username) {
        usernameInput.shouldBe(visible).sendKeys(username);
        return this;
    }

    @Nonnull
    public RegisterPage setPassword(String password) {
        passwordInput.shouldBe(visible).sendKeys(password);
        return this;
    }

    @Nonnull
    public RegisterPage setSubmitPassword(String password) {
        submitPasswordInput.shouldBe(visible).sendKeys(password);
        return this;
    }

    @Nonnull
    public RegisterPage submitRegistration() {
        signUpButton.shouldBe(clickable).click();
        return this;
    }

    @Nonnull
    public RegisterPage registerNewUser(String username, String password, String submitPassword) {
        return this.setUsername(username)
                .setPassword(password)
                .setSubmitPassword(submitPassword)
                .submitRegistration();
    }

    @Nonnull
    public RegisterPage checkRegistrationSuccessful() {
        registrationSuccessfulLabel.should(visible);
        return this;
    }

    @Nonnull
    public LoginPage clickSignIn() {
        signUpButton.shouldBe(visible).click();
        return new LoginPage();
    }

    @Nonnull
    public RegisterPage checkErrorUserRegisteredShown(String username) {
        SelenideElement errorMessage = $x(errorUserRegisteredLabel.formatted(username));
        errorMessage.shouldBe(visible);
        return this;
    }

    @Nonnull
    public RegisterPage checkErrorPasswordsDontCoincideShown() {
        errorPasswordsCoincideLabel.shouldBe(visible);
        return this;
    }

}
