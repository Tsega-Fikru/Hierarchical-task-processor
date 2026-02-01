import java.util.*;

class Scheduler {

    private int clock = 0;

    // 0 = High, 1 = Medium, 2 = Low
    private List<Queue<Task>> priorityLevels;

    // Pending Area (min-heap)
    private PriorityQueue<Task> futureTaskHeap;

    private int completedTasks = 0;

    Scheduler() {

        priorityLevels = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            priorityLevels.add(new LinkedList<>());
        }

        futureTaskHeap = new PriorityQueue<>(
                Comparator.comparingInt(t -> t.arrivalTime)
        );
    }

    public void scheduleTask(String id, int work, int arrival) {
        futureTaskHeap.add(new Task(id, work, arrival));
    }

    public void advanceClock() {
        clock++;

        handleArrivals();
        executeOneUnit();
        handleStarvation();
    }

    // Move arrived tasks to Queue A
    private void handleArrivals() {
        while (!futureTaskHeap.isEmpty()
                && futureTaskHeap.peek().arrivalTime <= clock) {
            priorityLevels.get(0).add(futureTaskHeap.poll());
        }
    }

    // Execute one unit with PREEMPTION
    private void executeOneUnit() {

        Task currentTask = null;
        int currentLevel = -1;

        // Pick highest available queue
        for (int i = 0; i < 3; i++) {
            if (!priorityLevels.get(i).isEmpty()) {
                currentTask = priorityLevels.get(i).poll();
                currentLevel = i;
                break;
            }
        }

        if (currentTask == null) return;

        /*
         * PREEMPTION:
         * If a low-priority task is selected but
         * a higher-priority task exists, do not run it.
         */
        if (currentLevel == 2 &&
                (!priorityLevels.get(0).isEmpty()
                        || !priorityLevels.get(1).isEmpty())) {

            priorityLevels.get(2).add(currentTask);
            return;
        }

        // Run one unit
        currentTask.remainingWork--;
        currentTask.timeUsedInLevel++;
        currentTask.lastExecutionTime = clock;

        if (currentTask.remainingWork == 0) {
            completedTasks++;
            return;
        }

        // Demotion rules
        if (currentLevel == 0 && currentTask.timeUsedInLevel == 2) {
            currentTask.timeUsedInLevel = 0;
            priorityLevels.get(1).add(currentTask);
        }
        else if (currentLevel == 1 && currentTask.timeUsedInLevel == 4) {
            currentTask.timeUsedInLevel = 0;
            priorityLevels.get(2).add(currentTask);
        }
        else {
            priorityLevels.get(currentLevel).add(currentTask);
        }
    }

    // Aging rule
    private void handleStarvation() {

        Iterator<Task> iterator = priorityLevels.get(2).iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (clock - task.lastExecutionTime >= 15) {
                iterator.remove();
                task.timeUsedInLevel = 0;
                priorityLevels.get(0).add(task);
            }
        }
    }

    public void printStatus() {

        System.out.println("\nTime: " + clock);
        System.out.println("Completed Tasks: " + completedTasks);

        System.out.print("Pending: ");
        printQueue(futureTaskHeap);

        System.out.print("Queue A: ");
        printQueue(priorityLevels.get(0));

        System.out.print("Queue B: ");
        printQueue(priorityLevels.get(1));

        System.out.print("Queue C: ");
        printQueue(priorityLevels.get(2));
    }

    private void printQueue(Collection<Task> queue) {
        if (queue.isEmpty()) {
            System.out.println("empty");
            return;
        }
        for (Task t : queue) {
            System.out.print(t.taskId + "(" + t.remainingWork + ") ");
        }
        System.out.println();
    }
    public boolean isAllDone() {
        return futureTaskHeap.isEmpty() &&
                priorityLevels.get(0).isEmpty() &&
                priorityLevels.get(1).isEmpty() &&
                priorityLevels.get(2).isEmpty();
    }

}
