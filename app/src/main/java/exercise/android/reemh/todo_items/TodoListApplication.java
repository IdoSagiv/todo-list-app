package exercise.android.reemh.todo_items;

import android.app.Application;

public class TodoListApplication extends Application {
    private TodoItemsHolderImpl database;
    private static TodoListApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = new TodoItemsHolderImpl(this);
    }

    public TodoItemsHolderImpl getDatabase() {
        return database;
    }

    public static TodoListApplication getInstance() {
        return instance;
    }
}
