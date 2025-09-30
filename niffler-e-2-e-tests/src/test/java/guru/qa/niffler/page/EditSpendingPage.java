package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.page.component.CalendarComponent;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Date;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ParametersAreNonnullByDefault
public class EditSpendingPage {
  private final CalendarComponent calendar = new CalendarComponent();
  private final SelenideElement openCalendarButton = $("img[alt='Calendar']");
  private final SelenideElement amountInput = $("#amount");
  private final SelenideElement currencyInput = $("#currency");
  private final SelenideElement categoryInput = $("#category");
  private final ElementsCollection currencyElements = $$("li[data-value]");
  private final SelenideElement descriptionInput = $("#description");
  private final SelenideElement submitButton = $("#save");


  @Step("Set new spending date: '{0}'")
  @Nonnull
  public EditSpendingPage setNewSpendingDate(Date date) {
    openCalendarButton.click();
    calendar.selectDateInCalendar(date);
    return this;
  }

  @Step("Set currency")
  @Nonnull
  public EditSpendingPage setCurrency(CurrencyValues currency) {
    currencyInput.click();
    currencyElements.find(text(currency.name())).click();
    return this;
  }

  @Step("Set category")
  @Nonnull
  public EditSpendingPage setCategory(String category) {
    categoryInput.clear();
    categoryInput.setValue(category);
    return this;
  }

  @Step("Enter amount")
  @Nonnull
  public EditSpendingPage setAmount(int amount) {
    amountInput.setValue(String.valueOf(amount));
    return this;
  }


  @Nonnull
  @Step("Set spending description to {description}")
  public EditSpendingPage setNewSpendingDescription(String description) {
    descriptionInput.clear();
    descriptionInput.setValue(description);
    return this;
  }

  @Nonnull
  @Step("Save spending")
  public MainPage save() {
    submitButton.click();
    return new MainPage();
  }
}
