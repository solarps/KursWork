package Classes;

import lombok.Data;

@Data
public class CPU {
    private boolean isActive;
    Core[] cores;

    public CPU(final int coresNumber) {
        this.cores = new Core[coresNumber];
        for(int i = 0;i<coresNumber;i++){
            cores[i] =new Core();
        }
        isActive = true;
    }

   /* @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CPU{");
        for (Core core : cores) {
            sb.append(core.getState()).append(",");
        }
        sb.append('}');
        return sb.toString();
    }*/
}
