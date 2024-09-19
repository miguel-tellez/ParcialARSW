package edu.eci.arsw.math;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
import java.util.LinkedList;
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static LinkedList<ThredpiDigits> threads = new LinkedList<>();

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param N Los X numeros de hilos usados.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) {

        Object lockObject = new Object();
        LinkedList<Integer> digits = new LinkedList<>();

        createThreads(start, count, N, lockObject);

        for (ThredpiDigits Thread : threads) {
            try {
                Thread.join();
                digits.addAll(Thread.getDigits());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        byte[] digitsArray = linkedlistthreadArraylist (count, digits);
        return digitsArray;
    }


    private static void createThreads(int start, int count, int N, Object lockObject) {
        int digitsPerThread = count / N;
        for (int i = 0; i < N; i++) {
            int startDigit = i * DIGITSPERSUM;
            int startInterval = i * digitsPerThread;
            int endInterval = (i == N-1) ? count : start + digitsPerThread;
            ThredpiDigits threadI = new ThredpiDigits(startDigit, startInterval, endInterval, i, lockObject);
            threads.add(threadI);
            threadI.start();
        }
    }

    public static byte[] linkedlistthreadArraylist(int count, LinkedList<Byte> digits) {
        byte[] bytes = new byte[count];
        int i = 0;
        for (Byte b : digits) {
            bytes[i++] = b;
        }
        return bytes;
    }

}
