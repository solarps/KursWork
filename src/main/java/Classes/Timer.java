package Classes;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Timer extends Thread{
    public static int time = 0;
    private CPU cpu;
    private int workTime = 0;

    public Timer(CPU cpu){
        this.cpu = cpu;
    }

    @SneakyThrows
    @Override
    public void run(){
        while (cpu.isActive()){
            Thread.sleep(1000);
            incTime();
        }
    }

    public void incWorkTime(int tact) {
        workTime += tact;
    }

    public static void incTime(int tact) {
        time += tact;
    }

    public static void incTime() {
        time++;
    }
}
