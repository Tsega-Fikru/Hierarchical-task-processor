# Hierarchical Task Processor

## Course: Data Structures & Algorithms

## Problem Selected

**Problem 02: Hierarchical Task Processor**

This project simulates a task processing system that handles tasks with different priorities using three queues and a pending area for future tasks. The processor runs automatically and follows all rules for execution, demotion, aging, and preemption.

---

## Author

**Individual Project** – completed and implemented by TSEGA FIKRU.

---

## How it Works

* **Queues:**
  * Queue A → High priority
  * Queue B → Medium priority
  * Queue C → Low priority

* **Rules:**
  * Always run tasks from the highest available queue
  * Queue A tasks → max 2 units → move to Queue B if unfinished
  * Queue B tasks → max 4 units → move to Queue C if unfinished
  * Queue C tasks → run until completion (or preempted if a higher-priority task arrives)
  * Tasks in Queue C waiting ≥15 units → promoted to Queue A
  * Tasks with future arrival times are stored in a min-heap and moved to Queue A when time comes

---

## How to Use (CLI)

1. Enter tasks in this format:

```
<TaskID> <TotalWork> <ArrivalTime>
```

Example:

```
T1 10 0
T2 5 2
```

2. When done entering tasks, type:

```
0
```

3. Processor runs automatically and prints status after each time unit.

4. After all tasks finish, you are asked if you want to add more tasks:
   * Type `yes` → add more tasks using the same format
   * Type `no` → program exits

---

## How to Run

1. Make sure Java is installed
2. Compile:

```
javac Main.java
```

3. Run:

```
java Main
```

---
