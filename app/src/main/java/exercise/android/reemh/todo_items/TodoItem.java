package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TodoItem implements Serializable, Comparable<TodoItem> {
    public enum Status {
        IN_PROGRESS("in progress", 0),
        DONE("done", 1);
        private final String asString;
        private final int value;

        Status(String asString, int value) {
            this.asString = asString;
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return asString;
        }
    }

    private Status mStatus;
    private final String mDescription;
    private final long mCreateTime;

    public TodoItem(String description) {
        this.mDescription = description;
        this.mStatus = Status.IN_PROGRESS;
        this.mCreateTime = System.currentTimeMillis();
    }

    public void changeStatus(Status status) {
        mStatus = status;
    }

    public int getBackgroundRes() {
        // todo: different color according to status?
        return R.drawable.light_blue_rect_with_rounded_corners;
    }

    public int getStatusIconRes() {
        // todo: icon or checkbox?
        switch (mStatus) {
            case IN_PROGRESS:
                return R.drawable.ic_task_in_progres;
            case DONE:
                return R.drawable.ic_task_done;
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

    /**
     * first compare by status (in-progres<done) second compare by editTime
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(TodoItem o) {
        // in-progres<done && new<old
        int cmp = mStatus.value - o.mStatus.value;
        cmp = cmp != 0 ? cmp : (int) (o.mCreateTime - mCreateTime);
        return cmp;
    }
}
