package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoItemHolder extends RecyclerView.ViewHolder {
    TextView title;
    ImageView statusIcon;
    TextView creationTime;
    View view;

    public TodoItemHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        title = itemView.findViewById(R.id.textViewTodoTaskTitle);
        statusIcon = itemView.findViewById(R.id.imageViewTodoStatus);
        creationTime = view.findViewById(R.id.textViewCreationDate);
    }
}
