package exercise.android.reemh.todo_items;

import android.app.Application;

public class TodoListApplication extends Application {
    private TodoItemsHolderImpl itemsDatabase;
    private static TodoListApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        itemsDatabase = new TodoItemsHolderImpl(this);
    }

    public TodoItemsHolderImpl getItemsDatabase() {
        return itemsDatabase;
    }

    public static TodoListApplication getInstance() {
        return instance;
    }
}
