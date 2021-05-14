package exercise.android.reemh.todo_items;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoItemHolder> {
    private final ArrayList<TodoItem> mTodoItems = new ArrayList<>();
    OnTaskClickListener onDeleteClickCallback = null;
    OnTaskClickListener onChangeStatusClickCallback = null;

    public void setItems(TodoItemsHolder todoItemsHolder) {
        mTodoItems.clear();
        mTodoItems.addAll(todoItemsHolder.getCurrentItems());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_todo_item, parent, false);
        return new TodoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemHolder holder, int position) {
        TodoItem todoItem = mTodoItems.get(position);

        holder.description.setText(todoItem.description());
        holder.description.setBackgroundResource(todoItem.getBackgroundRes());
        holder.statusIcon.setImageResource(todoItem.getStatusIconRes());
        if (todoItem.status() == TodoItem.Status.DONE) {
            holder.description.setPaintFlags(holder.description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.description.setPaintFlags(holder.description.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.deleteBtn.setOnClickListener(v -> {
            if (onDeleteClickCallback != null) {
                onDeleteClickCallback.onClick(todoItem);
            }
        });

        holder.statusIcon.setOnClickListener(v -> {
            if (onChangeStatusClickCallback != null) {
                onChangeStatusClickCallback.onClick(todoItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }
}
