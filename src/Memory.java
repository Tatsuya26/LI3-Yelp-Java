
public class Memory{

    private static long begin = 0L;
    private static long end = 0L;
    private static double total = 0L;

    public static void start() {
        begin = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        end = 0L;
    }

    public static void stop() {
        end = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        total = (end - begin) / 1048576.0;
    }
    public static void printMemory() {
        stop();
        System.out.println( "Diferença de memória alocada durante o processo (MB): " + total);
    }
}