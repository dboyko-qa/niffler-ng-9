package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class FriendsPage {
    private final SelenideElement peopleTab = $("a[href='/people/friends']");
    private final SelenideElement allTab = $("a[href='/people/all']");
    private final SelenideElement requestsTable = $("#requests");
    private final SelenideElement friendsTable = $("#friends");
    private final SelenideElement nextButton = $("#page-next");
    private final SearchComponent searchComponent = new SearchComponent();

    @Nonnull
    public FriendsPage checkExistingFriends(String... expectedUsernames) {
        Arrays.stream(expectedUsernames).forEach(el -> {
            searchComponent.searchUser(el);
            friendsTable.$$("tr").find(text(el));
        });
        return this;
    }

    @Nonnull
    public FriendsPage checkNoExistingFriends() {
        friendsTable.$$("tr").shouldHave(size(0));
        return this;
    }

    @Nonnull
    public FriendsPage checkExistingInvitations(String... expectedUsernames) {
        Arrays.stream(expectedUsernames).forEach(el -> {
            searchComponent.searchUser(el);
            requestsTable.$$("tr").find(text(el));
        });
        return this;
    }
}
