package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.*;
import io.qameta.allure.Step;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class Header {

  private final SelenideElement self = $("#root header");
  private final SelenideElement headerMenu = $("ul[role='menu']");
  private final SelenideElement newSpendingButton = self.$("a[href*='/spending']");

  public void checkHeaderText() {
    self.$("h1").shouldHave(text("Niffler"));
  }

  @Step("Open Friends page")
  public FriendsPage toFriendsPage () {
    self.$("button").click();
    headerMenu.$$("li").find(text("Friends")).click();
    return new FriendsPage();
  }

  @Step("Open all people page")
  public PeoplePage toAllPeoplesPage () {
    self.$("button").click();
    headerMenu.$$("li").find(text("All People")).click();
    return new PeoplePage();
  }

  @Step("Open Profile page")
  public ProfilePage toProfilePage () {
    self.$("button").click();
    headerMenu.$$("li").find(text("Profile")).click();
    return new ProfilePage();
  }

  @Step("Sign out")
  public LoginPage signOut () {
    self.$("button").click();
    headerMenu.$$("li").find(text("Sign out")).click();
    return new LoginPage();
  }

  @Step("Open Add spending page")
  public EditSpendingPage addSpendingPage () {
    newSpendingButton.click();
    return new EditSpendingPage();
  }

  @Step("Open Main page")
  public MainPage toMainPage () {
    self.$("h1").click();
    return new MainPage();
  }
}
