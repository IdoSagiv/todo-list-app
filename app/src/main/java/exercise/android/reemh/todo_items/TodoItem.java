package exercise.android.reemh.todo_items;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

        @Nullable
        static Status parse(String d) {
            if (d.equals(IN_PROGRESS.asString)) return IN_PROGRESS;
            if (d.equals(DONE.asString)) return DONE;
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return asString;
        }
    }

    private static final String SERIALIZE_SEP = "#!#";

    private final String mId;
    private String mTitle;
    private Status mStatus;
    private String mDescription;
    private final long mCreationTime;
    private long mEditTime;

    private TodoItem(String id, String title, Status status, String desctiption, long creationTime, long editTime) {
        this.mId = id;
        this.mTitle = title;
        this.mStatus = status;
        this.mDescription = desctiption;
        this.mCreationTime = creationTime;
        this.mEditTime = editTime;
    }

    public TodoItem(String description) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = description;
        this.mStatus = Status.IN_PROGRESS;
        this.mDescription = "";
        this.mCreationTime = System.currentTimeMillis();
        this.mEditTime = mCreationTime;

        // force time difference between two items
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(Status status) {
        mStatus = status;
        mEditTime = System.currentTimeMillis();
    }

    public void changeTitle(String title) {
        mTitle = title;
        mEditTime = System.currentTimeMillis();
    }

    public void changeDescription(String description) {
        mDescription = description;
        mEditTime = System.currentTimeMillis();
    }

    public int getStatusIconRes() {
        if (mStatus == Status.DONE) {
            return R.drawable.ic_task_done;
        }
        return R.drawable.ic_task_in_progress;
    }

    public String title() {
        return mTitle;
    }

    public String description() {
        return mDescription;
    }

    public Status status() {
        return mStatus;
    }

    public String id() {
        return mId;
    }

    public long editTime() {
        return mEditTime;
    }

    /**
     * first compare by status (in-progress<done).
     * if status is inProgress second sort by creation time, if status is done second sort by done time.
     *
     * @param other other to-do item to compare to
     * @return cmp>0 if this>other, c,p=0 if this=other, cmp<0 if this<other
     */
    @Override
    public int compareTo(TodoItem other) {
        int cmp1 = mStatus.value - other.mStatus.value;
        int cmp2 = mStatus == Status.IN_PROGRESS ?
                (int) (other.mCreationTime - mCreationTime) : (int) (other.mEditTime - mEditTime);
        return cmp1 != 0 ? cmp1 : cmp2;
    }

    public String creationTimeAsString() {
        Date creationDate = new Date(mCreationTime);
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if (isSameDay(creationDate, currentDate)) {
            if (isSameMinute(creationDate, currentDate)) {
                sdf = new SimpleDateFormat("HH:mm:ss");
            } else sdf = new SimpleDateFormat("HH:mm");
        } else if (isSameYear(creationDate, currentDate)) {
            sdf = new SimpleDateFormat("dd/MM");
        }
        return sdf.format(creationDate);
    }

    private static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    private static boolean isSameYear(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    private static boolean isSameMinute(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("mm");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public String serialize() {
        return mId + SERIALIZE_SEP + mTitle + SERIALIZE_SEP + mStatus + SERIALIZE_SEP + mDescription + SERIALIZE_SEP + mCreationTime + SERIALIZE_SEP + mEditTime;
    }

    public static TodoItem parse(String serialize) {
        try {
            String[] components = serialize.split(SERIALIZE_SEP);
            String id = components[0];
            String title = components[1];
            Status status = Status.parse(components[2]);
            if (status == null) {
                System.out.printf("Error while parsing a TodoItem.\nInput: %s\nException: %s is not a valid status\n", serialize, components[1]);
                return null;
            }

            String description = components[3];
            long creationTime = Long.parseLong(components[4]);
            long editTime = Long.parseLong(components[5]);
            return new TodoItem(id, title, status, description, creationTime, editTime);

        } catch (Exception e) {
            System.out.printf("Error while parsing a TodoItem.\nInput: %s\nException: %s\n", serialize, e.getMessage());
            return null;
        }
    }
}
