package v1;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class ToDoMVCTest {
    static {
        Configuration.pageLoadStrategy = "normal";
    }

    @Test
    public void toDoMVCWorkflow() {

        open("https://todomvc4tasj.herokuapp.com/");

        add("1", "2", "3", "4");
        assertTasks("1", "2", "3", "4");

        delete("2");
        assertTasks("1", "3", "4");

        complete("4");
        clearCompleted();
        assertTasks("1", "3");

        completeAll();
        clearCompleted();
        assertNoTasks(empty);
    }

    private void assertNoTasks(CollectionCondition collectionCondition) {
        tasks.shouldBe(collectionCondition);
    }

    private void add(String... taskTexts) {
        for (String taskText : taskTexts) {
            $("#new-todo").setValue(taskText).pressEnter();
        }
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

    private void complete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".toggle").click();
    }

    private void completeAll() {
        $("#toggle-all").click();
    }

    private ElementsCollection tasks = $$("#todo-list li");
}