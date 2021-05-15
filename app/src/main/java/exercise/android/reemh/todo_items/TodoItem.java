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
    private final long mCreationTime;

    public TodoItem(String description) {
        this.mDescription = description;
        this.mStatus = Status.IN_PROGRESS;
        this.mCreationTime = System.currentTimeMillis();
    }

    public void changeStatus(Status status) {
        mStatus = status;
    }

    public int getBackgroundRes() {
        // todo: different color according to status? if not - set background in xml
        return R.drawable.light_blue_rect_with_rounded_corners;
    }

    public int getStatusIconRes() {
        // todo: icon or checkbox?
        if (mStatus == Status.DONE) {
            return R.drawable.ic_task_done;
        }
        return R.drawable.ic_task_in_progress;
    }

    public String description() {
        return mDescription;
    }

    public Status status() {
        return mStatus;
    }

    /**
     * first compare by status (in-progress<done) second compare by editTime
     *
     * @param other other to-do item to compare to
     * @return cmp>0 if this>other, c,p=0 if this=other, cmp<0 if this<other
     */
    @Override
    public int compareTo(TodoItem other) {
        int cmp = mStatus.value - other.mStatus.value;
        cmp = cmp != 0 ? cmp : (int) (other.mCreationTime - mCreationTime);
        return cmp;
    }
}
