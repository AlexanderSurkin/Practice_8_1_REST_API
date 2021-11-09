package com.nc.edu.ta.aleksandrsurkin.pr6;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Objects;

/**
 * This abstract class "AbstractTaskList".
 * This class contains abstract methods for add task, remove task, and get task.
 * This class contains one public method for the getting size of task.
 * This class contains one integer field: counter for count of tasks.
 */
public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {


    int sizeCounter = 0;


    /**
     * This method is adding the not unique tasks into the tasks list.
     * @param task - input of task.
     */
    public abstract void add(Task task);

    /**
     * This method is for removing tasks out of a tasks list.
     * @param task - input of task.
     */
    public abstract void remove(Task task);

    /**
     * This method is counting quantity of tasks;
     * @return quantity of tasks;
     */
    public int size() {
        return sizeCounter;
    }

//    /**
//     * This method is get the tasks from their input indexes.
//     * @param index - index of task from tasks list.
//     * @return tasks.
//     */
//    public abstract Task getTask(int index);

    /**
     * This method return a list with tasks which time is between "from" and "to".
     * @param from start time of the time range.
     * @param to end time of the time range.
     * @return list with found tasks.
     */
    public abstract Task[] incoming(int from, int to);

    /**
     * This method is for cloning tasks lists.
     * @return cloned tasks list.
     */
    @Override
    public AbstractTaskList clone() throws CloneNotSupportedException {
        Class<?> cl = this.getClass();
        try {
            Constructor<?> cons = cl.getConstructor();
            AbstractTaskList clone = (AbstractTaskList) cons.newInstance();
            for (Task t : this) {
                clone.add(t.clone());
            }
            return clone;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is for calling iterators out from tasks lists.
     * @return object of Iterator method.
     */
    public abstract Iterator iterator();

    /**
     * This method returns a String wiev of tasks list.
     */
    @Override
    public String toString() {
        String getClassName = this.getClass().getSimpleName();
        String massiveString = "";

        for (Task t : this) {
            massiveString = massiveString + t.toString();

        }

        return getClassName + " [" + massiveString + "]";

    }

    /**
     * This method returns boolean value of comparing two tasks lists.
     * @param o input parameter for comparing.
     * @return boolean value of comparing.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractTaskList tasks = (AbstractTaskList) o;
        return sizeCounter == tasks.sizeCounter;
    }

    /**
     * This method returns integer value of hashcode for comparing.
     * @return value of hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sizeCounter);
    }

//    public abstract int getMax();

}
