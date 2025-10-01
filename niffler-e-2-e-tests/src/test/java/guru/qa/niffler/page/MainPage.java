package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.Header;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class MainPage {
  private final Header header = new Header();
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement statComponent = $("#stat");
  private final SelenideElement spendingTable = $("#spendings");
  private final SelenideElement nextButton = $("#page-next");

  @Nonnull
  public Header getHeader() {
    return header;
  }

  @Nonnull
  public FriendsPage friendsPage() {
    return header.toFriendsPage();
  }

  @Nonnull
  public PeoplePage allPeoplesPage() {
    return header.toAllPeoplesPage();
  }

  @Nonnull
  private SelenideElement findInTable(ElementsCollection element, String text) {
    SelenideElement result = null;
    result = element.find(text(text));
    while (!result.exists() && nextButton.is(clickable)) {
      nextButton.scrollIntoView(true).click();
    }
    return result;
  }

  @Nonnull
  @Step("Edit spending {spendingDescription}")
  public EditSpendingPage editSpending(String spendingDescription) {
    findInTable(tableRows, spendingDescription).$$("td").get(5).click();
    return new EditSpendingPage();
  }

  @Step("Check that table contains spending {spendingDescription}")
  public void checkThatTableContainsSpending(String spendingDescription) {
    findInTable(tableRows, spendingDescription).should(visible);
  }

  @Nonnull
  @Step("Verify that page is loaded")
  public MainPage checkThatPageLoaded() {
    statComponent.should(visible).shouldHave(text("Statistics"));
    spendingTable.should(visible).shouldHave(text("History of Spendings"));
    return this;
  }

}
