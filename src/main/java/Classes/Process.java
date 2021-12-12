package Classes;

import lombok.Data;

import java.util.Random;

import static Classes.Configurations.maxInterval;
import static Classes.Configurations.maxPriority;
import static Classes.MemoryScheduler.maxMemory;

@Data
public class Process {
    private String name;
    private int id;
    private int priority;
    private int interval;
    private int memory;
    private int timeAdmission;
    private int burstTime;
    private Status state;
    private MemoryBlock memoryBlock;

    Random random = new Random();

    public Process(int id) {
        this.id = id;
        this.memory = random.nextInt(maxMemory / 2);
        this.priority = random.nextInt(maxPriority);
        this.interval = random.nextInt(maxInterval);
        this.burstTime = 0;
        this.timeAdmission = Timer.time;
        this.name = "P" + id;
        this.state = Status.Ready;
        this.memoryBlock = new MemoryBlock(id,this.memory);
    }

    @Override
    public String toString() {
        return "{" + "name='" + name + '\'' +
                ", id=" + id +
                ", priority=" + priority +
                ", interval=" + interval +
                ", memory=" + memory +
                ", timeAdmission=" + timeAdmission +
                ", burstTime=" + burstTime +
                ", state=" + state +
                '}';
    }
}
