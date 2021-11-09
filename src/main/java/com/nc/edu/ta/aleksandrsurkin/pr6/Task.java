package com.nc.edu.ta.aleksandrsurkin.pr6;

import java.util.Objects;

/**
 * This is class "Task". This class contains are methods, constructors and
 * fields for the program named "The Task".
 */
public class Task implements Cloneable {

    private String title;
    private boolean active;
    private int time;
    private int start;
    private int end;
    private int repeat;

    public Task(String title, int time) {

        if (title == null || title == "") {
            throw new NullPointerException("The <title> is null, try to add a not null name of <title>");
        }
        validateTime(time);

        this.title = title;
        this.time = time;
        this.start = time;
        this.end = time;
    }

    public Task(String title, int start, int end, int repeat) {

        if (title == null || title.equals("")) {
            throw new NullPointerException("The <title> is null, try to add a not null <title>");
        }
        if (start < 0 || end < 0 || repeat < 0) {
            throw new IllegalArgumentException("The <time> can not be a negative, try to add a positive value");
        }
        if (end < start) {
            throw new IllegalArgumentException("The <end> can not be less than <start>");
        }

        this.title = title;
        this.start = start;
        this.time = start;
        this.end = end;
        this.repeat = repeat;
    }

    /**
     * This method returns cloned task.
     * @return cloned task.
     */
    @Override
    public Task clone()  {
        try {
            Task task = (Task) super.clone();
            return task;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method returns boolean value of comparing two tasks.
     * @param task input task for comparing.
     * @return boolean value of comparing two tasks.
     */
    @Override
    public boolean equals(Object task) {
        if (task == null) {
            return false;
        } else if (!(this.getClass() == task.getClass())) {
            return false;
        } else if (this == task) {
            return true;
        } else if (this.hashCode() != task.hashCode()) {
            return false;
        } else if (!(this.active == ((Task) task).active)) {
            return false;
        } else {
            return Objects.equals(this.title, ((Task) task).title)
                    && this.time == ((Task) task).time
                    && this.start == ((Task) task).start
                    && this.end == ((Task) task).end
                    && this.repeat == ((Task) task).repeat;
        }
    }

    /**
     * This method returns integer hashcode of task.
     * @return  hashcode of task.
     */
    @Override
    public int hashCode() {
        return 30 * time + 32 * start + 30 * end + 31 * (repeat + 32 * title.hashCode());
    }

    /**
     * This method is validate that input value is positive or negative.
     * @param value input value of time.
     */
    void validateTime(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The <time> can not be a negative, try to add a positive value");
        }
    }

    /**
     * This method is return "title" name of a task.
     * @return title - title of a task.
     */
    public String getTitle() {
        return title.replace("[EDUCTR][TA]", "");
    }

    /**
     * This method is set a value in the "title" field.
     * @param title name of a title.
     */
    public void setTitle(String title) {

        if (title == null || title.equals("")) {
            throw new NullPointerException("The <title> is null, try to add a not null <title>");
        }

        this.title = title;
    }

    /**
     * This method is return a boolean value of a task activity.
     * @return active boolean value of a task activity.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method is set a boolean value of a task activity.
     * @param active boolean value of a task activity.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method is set a time of an one-time task.
     * @param time task alert time.
     */
    public void setTime(int time) {

        validateTime(time);

        this.time = time;
        this.start = time;
        this.end = time;
        this.repeat = 0;
    }

    /**
     * This method is set parameters of a recurring task.
     * @param start start time of task notification.
     * @param end time to stop task alert.
     * @param repeat time interval after which it is necessary to repeat the task notification.
     */
    public void setTime(int start, int end, int repeat) {

        if (start < 0 || end < 0 || repeat < 0) {
            throw new IllegalArgumentException("The <time> can not be a negative, try to add a positive value");
        }
        if (end < start) {
            throw new IllegalArgumentException("The <end> can not be less than <start>");
        }

        this.time = start;
        this.start = start;
        this.end = end;
        this.repeat = repeat;
    }

    /**
     * This method is return a task alert time.
     * @return time - returns a one-time task notification.
     */
    public int getTime() {
        return time;
    }

    /**
     * This method is return a task alert time.
     * @return start - returns a start time of a recurring task notification.
     */
    public int getStartTime() {
        return start;
    }

    /**
     * This method is return the end of an alert time.
     * @return end or time.
     */
    public int getEndTime() {
        return end;
    }

    /**
     * This method is return a time the time interval after which the
     * task notification must be repeated (for a recurring task) or
     * 0 (for a one-time task).
     * @return repeat.
     */
    public int getRepeatInterval() {
        return repeat;
    }

    /**
     * This method is return information about the task is repeated or not.
     * @return start, if start more than 0.
     */
    public boolean isRepeated() {
        return repeat > 0;
    }

    /**
     * This method is return a description of the task.
     * @return string with title, values of arguments and statuses.
     */
    public String toString() {
        if (!active) {
            return "Task \""  + this.title + "\" is inactive";
        }
        if (repeat == 0) {
            return "Task \""  + this.title + "\" at " + this.time;
        }
        if (repeat > 0) {
            return "Task \"" + this.title + "\" from " + this.start + " to " +
                    this.end + " every " + this.repeat + " seconds";
        }
        return "";
    }

    /**
     * This method is return a time of next alert after specified
     * time(without "time").
     * @return result - time of the next alert or -1.
     * @param time value of input time.
     */
    public int nextTimeAfter(int time) {

        validateTime(time);

        if (!this.active) {
            return -1;
        }
        if (time < this.start) {
            return this.start;
        }
        int timeNextAlert = this.repeat + this.start;

        while (timeNextAlert <= time && timeNextAlert < this.end) {
            timeNextAlert = this.repeat + timeNextAlert;
        }
        if (timeNextAlert > time && timeNextAlert <= end) {          //может быть равно end
            return timeNextAlert;
        } else {
            return -1;
        }

    }

}
