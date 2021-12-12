package Classes;

import lombok.Getter;

import java.util.Comparator;

@Getter
public class MemoryBlock {
    private int start;
    private int end;
    private final int size;
    private int id;

    public static Comparator<MemoryBlock> byEnd = Comparator.comparingInt(o -> o.end);

    public MemoryBlock(int id ,int size) {
        this.size = size;
        this.id = id;
    }

    public void setParameters(int start) {
        this.start = start;
        this.end = start + this.size;
    }

    @Override
    public String toString() {
        return "{" + start + "," + end + '}';
    }
}
