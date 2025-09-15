package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class ProfilePage {
    private final String categoryLabel = ("//span[text()='%s']");
    private final SelenideElement profileLabel = $x("//h2[text()='Profile']");
    private final SelenideElement usernameInput = $("#username");

    @Nonnull
    public ProfilePage openPage() {
        Selenide.open(Config.getInstance().frontUrl() + "profile");
        return this;
    }

    @Nonnull
    public ProfilePage verifyOpened() {
        profileLabel.shouldBe(visible);
        usernameInput.shouldBe(visible);
        return this;
    }

    @Nonnull
    public ProfilePage checkCategoryShown(String categoryName) {
        SelenideElement categoryElement = $x(categoryLabel.formatted(categoryName));
        categoryElement.should(visible);
        return this;

    }

    @Nonnull
    public ProfilePage checkCategoryNotShown(String categoryName) {
        SelenideElement categoryElement = $x(categoryLabel.formatted(categoryName));
        categoryElement.shouldNot(visible);
        return this;

    }
}
