package Classes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static Classes.Configurations.OSMemory;

public class MemoryScheduler {
    public static int maxMemory = 4096;

    private final List<MemoryBlock> memoryBlocks = new CopyOnWriteArrayList<>();

    public synchronized boolean findFreeBlock(MemoryBlock memoryBlock) {
        if (memoryBlocks.isEmpty()) {
            memoryBlock.setParameters(OSMemory);
            return true;
        }
        memoryBlocks.sort(MemoryBlock.byEnd);
        for (int i = 0; i < memoryBlocks.size(); i++) {
            if (i == memoryBlocks.size() - 1) {
                if (maxMemory - memoryBlocks.get(i).getEnd() >= memoryBlock.getSize()) {
                    memoryBlock.setParameters(memoryBlocks.get(i).getEnd());
                    return true;
                } else return false;
            }
            if (memoryBlocks.get(i + 1).getStart() - memoryBlocks.get(i).getEnd() >= memoryBlock.getSize()) {
                memoryBlock.setParameters(memoryBlocks.get(i).getEnd());
                return true;
            }
        }
        return false;
    }

    public synchronized boolean fill(MemoryBlock memoryBlock) {
        if (findFreeBlock(memoryBlock)) {
            memoryBlocks.add(memoryBlock);
            return true;
        }
        return false;
    }

    public synchronized void release(int id) {
        memoryBlocks.removeIf(memoryBlock -> memoryBlock.getId() == id);
    }

    public String print() {
        StringBuilder result = new StringBuilder();
        for (MemoryBlock memoryBlock : memoryBlocks) {
            result.append(memoryBlock);
        }
        return result.toString();
    }

    public void clear() {
        memoryBlocks.clear();
    }
}
