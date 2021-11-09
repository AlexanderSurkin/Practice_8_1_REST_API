package com.nc.edu.ta.aleksandrsurkin.pr6;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class "ArrayTaskList" extends class "AbstractTaskList" and
 * realise methods of abstract class "AbstractTaskList".
 */
public class ArrayTaskList extends AbstractTaskList {

    Task [] tasks = new Task[10];
    final int ARRAY_BUFFER_INC = 10;
    static private int numberOfTasksListsCounter;
    final String THEME_OF_TITLE = "[EDUCTR][TA]";

    /**
     * This method is set values for new tasks list.
     */
    public ArrayTaskList() {
        numberOfTasksListsCounter++;
    }

    /**
     * This method is validate that "task" is not Null.
     * @param object input object for "Null - check".
     */
    void validateTask(Task object) {
        if (object == null) {
            throw new NullPointerException("The <task> can not be Null");
        }
    }

//    public int getMax() {
//        int max = 0;
//        for (int i = 0; i < sizeCounter; i++) {
//            if (tasks[i].getTime() > max) {
//                max = tasks[i].getTime();
//            }
//        }
//        return max;
//    }

    /**
     * This method is return object of Iterator.
     * @return object of Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayIterator();
    }

    /**
     * This class is implements interface "Iterator".
     */
    public class ArrayIterator implements Iterator {

        private int nextElement  = 0;
        private int checkNextElement = 0;

        /**
         * This method is check that next element from Iterator is not null.
         * @return boolean value of next iteration.
         */
        @Override
        public boolean hasNext() {
            return nextElement < sizeCounter;
        }

        /**
         * This method returns next task from array list.
         * @return element after iteration.
         */
        @Override
        public Task next() {
            Task result = tasks[nextElement];
            nextElement = nextElement + 1;

            if (nextElement == 0 || nextElement > sizeCounter) {
                throw new NoSuchElementException();
            } else {
                return result;
            }

        }

        /**
         * This method is remove element after iteration.
         */
        @Override
        public void remove() {
            if (checkNextElement == nextElement) {
                throw new IllegalStateException();
            }
            checkNextElement = nextElement;
            if (nextElement - 1 < sizeCounter - 1) {
                for (int i = nextElement - 1; i < sizeCounter - 1; ++i) {
                    tasks[i] = tasks[i + 1];
                }
            }
            sizeCounter--;
        }
    }

    /**
     * This method is realise actions of method "abstract void add(Task task)" from class "AbstractTaskList",
     * adding theme to tasks title and counts the number of incoming tasks.
     * @param task input of task.
     */
    @Override
    public void add(Task task) {

        validateTask(task);

        if (sizeCounter >= tasks.length) {
            Task[] middleMassive = new Task[tasks.length + ARRAY_BUFFER_INC];
            for (int i = 0; i < tasks.length; i++) {
                middleMassive[i] = tasks[i];
            }
            tasks = middleMassive;
        }

        String taskTitle = task.getTitle();
        if (!taskTitle.startsWith(THEME_OF_TITLE)) {
            task.setTitle(THEME_OF_TITLE + taskTitle);
        }
//        String taskTitle = task.getTitle();
//        task.setTitle(THEME_OF_TITLE + taskTitle);

        tasks[sizeCounter++] = task;
    }



    /**
     * This method is realise actions of method "abstract void remove(Task task)" from class "AbstractTaskList",
     * removing task from tasks list if argument "task" equals task from tasks list.
     * @param task input of task.
     */
    @Override
    public void remove(Task task) { // переделать удаление через временный массив

        validateTask(task);

        int localCounter = 0;

        String taskTitle = task.getTitle();
        if (!taskTitle.startsWith(THEME_OF_TITLE)) {
            task.setTitle(THEME_OF_TITLE + taskTitle);
        }

        Task[] temporaryMassive = new Task[sizeCounter];
        for (int i = 0; i < sizeCounter + 1; i++) {
             if (tasks[i].equals(task) || tasks[i] == task) {  //првоерить
                 sizeCounter--;
             } else {
                 temporaryMassive[localCounter++] = tasks[i];
             }
        }
        tasks = temporaryMassive;
    }

//    /**
//     * This method is realise actions of method "abstract Task getTask(int index)" from
//     * class "AbstractTaskList" and returning task from its index in tasks list.
//     * @param index index of task from tasks list.
//     * @return task or null.
//     */
//    @Override
//    public Task getTask(int index) {
//
//        if (index < 0) {
//            throw new IllegalArgumentException("The <time> can not be a negative, try to add a positive value");
//        }
//        if (size() < 0) {
//            throw new IllegalArgumentException("The <size()> can not be a negative, try to add a positive value");
//        }
//        if (index >= size()) {
//            throw new IllegalArgumentException("The <index> can not be more or equal than <size()>");
//        }
//
//        return tasks[index];
//    }

    /**
     * This method return a list with tasks which time is between "from" and "to".
     * @param from start time of the time range.
     * @param to end time of the time range.
     * @return list with found tasks.
     */
    public Task[] incoming(int from, int to) {

        if (from < 0 || to < 0) {
            throw new IllegalArgumentException ("You can't use a negative number");
        }
        if (to < from) {
            throw new IllegalArgumentException("<to> can not be less than <from>");
        }

        Task [] incTasks = new Task [sizeCounter];
        Task [] emptyMassive = new Task [0];
        int incTaskCounter = 0;
        for (Task task : tasks) {
            if (task != null) {
                if (task.nextTimeAfter(from) > from && task.nextTimeAfter(from) <= to) {
                    if (task.isActive()) {
                        incTasks[incTaskCounter++] = task;
                    }
                }
            }
        }
        if (incTaskCounter == 0) {
            incTasks = emptyMassive;
            return incTasks;
        } else {
            Task [] countedMassive = new Task [incTaskCounter];
            for (int i = 0; i < countedMassive.length; i++) {
                if (incTasks[i] != null) {
                    countedMassive[i] = incTasks[i];
                }
            }
            return countedMassive;
        }
    }


}
