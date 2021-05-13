package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

// TODO: implement!
public class TodoItemsHolderImpl implements TodoItemsHolder {
    //ToDo: best collection type?
    private final ArrayList<TodoItem> items;

    public TodoItemsHolderImpl() {
        items = new ArrayList<>();
    }

    @Override
    public List<TodoItem> getCurrentItems() {
        return items;
    }

    @Override
    public void addNewInProgressItem(String description) {
        items.add(0,new TodoItem(description));
    }

    @Override
    public void markItemDone(TodoItem item) {
        item.changeStatus(TodoItem.Status.DONE);
    }

    @Override
    public void markItemInProgress(TodoItem item) {
        item.changeStatus(TodoItem.Status.IN_PROGRESS);
    }

    @Override
    public void deleteItem(TodoItem item) {
        items.remove(item);
    }
}
