package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class SearchComponent {
    private final SelenideElement search = $("input[placeholder='Search']");

    @Nonnull
    public SearchComponent searchUser(String username) {
        search.shouldBe(visible).clear();
        search.sendKeys(username);
        search.pressEnter();
        return this;
    }
}
