package v2;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hasClass;
import static com.codeborne.selenide.Selenide.*;

public class ToDoMVCTest {
    static {
        Configuration.remote = "http://192.168.102.137:4444/wd/hub/";
    }

    @Test
    public void toDoMVCWorkflow() {

        open("https://todomvc4tasj.herokuapp.com/");

        add("1", "2", "3", "4");
        assertTasks("1", "2", "3", "4");

        edit("2", "5");
        assertTasks("1", "5", "3", "4");

        delete("5");
        assertTasks("1", "3", "4");

        toggle("3");
        clearCompleted();
        assertTasks("1", "4");

        toggleAll();
        clearCompleted();
        assertNoTasks();

    }

    private void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    private void add(String... taskTexts) {
        for (String taskText : taskTexts) {
            $("#new-todo").setValue(taskText).pressEnter();
        }
    }

    private void edit(String e1, String e2) {
        tasks.find(exactText(e1)).doubleClick();
        tasks.find(cssClass("editing")).find(".edit").setValue(e2).pressEnter();
    }

    private void assertTasks(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void delete(String taskText) {
            tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".toggle").click();
    }

    private void toggleAll() {
        $("#toggle-all").click();
    }

    private ElementsCollection tasks = $$("#todo-list li");
}