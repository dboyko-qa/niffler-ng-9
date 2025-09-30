package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import java.util.Date;

@WebTest
public class SpendingTest {

  private static final Config CFG = Config.getInstance();

  @User(
          username = "duck",
          spendings = @Spending(
            category = "Обучение",
            description = "java advanced",
            amount = 1000
            )
  )
  @Test
  void spendingShouldBeDisplayedInTheList(SpendJson[] spendJson) {
    final String newDescription = "new name";
    Selenide.open(CFG.frontUrl(), LoginPage.class)
        .fillLoginPage("duck", "12345")
        .submit()
        .checkThatPageLoaded()
        .editSpending(spendJson[0].description())
        .setNewSpendingDescription(newDescription)
        .save()
        .checkThatTableContainsSpending(newDescription);
  }

  @Test
  void addSpendingTest() {
    String newSpendingName  = "New spending" + System.currentTimeMillis();
    Selenide.open(CFG.frontUrl(), LoginPage.class)
            .fillLoginPage("duck", "12345")
            .submit()
            .checkThatPageLoaded()
            .getHeader()
            .addSpendingPage()
            .setNewSpendingDate(new Date())
            .setAmount(1000)
            .setCurrency(CurrencyValues.EUR)
            .setCategory("New category")
            .setNewSpendingDescription(newSpendingName)
            .save()
            .checkThatTableContainsSpending(newSpendingName);
  }
}
