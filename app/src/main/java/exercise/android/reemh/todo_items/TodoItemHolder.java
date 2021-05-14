package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemHolder extends RecyclerView.ViewHolder {
    TextView description;
    ImageView statusIcon;
    View view;

    public TodoItemHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        description = itemView.findViewById(R.id.textViewTodoTaskDescription);
        statusIcon = itemView.findViewById(R.id.imageViewTodoStatus);
    }
}
