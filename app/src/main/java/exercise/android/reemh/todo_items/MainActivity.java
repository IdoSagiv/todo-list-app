package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // in tests can inject value
    public TodoItemsHolderImpl itemsHolder = null;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new TodoListAdapter();

        if (itemsHolder == null) {
            itemsHolder = TodoListApplication.getInstance().getItemsDatabase();
        }

        itemsHolder.itemsLiveData.observe(this, todoItems -> {
            adapter.setItems(itemsHolder.getCurrentItems());
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter.onLongPressCallback = item -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        itemsHolder.deleteItem(item);
                    }
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.delete_task_question))
                    .setPositiveButton(getString(R.string.yes), dialogClickListener)
                    .setNegativeButton(getString(R.string.no), dialogClickListener).show();
        };

        adapter.onChangeStatusClickCallback = item -> {
            if (item.status() == TodoItem.Status.IN_PROGRESS) {
                itemsHolder.markItemDone(item);
            } else {
                itemsHolder.markItemInProgress(item);
            }
        };

        adapter.onClickCallback = item -> {
            Intent editItemIntent = new Intent(this, EditItemActivity.class);
            editItemIntent.putExtra("item_to_edit", item.id());
            startActivity(editItemIntent);
        };

        FloatingActionButton createTaskBtn = findViewById(R.id.buttonCreateTodoItem);
        EditText insertTaskEditText = findViewById(R.id.editTextInsertTask);

        createTaskBtn.setOnClickListener(v -> {
            if (insertTaskEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.task_title_not_entered_toast),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            itemsHolder.addNewInProgressItem(insertTaskEditText.getText().toString());
            insertTaskEditText.setText("");
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText insertTaskEditText = findViewById(R.id.editTextInsertTask);
        outState.putString("editTextContent", insertTaskEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String text = savedInstanceState.getString("editTextContent", null);
        if (text != null) {
            EditText insertTaskEditText = findViewById(R.id.editTextInsertTask);
            insertTaskEditText.setText(text);
        }
    }
}
