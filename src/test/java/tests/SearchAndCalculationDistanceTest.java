package tests;

import helpers.GetUrl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.qameta.allure.Allure.step;

public class SearchAndCalculationDistanceTest extends TestBase {

    @Test
    @DisplayName("Поиск сайта «avtodispetcher.ru» по ключевым словам в яндексе, переход по найденной ссылке и " +
            "расчет стоимости пути между городами с указанием цены на 1л топлива и расхода на 100км пути")
    void searchAndCalculationDistanceTest() {
        step("Пользователь заходит на сайт Яндекс: www.yandex.ru", () -> {
            open("https://yandex.ru/");
        });

        step("Вводит в поисковую строку фразу «расчет расстояний между городами» и запускает поиск", () -> {
            $("#text").sendKeys("расчет расстояний между городами");
            $(".search2__button").click();
        });

        step("Среди результатов поиска пользователь ищет результат с сайта «avtodispetcher.ru»", () -> {
            $(byPartialLinkText("avtodispetcher.ru")).shouldBe(visible);
            sleep(5000);
        });

        step("Найдя нужный результат с этого сайта – пользовать кликает на данном результате и переходит" +
                "на сайт www.avtodispetcher.ru/distance/", () -> {
            $(byPartialLinkText("avtodispetcher.ru")).click();
            switchTo().window(1);
        });

        step("Пользователь убеждается что попал на нужный сайт", () -> {
            GetUrl.longLoadPage();
            Assertions.assertEquals("https://www.avtodispetcher.ru/distance/", url());
        });

        step("Пользователь вводит следующие значения в поля для расчета (в течении 10 секунд):\n" +
                "a. Поле «Откуда» - «Тула»\n" +
                "b. Поле «Куда» - «Санкт-Петербург»\n" +
                "c. Поле «Расход топлива» - «9»\n" +
                "d. Поле «Цена топлива» - «46»", () -> {
            $(byName("from")).sendKeys("Тула");
            $(byName("to")).sendKeys("Санкт-Петербург");
            $(byName("fc")).clear();
            $(byName("fc")).sendKeys("9");
            $(byName("fp")).clear();
            $(byName("fp")).sendKeys("46");
            sleep(10000);
        });

        step("Пользователь нажимает кнопку «Рассчитать»", () -> {
            $("[value=Рассчитать]").scrollTo().click();
        });

        step("Пользователь проверяет что рассчитанное расстояние = 897 км," +
                " а стоимость топлива =3726 руб.", () -> {
            $("#totalDistance").shouldHave(text("897"));
            $("#summaryContainer").shouldHave(text("3726"));
        });

        step("Пользователь кликает на «Изменить маршрут»", () -> {
            $("#triggerFormD").click();
        });

        step("В открывшейся форме в поле «Через города» вводит «Великий Новгород» (тратит на это 5 сек)", () -> {
            $(byName("v")).sendKeys("Великий Новгород");
            sleep(5000);
        });

        step("Пользователь снова нажимает «Рассчитать»", () -> {
            $("[value=Рассчитать]").scrollTo().click();
        });

        step("Пользователь проверяет что расстояние теперь = 966 км, а стоимость топлива = 4002 руб.", () -> {
            $("#totalDistance").shouldHave(text("966"));
            $("#summaryContainer").shouldHave(text("4002"));
        });
    }
}