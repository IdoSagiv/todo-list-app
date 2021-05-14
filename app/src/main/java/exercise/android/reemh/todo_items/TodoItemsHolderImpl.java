package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class TodoItemsHolderImpl implements TodoItemsHolder {
    //ToDo: best collection type?
    private final ArrayList<TodoItem> items;

    public TodoItemsHolderImpl() {
        items = new ArrayList<>();
    }

    @Override
    public List<TodoItem> getCurrentItems() {
        Collections.sort(items);
        return items;
    }

    @Override
    public void addNewInProgressItem(String description) {
        items.add(new TodoItem(description));
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
