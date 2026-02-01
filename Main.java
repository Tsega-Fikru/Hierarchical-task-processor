import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scheduler scheduler = new Scheduler();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Hierarchical Task Processor!");
        System.out.println("Enter tasks one by one in this format:");
        System.out.println("<TaskID> <TotalWork> <ArrivalTime>");
        System.out.println("Example: T1 10 0");
        System.out.println("Enter '0' when you are done adding tasks.");

        boolean addingTasks = true;

        while (true) {

            // 1️⃣ Task input phase
            if (addingTasks) {
                while (true) {
                    System.out.print("Enter task: ");
                    String line = sc.nextLine().trim();

                    if (line.equals("0")) break;

                    String[] parts = line.split("\\s+");
                    if (parts.length != 3) {
                        System.out.println("Invalid format! Example: T1 10 0");
                        continue;
                    }

                    String id = parts[0];
                    int work = Integer.parseInt(parts[1]);
                    int arrival = Integer.parseInt(parts[2]);
                    scheduler.scheduleTask(id, work, arrival);
                    System.out.println("Task " + id + " scheduled!");
                }

                addingTasks = false; // finished initial batch
            }

            // 2️⃣ Auto-processing phase
            while (!scheduler.isAllDone()) {
                scheduler.advanceClock();
                scheduler.printStatus();
            }

            // 3️⃣ Prompt user to add more tasks
            System.out.println("\nAll tasks completed!");
            System.out.println("Do you want to add more tasks? (yes/no)");
            String answer = sc.nextLine().trim().toLowerCase();

            if (answer.equals("yes")) {
                System.out.println("Enter tasks in format <TaskID> <TotalWork> <ArrivalTime>");
                System.out.println("Enter '0' when you are done adding tasks.");
                addingTasks = true; // back to task input phase
            } else {
                System.out.println("No more tasks. Program finished.");
                break;
            }
        }

        sc.close();
    }
}
