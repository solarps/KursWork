package Classes;


import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class Core extends Thread {
    private Process process;
    private QueueCPU queueCPU;
    private boolean isFree = true;

    public Core() {
    }

    public Core(QueueCPU queueCPU, Process process) {
        this.queueCPU = queueCPU;
        this.process = process;
    }

    public boolean isFree() {
        return isFree;
    }

    @SneakyThrows
    @Override
    public void run() {
        isFree = false;
        Timer timer = new Timer();
        int k = 0;
        do {
            timer.incWorkTime(Configurations.Tact);
            k++;
            Thread.sleep(1000);
            process.setBurstTime(timer.getWorkTime());
        } while (k < process.getInterval());
        process.setState(Status.Finished);
        queueCPU.complete(process);
        isFree = true;
        queueCPU.addRejected();
    }
}