package kurs.kursgui;

import Classes.Process;
import Classes.*;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.Comparator;


@EqualsAndHashCode(callSuper = true)
@Data
public class Scheduler extends Thread {
    private CPU cpu;
    private QueueCPU queueCPU;
    private MemoryScheduler memoryScheduler;

    public Scheduler(final int coresNumber, int maxMemoryVolume) {
        this.cpu = new CPU(coresNumber);
        this.memoryScheduler = new MemoryScheduler();
        this.queueCPU = new QueueCPU(memoryScheduler);
        MemoryScheduler.maxMemory = maxMemoryVolume;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (getCpu().isActive()) {
            Core[] cores = cpu.getCores();
            queueCPU.getProcesses().sort(Comparator.comparingInt(Process::getPriority));
            do {
                for (int i = 0; i < queueCPU.getProcesses().size(); i++) {
                    for (int j = 0; j < cores.length; j++) {
                        synchronized (this) {
                            if (!queueCPU.getProcesses().isEmpty() && cores[j].isFree()
                                    && queueCPU.getProcesses().get(i).getState() == Status.Ready) {
                                queueCPU.getProcesses().get(i).setState(Status.Running);
                                cores[j] = new Core(queueCPU, queueCPU.getProcesses().get(i));
                                cores[j].start();
                                queueCPU.addRejected();
                                break;
                            }
                        }
                        queueCPU.addRejected();
                    }
                    queueCPU.addRejected();
                }
                queueCPU.addRejected();
            } while (!queueCPU.getProcesses().isEmpty() && !queueCPU.getRejected().isEmpty());
            queueCPU.addRejected();
        }
    }
}
