package exercise.android.reemh.todo_items;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TodoItem implements Serializable {
    public static enum Status {
        IN_PROGRESS("in progress"),
        DONE("done");
        String asString;

        private Status(String asString) {
            this.asString = asString;
        }

        @NonNull
        @Override
        public String toString() {
            return asString;
        }
    }

    private Status mStatus;
    private final String mDescription;

    public TodoItem(String description) {
        this.mDescription = description;
        this.mStatus = Status.IN_PROGRESS;
    }

    public void changeStatus(Status status) {
        mStatus = status;
    }

    public int getBackgroundRes() {
        // todo: implement
        return R.drawable.light_blue_rect_with_rounded_corners;
    }

    public int getStatusIconRes() {
        // todo: implement
        switch (mStatus) {
            case IN_PROGRESS:
                return R.drawable.ic_task_in_progres_v;
            case DONE:
                return R.drawable.ic_task_done_v;
            default:
                return R.drawable.ic_task_not_started;
        }
    }

    public String description() {
        return mDescription;
    }

    public Status status() {
        return mStatus;
    }
}
