package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.component.SearchField;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class PeoplePage {
    private final ElementsCollection allPeopleList = $$("#all tr");
    private final SelenideElement allTable = $x("//a[@href='/people/all']");
    private final SearchField searchField = new SearchField();

    @Nonnull
    @Step("Open People page")
    public PeoplePage openPage() {
        Selenide.open(Config.getInstance().frontUrl() + "people/all");
        allTable.shouldBe(visible);
        return this;
    }

    @Nonnull
    @Step("Check that user {outcomeInviteUser} is in Waiting status")
    public PeoplePage checkUserInWaitingStatus(String outcomeInviteUser) {
        searchField.search(outcomeInviteUser);
        SelenideElement outcomeLine = allPeopleList.find(text(outcomeInviteUser));
        outcomeLine.shouldHave(text("Waiting..."));
        return this;
    }

    @Nonnull
    @Step("Check that there are no invites")
    public PeoplePage checkNoOutcomeInvites() {
        allPeopleList.find(text("Waiting...")).shouldNot(exist);
        return this;
    }
}
