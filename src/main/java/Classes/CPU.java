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
}
