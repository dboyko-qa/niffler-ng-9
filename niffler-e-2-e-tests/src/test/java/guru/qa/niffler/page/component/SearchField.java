package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class SearchField {
    private final SelenideElement search = $("input[placeholder='Search']");

    @Nonnull
    public SearchField search(String input) {
        clearIfNotEmpty();
        search.shouldBe(visible).sendKeys(input);
        search.pressEnter();
        return this;
    }

    @Nonnull
    public SearchField clearIfNotEmpty () {
        if (!search.getValue().isBlank()) search.shouldBe(visible).clear();
        return this;
    }
}
