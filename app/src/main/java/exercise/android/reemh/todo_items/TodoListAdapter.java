package exercise.android.reemh.todo_items;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoItemHolder> {
    private final ArrayList<TodoItem> mTodoItems = new ArrayList<>();
    OnTaskClickListener onLongPressCallback = null;
    OnTaskClickListener onClickCallback = null;
    OnTaskClickListener onChangeStatusClickCallback = null;

    public void setItems(List<TodoItem> items) {
        mTodoItems.clear();
        mTodoItems.addAll(items);
        Collections.sort(mTodoItems);
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

        holder.title.setText(todoItem.title());

        holder.creationTime.setText(todoItem.creationTimeAsString());

        holder.statusIcon.setImageResource(todoItem.getStatusIconRes());
        if (todoItem.status() == TodoItem.Status.DONE) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title.setPaintFlags(holder.title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.view.setOnLongClickListener(v -> {
            if (onLongPressCallback != null) {
                onLongPressCallback.onClick(todoItem);
            }
            return true;
        });

        holder.view.setOnClickListener(v -> {
            if (onClickCallback != null) {
                onClickCallback.onClick(todoItem);
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
