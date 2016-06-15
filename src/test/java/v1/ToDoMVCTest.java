package v1;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class ToDoMVCTest {

    @Test
    public void testTaskManage() {
        open("https://todomvc4tasj.herokuapp.com/");

        $("#new-todo").setValue("task1").pressEnter();
        $("#new-todo").setValue("task2").pressEnter();
        $("#new-todo").setValue("task3").pressEnter();
        $("#new-todo").setValue("task4").pressEnter();
        $$("#todo-list li").shouldHave(exactTexts("task1", "task2", "task3", "task4"));

        $("#todo-list li", 1).hover().$(".destroy").hover().click();
        $$("#todo-list li").shouldHave(exactTexts("task1", "task3", "task4"));

        $("#todo-list li", 2).$(".toggle").click();
        $("#clear-completed").click();
        $$("#todo-list li").shouldHave(exactTexts("task1", "task3"));

        $("#toggle-all").click();
        $("#clear-completed").click();
        $$("#todo-list li").shouldBe(empty);
    }
}