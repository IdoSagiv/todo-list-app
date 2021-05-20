package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditItemActivity extends AppCompatActivity {
    // in tests can inject value
    public TodoItemsHolderImpl itemsHolder = null;

    private CheckBox taskStatusCheckBox;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private TextView taskLastEditTimeTextView;
    private TextView taskCreateTimeTextView;
    private TodoItem itemToEdit;
    private String titleBeforeEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_item);

        if (itemsHolder == null) {
            itemsHolder = TodoListApplication.getInstance().getItemsDatabase();
        }

        Intent intent = getIntent();
        if (!intent.hasExtra("item_to_edit")) {
            finish();
            return;
        }
        itemToEdit = itemsHolder.getItem(intent.getStringExtra("item_to_edit"));
        if (itemToEdit == null) {
            finish();
            return;
        }

        titleBeforeEdit = itemToEdit.title();
        taskStatusCheckBox = findViewById(R.id.checkboxTaskStatusInEditActivity);
        taskTitleEditText = findViewById(R.id.editTextTaskTitleInEditActivity);
        taskDescriptionEditText = findViewById(R.id.editTextTaskDescriptionInEditActivity);
        taskLastEditTimeTextView = findViewById(R.id.textViewTaskEditTimeInEditActivity);
        taskCreateTimeTextView = findViewById(R.id.textViewTaskCreateTimeInEditActivity);

        // initialize views content
        taskStatusCheckBox.setChecked(itemToEdit.status() == TodoItem.Status.DONE);
        taskTitleEditText.setText(itemToEdit.title());
        taskDescriptionEditText.setText(itemToEdit.description());
        taskLastEditTimeTextView.setText(String.format("last modified %s", lastModifiedText(itemToEdit)));
        taskCreateTimeTextView.setText(String.format("Created at %s", itemToEdit.creationTimeAsString()));

        taskStatusCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                itemsHolder.markItemDone(itemToEdit);
            } else {
                itemsHolder.markItemInProgress(itemToEdit);
            }
            taskLastEditTimeTextView.setText(String.format("last modified %s", lastModifiedText(itemToEdit)));
        });

        taskTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (taskTitleEditText.getText().toString().isEmpty()) {
                    taskTitleEditText.setError("title is required");
                } else {
                    itemsHolder.changeItemTitle(itemToEdit, taskTitleEditText.getText().toString());
                    taskLastEditTimeTextView.setText(String.format("last modified %s", lastModifiedText(itemToEdit)));
                }
            }
        });

        taskDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                itemsHolder.changeItemDescription(itemToEdit, taskDescriptionEditText.getText().toString());
                taskLastEditTimeTextView.setText(String.format("last modified %s", lastModifiedText(itemToEdit)));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_checked", taskStatusCheckBox.isChecked());
        outState.putString("title_text_view_text", taskTitleEditText.getText().toString());
        outState.putString("description_text_view_text", taskDescriptionEditText.getText().toString());
        outState.putString("edit_time_text_view_text", taskLastEditTimeTextView.getText().toString());
        outState.putString("create_time_text_view_text", taskCreateTimeTextView.getText().toString());
        outState.putString("title_before_edit", titleBeforeEdit);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        taskStatusCheckBox.setChecked(savedInstanceState.getBoolean("is_checked", false));
        taskTitleEditText.setText(savedInstanceState.getString("title_text_view_text", ""));
        taskDescriptionEditText.setText(savedInstanceState.getString("description_text_view_text", ""));
        taskLastEditTimeTextView.setText(savedInstanceState.getString("edit_time_text_view_text", ""));
        taskCreateTimeTextView.setText(savedInstanceState.getString("create_time_text_view_text", ""));
        titleBeforeEdit = savedInstanceState.getString("title_before_edit");
    }

    // todo: move to TodoItem class??
    private String lastModifiedText(TodoItem item) {
        long currentTimeMillis = System.currentTimeMillis();
        Date editDate = new Date(item.editTime());
        Date currentDate = new Date(currentTimeMillis);
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
        SimpleDateFormat dateSdf = new SimpleDateFormat("dd/MM/yy");

        if (dateSdf.format(editDate).equals(dateSdf.format(currentDate))) {
            if (hourSdf.format(editDate).equals(hourSdf.format(currentDate))) {
                return TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis - item.editTime()) + " minutes ago";
            } else {
                return "today at " +  new SimpleDateFormat("HH:mm").format(editDate);
            }
        } else {
            return String.format("at %s at %s", dateSdf.format(editDate), hourSdf.format(editDate));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (taskTitleEditText.getText().toString().isEmpty()) {
            itemToEdit.changeTitle(titleBeforeEdit);
        }
    }
}
