package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemHolder extends RecyclerView.ViewHolder {
    TextView description;
    ImageView deleteBtn;
    ImageView statusIcon;

    public TodoItemHolder(@NonNull View itemView) {
        super(itemView);
        description = itemView.findViewById(R.id.textViewTodoTaskDescription);
        deleteBtn = itemView.findViewById(R.id.buttonDeleteTask);
        statusIcon = itemView.findViewById(R.id.imageViewTodoStatus);
    }
}
