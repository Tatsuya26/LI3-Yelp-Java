import static java.lang.System.nanoTime;
public class Crono {

    private static long begin = 0L;
    private static long end = 0L;

    public static void start() {
        end = 0L;
        begin = nanoTime();
    }

    public static double stop() {
        end = nanoTime();
        long elapsedTime = end - begin;
        return elapsedTime / 1.0E09;
    }

    public static String getTimeAsString() {
        return "" + stop();
    }

    public static void printElapsedTime() {
        System.out.println( "Elapsed Time: " + stop() + " s");
    }
}