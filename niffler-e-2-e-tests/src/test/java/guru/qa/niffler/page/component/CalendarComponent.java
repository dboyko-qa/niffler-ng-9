package guru.qa.niffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class CalendarComponent {
  private final SelenideElement self = $(".MuiPickersLayout-root");
  private final SelenideElement input = $("input[name='date']");
  private final SelenideElement calendarButton = $("button[aria-label*='Choose date']");
  private final SelenideElement prevMonthButton = self.$("svg[data-testid='ArrowLeftIcon']");
  private final SelenideElement nextMonthButton = self.$("svg[data-testid='ArrowRightIcon']");
  private final SelenideElement currentMonthAndYear = self.$(".MuiPickersCalendarHeader-label");

  private final SelenideElement yearSelectionDropdownButton = self.$x(".//*[@data-testid='ArrowDropDownIcon']");
  private final ElementsCollection yearButtons = self.$$("button.MuiPickersYear-yearButton");
  private final ElementsCollection dayButton = self.$$("button.MuiPickersDay-root");
  @Step("Select date in calendar")
  public CalendarComponent selectDateInCalendar(@Nonnull Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    selectYear(year);
    selectMonth(month);
    selectDay(day);
    return this;
  }

  @Step("Select year ({year}")
  public CalendarComponent selectYear(int year) {
    yearSelectionDropdownButton.click();
    yearButtons.findBy(Condition.text(String.valueOf(year))).scrollIntoView(true).click();
    return this;
  }

  @Step("Select month {month}")
  private void selectMonth(int month) {
    int actualMonth = getActualMonthIndex();

    while (actualMonth > month) {
      prevMonthButton.click();
      Selenide.sleep(200);
      actualMonth = getActualMonthIndex();
    }
    while (actualMonth < month) {
      nextMonthButton.click();
      Selenide.sleep(200);
      actualMonth = getActualMonthIndex();
    }
  }

  @Nonnull
  private int getActualMonthIndex() {
    return Month.valueOf(splitActualDateFromCalendar()[0]
                    .toUpperCase())
            .ordinal();
  }

  @Nonnull
  private String[] splitActualDateFromCalendar() {
    return currentMonthAndYear.should(matchText(".*\\d{4}"))
            .getText()
            .split(" ");
  }

  @Step("Select day ")
  public CalendarComponent selectDay(int day) {
    dayButton.findBy(Condition.text(String.valueOf(day))).click();
    return this;
  }
}
