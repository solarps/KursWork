package kurs.kursgui;

import javafx.scene.control.TextArea;

public class PrintThread extends Thread {
    TextArea queue;
    TextArea completed;
    TextArea rejected;
    Scheduler scheduler;

    public PrintThread(TextArea queueArea, TextArea completedArea, TextArea rejectedArea, Scheduler scheduler) {
        this.queue = queueArea;
        this.completed = completedArea;
        this.rejected = rejectedArea;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        while (scheduler.getCpu().isActive()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.setText(scheduler.getQueueCPU().printProcesses() + '\n' + scheduler.getMemoryScheduler().print());
            completed.setText(scheduler.getQueueCPU().printCompleted());
            rejected.setText(scheduler.getQueueCPU().printRejected());
        }
    }
}
