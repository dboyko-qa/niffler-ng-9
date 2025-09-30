package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.SearchField;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class FriendsPage {
    private final SelenideElement peopleTab = $("a[href='/people/friends']");
    private final SelenideElement allTab = $("a[href='/people/all']");
    private final SelenideElement requestsTable = $("#requests");
    private final SelenideElement friendsTable = $("#friends");
    private final SelenideElement nextButton = $("#page-next");
    private final SelenideElement declineConfirmationDialog = $("div[role='dialog']");
    private final SearchField searchField = new SearchField();

    @Nonnull
    @Step("Check that friends exist {expectedUsernames}")
    public FriendsPage checkExistingFriends(String... expectedUsernames) {
        Arrays.stream(expectedUsernames).forEach(el -> {
            searchField.search(el);
            friendsTable.$$("tr").find(text(el));
        });
        return this;
    }

    @Nonnull
    @Step("Check that no friends exist for user")
    public FriendsPage checkNoExistingFriends() {
        friendsTable.$$("tr").shouldHave(size(0));
        return this;
    }

    @Nonnull
    @Step("Check that invitation exist: {expectedUsernames}")
    public FriendsPage checkExistingInvitations(String... expectedUsernames) {
        Arrays.stream(expectedUsernames).forEach(el -> {
            searchField.search(el);
            requestsTable.$$("tr").find(text(el));
        });
        return this;
    }

    @Nonnull
    @Step("Accept invitation")
    public FriendsPage acceptInvitations(String username) {
        return invitationManage(true, username);
    }

    @Nonnull
    @Step("Decline invitation")
    public FriendsPage declineInvitations(String username) {
        invitationManage(false, username);
        declineConfirmationDialog.$(byText("Decline")).click();
        return this;
    }

    @Nonnull
    private FriendsPage invitationManage(Boolean accept, String username) {
        searchField.search(username);
        SelenideElement invitationRow = requestsTable.$$("tr").find(text(username));
        SelenideElement button = accept
                ? invitationRow.$(byText("Accept"))
                : invitationRow.$(byText("Decline"));
        button.click();
        return this;
    }

    @Nonnull
    @Step("Check that friendship accepted")
    public FriendsPage checkFriendshipAccepted(String username) {
        SelenideElement invitationRow = friendsTable.$$("tr").find(text(username));
        invitationRow.$(byText("Unfriend")).shouldBe(visible);
        return this;
    }

    @Step("Check that there is no friend with name {username}")
    public void checkNoFriendWithUsername(String username) {
        searchField.search(username);
        friendsTable.$$("tr").shouldHave(size(0));
    }


}
