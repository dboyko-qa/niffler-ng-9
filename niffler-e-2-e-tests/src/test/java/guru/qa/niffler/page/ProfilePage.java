package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.component.Header;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class ProfilePage {
    public final Header header = new Header();
    private final String categoryLabel = ("//span[text()='%s']");
    private final SelenideElement profileLabel = $x("//h2[text()='Profile']");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement avatarInput = $("input");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement submitButton = $("button[type='submit']");

    @Nonnull
    @Step("Open Profile page")
    public ProfilePage openPage() {
        Selenide.open(Config.getInstance().frontUrl() + "profile");
        return this;
    }

    @Nonnull
    @Step("Verify that Profile page is opened")
    public ProfilePage verifyOpened() {
        profileLabel.shouldBe(visible);
        usernameInput.shouldBe(visible);
        return this;
    }

    @Nonnull
    @Step("Check that category {categoryName} is shown")
    public ProfilePage checkCategoryShown(String categoryName) {
        SelenideElement categoryElement = $x(categoryLabel.formatted(categoryName));
        categoryElement.should(visible);
        return this;

    }

    @Nonnull
    @Step("Check that category {categoryName} is not shown")
    public ProfilePage checkCategoryNotShown(String categoryName) {
        SelenideElement categoryElement = $x(categoryLabel.formatted(categoryName));
        categoryElement.shouldNot(visible);
        return this;

    }

    @Nonnull
    @Step("Upload new avatar")
    public ProfilePage uploadAvatar(String filePath) {
        avatarInput.uploadFromClasspath(filePath);
        return this;
    }

    @Step("Set user name")
    @Nonnull
    public ProfilePage setName(String name) {
        nameInput.clear();
        nameInput.setValue(name);
        return this;
    }

    @Step("Verify user name")
    @Nonnull
    public ProfilePage verifyName(String name) {
        nameInput.shouldHave(value(name));
        return this;
    }

    @Step("Save changes")
    @Nonnull
    public ProfilePage saveChanges() {
        submitButton.click();
        return this;
    }
}
