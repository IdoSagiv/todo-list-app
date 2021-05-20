package exercise.android.reemh.todo_items;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class TodoItemsHolderImplTest {
    private Context context;

    @Before
    public void init() {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        activityController.create().visible();
        context = activityController.get();
    }

    @Test
    public void when_addingTodoItem_then_callingListShouldHaveThisItem() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());

        // test
        holderUnderTest.addNewInProgressItem("do shopping");

        // verify
        Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
    }

    @Test
    public void when_addingTodoItem_then_itsStatusIsInProgress() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);

        // test
        holderUnderTest.addNewInProgressItem("do shopping");
        TodoItem item = holderUnderTest.getCurrentItems().get(0);

        // verify
        Assert.assertEquals(TodoItem.Status.IN_PROGRESS, item.status());
    }


    @Test
    public void when_deleteTodoItem_then_callingListShouldNotHaveThisItem() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("do shopping");
        holderUnderTest.addNewInProgressItem("clean home");
        TodoItem item = holderUnderTest.getCurrentItems().get(0);
        Assert.assertEquals(2, holderUnderTest.getCurrentItems().size());

        // test
        holderUnderTest.deleteItem(item);

        // verify
        Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
        Assert.assertFalse(holderUnderTest.getCurrentItems().contains(item));
    }

    @Test
    public void when_DeleteAllItems_then_theListIsEmpty() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("do shopping");
        Assert.assertEquals(1, holderUnderTest.getCurrentItems().size());
        TodoItem item = holderUnderTest.getCurrentItems().get(0);
        // test
        holderUnderTest.deleteItem(item);

        // verify
        Assert.assertEquals(0, holderUnderTest.getCurrentItems().size());
    }

    @Test
    public void when_changeTodoItemToDone_then_itsStatusIsDone() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("do shopping");
        TodoItem item = holderUnderTest.getCurrentItems().get(0);

        // test
        holderUnderTest.markItemDone(item);

        // verify
        Assert.assertEquals(TodoItem.Status.DONE, item.status());
    }

    @Test
    public void when_changeTodoItemToInProgress_then_itsStatusIsInProgress() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("do shopping");
        TodoItem item = holderUnderTest.getCurrentItems().get(0);
        holderUnderTest.markItemDone(item);

        // test
        holderUnderTest.markItemInProgress(item);

        // verify
        Assert.assertEquals(TodoItem.Status.IN_PROGRESS, item.status());
    }

    @Test
    public void when_addNewTwoItems_then_theLastToEnterIsTheFirstInTheList() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("old");

        // test
        holderUnderTest.addNewInProgressItem("new");

        // verify
        Assert.assertEquals("new", holderUnderTest.getCurrentItems().get(0).title());
    }

    @Test
    public void when_markItemDone_then_itIsAfterInProgressItemsInTheList() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("old");
        TodoItem oldItem = holderUnderTest.getCurrentItems().get(0);
        holderUnderTest.addNewInProgressItem("new");
        TodoItem newItem = holderUnderTest.getCurrentItems().get(0);
        List<TodoItem> currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(newItem) < currItems.indexOf(oldItem));
        // test
        holderUnderTest.markItemDone(newItem);

        // verify
        currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(newItem) > currItems.indexOf(oldItem));
    }

    @Test
    public void when_changeItemFromDoneToInProgress_then_itIsBackInHisOrigLocation() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("old");
        TodoItem oldItem = holderUnderTest.getCurrentItems().get(0);
        holderUnderTest.addNewInProgressItem("new");
        TodoItem newItem = holderUnderTest.getCurrentItems().get(0);
        holderUnderTest.markItemDone(newItem);
        List<TodoItem> currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(newItem) > currItems.indexOf(oldItem));
        // test
        holderUnderTest.markItemInProgress(newItem);
        // verify
        currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(newItem) < currItems.indexOf(oldItem));
    }

    @Test
    public void when_twoItemsAreDone_then_theLastToDoneComesBeforeTheFirst() {
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl(context);
        holderUnderTest.addNewInProgressItem("first done");
        TodoItem first = holderUnderTest.getCurrentItems().get(0);
        holderUnderTest.addNewInProgressItem("second done");
        TodoItem second = holderUnderTest.getCurrentItems().get(0);

        List<TodoItem> currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(second) < currItems.indexOf(first));

        // test
        holderUnderTest.markItemDone(first);
        holderUnderTest.markItemDone(second);

        // verify
        currItems = holderUnderTest.getCurrentItems();
        Assert.assertTrue(currItems.indexOf(second) < currItems.indexOf(first));
    }

    // TODO: add at least 1 more tests

    // todo: all the tests that test the items order are not relevant anymore
}