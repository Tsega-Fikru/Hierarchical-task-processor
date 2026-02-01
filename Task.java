public class Task {

    String taskId;
    int remainingWork;
    int arrivalTime;

    int timeUsedInLevel;
    int lastExecutionTime;

    Task(String taskId, int totalWork, int arrivalTime) {
        this.taskId = taskId;
        this.remainingWork = totalWork;
        this.arrivalTime = arrivalTime;
        this.timeUsedInLevel = 0;
        this.lastExecutionTime = -1;
    }
}
