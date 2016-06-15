package v3;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndToDoVMCWorkflow {
    static {
        Configuration.remote = "http://192.168.102.137:4444/wd/hub/";
    }

    @Test
    public void endToEndToDoVMCWorkflow() {

        open("https://todomvc4tasj.herokuapp.com/");
        add("1");
        edit("1", "5");

        open("https://todomvc4tasj.herokuapp.com/#/active");
        assertTasks("5");
        delete("5");
        add("2");
        editCanceled("2", "333");
        toggle("2"); //complete

        open("https://todomvc4tasj.herokuapp.com/#/completed");
        assertTasks("2");
        toggleAll(); //reopen all

        open("https://todomvc4tasj.herokuapp.com/#/");
        assertTasks("2");
        toggle("2"); //complete
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

    private void editCanceled(String e1, String e2) {
        tasks.find(exactText(e1)).doubleClick();
        tasks.find(cssClass("editing")).find(".edit").setValue(e2).pressEscape();
    }

    private void assertTasks(String... taskTexts) { tasks.shouldHave(exactTexts(taskTexts)); }

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