import cs_1c.FHsort;

import java.text.NumberFormat;
import java.util.Locale;

public class Foothill
{
   private static < E extends Comparable< ? super E > >
   void shellSortX(E[] a, int[] gapArray)
   {
      int gap, maxGapPos;
      int k, pos, arraySize;
      E tmp;
      maxGapPos = gapArray.length-1;
      while(gapArray[maxGapPos] >= a.length)
      {
         maxGapPos--;
         //System.out.println("maxGapPos " + maxGapPos);
      }
      arraySize = a.length;
      for ( ;  maxGapPos > 0;  maxGapPos--)
      {
         gap = gapArray[maxGapPos];
         //System.out.println("maxGapPos " + maxGapPos + ", gap " + gap);
         for (pos = gap; pos < arraySize; pos++)
         {
            tmp = a[pos];
            for (k = pos; k >= gap && tmp.compareTo(a[k - gap]) < 0; k -= gap)
               a[k] = a[k - gap];
            a[k] = tmp;
         }
      }
   }

   public static void main(String[] args)
   {
      final int[] ARRAY_SIZE = {10000, 50000, 65536, 100000, 131072, 150000, 200000};
      //2^14=16384 2^15=32768 2^16=65536  2^17=131072  2^18=262144 2^19=524288

      int[] gapArray = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
              2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
              1048576};
      int[] sedgewickArray = new int[20];
      System.out.println("Sedgwick gap sequence");
      for(int n = 0; n < sedgewickArray.length; n++)
      {
         if( n % 2 == 0 )
            sedgewickArray[n] =
                    9*(int)Math.pow(2, n) - 9*(int)Math.pow(2, n/2) + 1;
         else
            sedgewickArray[n] =
                    8*(int)Math.pow(2, n) - 6*(int)Math.pow(2, (n + 1)/2) + 1;
         System.out.print(sedgewickArray[n] + ", ");
      }
      System.out.println();

      // Hibbard gap sequence 2^n - 1
      System.out.println("Hibbard gap sequence");
      int[] hibbardArray = new int[20];
      for(int n = 1; n < hibbardArray.length; n++)
      {
         hibbardArray[n] = (int)Math.pow(2, n) - 1;
         System.out.print(hibbardArray[n] + ", ");
      }
      System.out.println();

      for (int aARRAY_SIZE : ARRAY_SIZE)
      {
         System.out.println("\nArray size: " + aARRAY_SIZE);
         sequencesRun(aARRAY_SIZE, gapArray, sedgewickArray, hibbardArray);
      }
   }

   private static void sequencesRun(int ARRAY_SIZE, int[] gapArray,
                                    int[] sedgewickArray, int[] hibbardArray)
   {
      int randomInt;
      long startTime, stopTime;
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      Integer[] arrayOfInts1 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts2 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts3 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts4 = new Integer[ARRAY_SIZE];

      for (int k = 0; k < ARRAY_SIZE; k++)
      {
         randomInt = (int) (Math.random() * ARRAY_SIZE);
         arrayOfInts1[k] = randomInt;
         arrayOfInts2[k] = randomInt;
         arrayOfInts3[k] = randomInt;
         arrayOfInts4[k] = randomInt;
      }

      startTime = System.nanoTime();  // ------------------ start
      FHsort.shellSort1(arrayOfInts1);
      stopTime = System.nanoTime();    // ---------------------- stop
      System.out.println(String.format(
              "Elapsed Time: %8s seconds. Shell's implied gap sequence ",
              tidy.format( (stopTime - startTime) / 1e9)));

      startTime = System.nanoTime();  // ---------------------- start
      shellSortX(arrayOfInts2, gapArray);  // time this
      stopTime = System.nanoTime();   // ---------------------- stop
      System.out.println(String.format(
              "Elapsed Time: %8s seconds. Shell's explicit gap sequence ",
              tidy.format( (stopTime - startTime) / 1e9)));

      startTime = System.nanoTime();  // ---------------------- start
      shellSortX(arrayOfInts3, sedgewickArray);  // time this
      stopTime = System.nanoTime();   // ---------------------- stop
      System.out.println(String.format(
              "Elapsed Time: %8s seconds. Sedgewick's gap sequence ",
              tidy.format( (stopTime - startTime) / 1e9)));


      startTime = System.nanoTime();  // ---------------------- start
      shellSortX(arrayOfInts4, hibbardArray);  // time this
      stopTime = System.nanoTime();   // ---------------------- stop
      System.out.println(String.format(
              "Elapsed Time: %8s seconds. Hibbard's gap sequence ",
              tidy.format( (stopTime - startTime) / 1e9)));

   }
}

/*
Answer these questions at the end of your file:
Why does Shell's gap sequence implied by shellSort1() give a different timing
result than the explicit array described above and passed to shellSortX()?
Which is faster and why?

Run----------------------------------------------
Sedgwick gap sequence
1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609, 587521, 1045505, 2354689, 4188161,
Hibbard gap sequence
1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287,

Array size: 10000
Elapsed Time:   0.0152 seconds. Shell's implied gap sequence
Elapsed Time:   0.0264 seconds. Shell's explicit gap sequence
Elapsed Time:   0.0232 seconds. Sedgewick's gap sequence
Elapsed Time:   0.0217 seconds. Hibbard's gap sequence

Array size: 50000
Elapsed Time:   0.0924 seconds. Shell's implied gap sequence
Elapsed Time:   0.1206 seconds. Shell's explicit gap sequence
Elapsed Time:     0.08 seconds. Sedgewick's gap sequence
Elapsed Time:   0.0816 seconds. Hibbard's gap sequence

Array size: 65536
Elapsed Time:   0.1414 seconds. Shell's implied gap sequence
Elapsed Time:   0.0949 seconds. Shell's explicit gap sequence
Elapsed Time:   0.0364 seconds. Sedgewick's gap sequence
Elapsed Time:   0.0594 seconds. Hibbard's gap sequence

Array size: 100000
Elapsed Time:   0.1894 seconds. Shell's implied gap sequence
Elapsed Time:   0.2976 seconds. Shell's explicit gap sequence
Elapsed Time:   0.0903 seconds. Sedgewick's gap sequence
Elapsed Time:   0.1057 seconds. Hibbard's gap sequence

Array size: 131072
Elapsed Time:   0.4243 seconds. Shell's implied gap sequence
Elapsed Time:    0.296 seconds. Shell's explicit gap sequence
Elapsed Time:   0.1101 seconds. Sedgewick's gap sequence
Elapsed Time:   0.1512 seconds. Hibbard's gap sequence

Array size: 150000
Elapsed Time:   0.1867 seconds. Shell's implied gap sequence
Elapsed Time:   0.3995 seconds. Shell's explicit gap sequence
Elapsed Time:   0.1304 seconds. Sedgewick's gap sequence
Elapsed Time:   0.1749 seconds. Hibbard's gap sequence

Array size: 200000
Elapsed Time:   0.2293 seconds. Shell's implied gap sequence
Elapsed Time:   0.5535 seconds. Shell's explicit gap sequence
Elapsed Time:   0.1458 seconds. Sedgewick's gap sequence
Elapsed Time:   0.2167 seconds. Hibbard's gap sequence

 */