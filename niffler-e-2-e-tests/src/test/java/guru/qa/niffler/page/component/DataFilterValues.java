package guru.qa.niffler.page.component;

import lombok.Getter;

public enum DataFilterValues {
    ALL_TIME("All time"),
    LAST_MONTH("Last month"),
    LAST_WEEK("Last week"),
    TODAY("Today");

    @Getter
    private final String value;

    DataFilterValues(String value) {
        this.value = value;
    }
}
