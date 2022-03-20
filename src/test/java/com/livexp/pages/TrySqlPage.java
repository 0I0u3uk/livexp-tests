package com.livexp.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.livexp.model.Customer;
import com.livexp.model.TableField;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class TrySqlPage {

    private final SelenideElement restoreButton = $(By.id("restoreDBBtn"));
    private final SelenideElement runScriptButton = $(By.xpath("//button[contains(text(),'Run SQL')]"));
    private final ElementsCollection results = $$(By.xpath("//*[@id='resultSQL']//tr"));

    private Map<String, String> parseToMap(ElementsCollection columnNames, ElementsCollection fields) {
        var resultMap = new HashMap<String, String>();
        for (var i = 0; i < columnNames.size(); i++) {
            resultMap.put(columnNames.get(i).text(), fields.get(i).text());
        }
        return resultMap;
    }

    public TrySqlPage executeQuery(String query) {
        var js = String.format("document.querySelector('.CodeMirror').CodeMirror.setValue('%s')", query);

        executeJavaScript(js);

        runScriptButton.click();

        return this;
    }

    public TrySqlPage restoreDatabase() {
        restoreButton.click();
        Selenide.confirm();
        return this;
    }

    public <T extends TableField> List<T> getResults(Class<T> clazz) {
        waitResultMessage("Number of Records");
        var columns = results.get(0);
        var data = new ArrayList<T>();
        for (int i = 1; i < results.size(); i++) {
            var map = parseToMap(columns.findAll(By.xpath("./th")), results.get(i).findAll(By.xpath("./td")));
            try {
                var model = clazz.getDeclaredConstructor().newInstance();
                model.parseToModel(map);
                data.add(model);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                throw new IllegalArgumentException(String.format("Can't create %s by this reason %s", clazz.getName(), ex.getMessage()));
            }
        }

        return data;
    }

    public void waitResultMessage(String s) {
        $(By.xpath(String.format("//*[@id='divResultSQL']//div[contains(text(),'%s')]", s))).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
