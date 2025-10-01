package guru.qa.niffler.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.EditSpendingPage;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SpendingTable {
    private final SelenideElement self = $("#spendings");
    private final ElementsCollection tableRows = self.$("tbody").$$("tr");
    private final SelenideElement nextButton = self.$("#page-next");
    private final SelenideElement deleteButton = self.$("#delete");
    private final SearchField searchField = new SearchField();

    public SpendingTable selectPeriod(DataFilterValues period) {
        self.$("#period").click();
        $x("//li[@data-value='%s']".formatted(period.getValue()));
        return this;
    }

    @Nonnull
    private SelenideElement findInTable(String text) {
        SelenideElement result = null;
        result = tableRows.find(text(text));
        while (!result.exists() && nextButton.is(clickable)) {
            nextButton.scrollIntoView(true).click();
        }
        return result;
    }

    public EditSpendingPage editSpending(String description) {
        findInTable(description).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    public SpendingTable deleteSpending(String description) {
        findInTable(description).$$("td").get(0).click();
        deleteButton.click();
        $x("//div[@role='dialog']//button[text()='Delete']").click();
        return this;
    }

    public SpendingTable searchSpendingByDescription(String description) {
        searchField.search(description);
        return this;
    }

    public SpendingTable checkTableContains(String... expectedSpends) {
        Arrays.stream(expectedSpends).forEach(el -> {
                    findInTable(el).should(visible);
                }
        );
        return this;
    }

    public SpendingTable checkTableSize(int expectedsize) {
        int actualSize = tableRows.size();
        while (nextButton.is(clickable)) {
            nextButton.click();
            actualSize = actualSize + tableRows.size();
        }
        Assert.isTrue(actualSize == expectedsize, "Number of spendings is not equal to expected size");
        return this;
    }
}
