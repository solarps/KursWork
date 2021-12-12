package Classes;

import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class QueueCPU {
    private final List<Process> processes = new CopyOnWriteArrayList<>();
    private final List<Process> rejected = new CopyOnWriteArrayList<>();
    public final List<Process> completed = new CopyOnWriteArrayList<>();
    private MemoryScheduler memoryScheduler;
    private int lastId = 1;

    public QueueCPU(MemoryScheduler memoryScheduler) {
        this.memoryScheduler = memoryScheduler;
    }

    public void clear() {
        this.processes.clear();
        this.rejected.clear();
    }

    public void add() {
        Process process = new Process(lastId++);
        if (!memoryScheduler.fill(process.getMemoryBlock())) {
            this.rejected.add(process);
        } else processes.add(process);
    }

    public void add(int N) {
        for (int i = 0; i < N; i++) {
            add();
        }
    }

    public synchronized void addRejected() {
        rejected.sort(Comparator.comparingInt(Process::getPriority));
        for (Process process : rejected) {
            MemoryBlock needMemory = new MemoryBlock(process.getId(), process.getMemory());
            if (memoryScheduler.fill(needMemory)) {
                processes.add(process);
                rejected.remove(process);
                break;
            }
        }
    }

    public synchronized void complete(Process process) {
        memoryScheduler.release(process.getMemoryBlock().getId());
        completed.add(process);
        processes.remove(process);
    }

    public String printProcesses() {
        StringBuilder result = new StringBuilder();
        for (Process process : processes) {
            result.append(process).append("\n");
        }
        return result.toString();
    }

    public String printCompleted() {
        StringBuilder result = new StringBuilder();
        for (Process process : completed) {
            result.append(process).append("\n");
        }
        return result.toString();
    }

    public String printRejected() {
        StringBuilder result = new StringBuilder();
        for (Process process : rejected) {
            result.append(process).append("\n");
        }
        return result.toString();
    }

}
