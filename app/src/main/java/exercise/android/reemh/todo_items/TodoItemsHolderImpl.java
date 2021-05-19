package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoItemsHolderImpl implements TodoItemsHolder {
    private final ArrayList<TodoItem> items = new ArrayList<>();

    private final SharedPreferences sp;

    private final MutableLiveData<List<TodoItem>> itemsMutableLiveData = new MutableLiveData<>();
    public final LiveData<List<TodoItem>> itemsLiveData = itemsMutableLiveData;

    public TodoItemsHolderImpl(Context context) {
        this.sp = context.getSharedPreferences("local_items_db", Context.MODE_PRIVATE);
        initializeFromSp();
    }

    private void initializeFromSp() {
        for (String key : sp.getAll().keySet()) {
            String itemSerialize = sp.getString(key, null);
            if (itemSerialize == null) return;
            TodoItem itemToAdd = TodoItem.parse(itemSerialize);
            if (itemToAdd != null) items.add(itemToAdd);
        }
        itemsMutableLiveData.setValue(getCurrentItems());
    }

    @Override
    public List<TodoItem> getCurrentItems() {
        Collections.sort(items); // todo: move the sort to somewhere else??
        return new ArrayList<>(items);
    }

    @Override
    public void addNewInProgressItem(String description) {
        TodoItem newItem = new TodoItem(description);
        items.add(newItem);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(newItem.id(), newItem.serialize());
        editor.apply();
        itemsMutableLiveData.setValue(getCurrentItems());
    }

    @Override
    public void markItemDone(TodoItem item) {
        item.changeStatus(TodoItem.Status.DONE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.id(), item.serialize());
        editor.apply();

        itemsMutableLiveData.setValue(getCurrentItems());
    }

    @Override
    public void markItemInProgress(TodoItem item) {
        item.changeStatus(TodoItem.Status.IN_PROGRESS);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.id(), item.serialize());
        editor.apply();

        itemsMutableLiveData.setValue(getCurrentItems());
    }

    @Override
    public void deleteItem(TodoItem item) {
        items.remove(item);

        SharedPreferences.Editor editor = sp.edit();
        editor.remove(item.id());
        editor.apply();

        itemsMutableLiveData.setValue(getCurrentItems());
    }
}
