package guru.qa.niffler.utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {

    private static final Faker faker = new Faker();
    public static String randomName () {
        return faker.name().firstName();
    }

    public static String randomSurname () {
        return faker.name().lastName();
    }

    public static String randomSentence (int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    public static String randomUsername() {
        return faker.name().username() + System.currentTimeMillis();
    }

    public static String generateUniqueCategoryName() {
        return faker.name().title() + System.currentTimeMillis();
    }
}
