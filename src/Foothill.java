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
      //arraySize = a.length;
      for ( arraySize = a.length;  maxGapPos >= 0;  maxGapPos--)
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
      final int[] ARRAY_SIZE = {10000, 50000, 75000, 100000, 150000, 200000};

      int[] gapArray = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
              2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
              1048576};

      int[] sedgewickArray = new int[26];
      System.out.println("Sedgwick gap sequence");
      // Sedgewick gap sequence
      // make an array or two of precomputed values:
      // f(n) = 9*2^n - 9*2^(n/2) + 1 if n is even;
      // f(n) = 8*2^n - 6*2^((n+1)/2) + 1 if n is odd.
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

      // Sedgewick gap sequence formulas from textbook
      // 9 · 4^i − 9 · 2^i + 1      1 5 19 41 109 209 505 929 2161 3905 ..
      // 4^i − 3 · 2^i + 1
      int[] sedgewickArray2 = new int[26];
      System.out.println("Sedgwick gap sequence 2");
      for(int n = 0, i = 0; n < sedgewickArray2.length; n += 2, i++)
      {
         sedgewickArray2[n] =
                 9 * ((int)Math.pow(4, i) - (int)Math.pow(2, i)) + 1;
         sedgewickArray2[n + 1] =
                 (int)Math.pow(4, i + 2) - 3 * (int)Math.pow(2, i + 2) + 1;
      }
      for (int item : sedgewickArray2)
         System.out.print(item + ", ");

      System.out.println();

      // Sedgewick gap sequence formulas from textbook no math.pow calls
      int[] sedgewickArray3 = new int[27];
      System.out.println("Sedgwick gap sequence 3(no math.pow calls)");
      int pow4 = 1, pow2 = 1;
      sedgewickArray3[0] = 1;
      for(int n = 1; n < sedgewickArray3.length-1; n += 2, pow2 *= 2, pow4 *= 4)
      {
         sedgewickArray3[n + 1] = 9 * (4 * pow4 - 2 * pow2) + 1;
         sedgewickArray3[n] = 4 * (4 * pow4) - 2 * 3 * (2 * pow2) + 1;
      }
      for (long item : sedgewickArray3)
         System.out.print(item + ", ");
      System.out.println();

      // Hibbard gap sequence 2^n - 1
      System.out.println("Hibbard gap sequence");
      int[] hibbardArray = new int[26];
      for(int n = 1; n < hibbardArray.length+1; n++)
      {
         hibbardArray[n-1] = (int)Math.pow(2, n) - 1;
         System.out.print(hibbardArray[n-1] + ", ");
      }
      System.out.println();

      for (int item : ARRAY_SIZE)
      {
         System.out.println("\nArray size: " + item);
         sequencesRun(item, gapArray, sedgewickArray2, hibbardArray);
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

      /* un-comment  to verify sort ----------
      for (int k = 0; k < ARRAY_SIZE; k+= ARRAY_SIZE/10)
      {
         System.out.println(String.format("insertion # %6d: %7d ", k , arrayOfInts1[k]));
         System.out.println(String.format("gapArray  # %6d: %7d ", k, arrayOfInts2[k]));
         System.out.println(String.format("sedgewick # %6d: %7d ", k, arrayOfInts3[k]));
         System.out.println(String.format("hibbard   # %6d: %7d \n", k, arrayOfInts4[k]));
      }
      -------------------------------------- */
   }
}

/*
Answer:
Shell's gap sequence implied by shellSort1() is faster than the explicit array
described above and passed to shellSortX() because
"all but the last gap is even, this means that the even positions
never mix with the odd positions in the sorting until the final gap = 1 loop.
Therefore, we have a plan to make a sequence which stays very unsorted until
the final pass of outer loop, when the gap is 1." (M. Loceff)
On the other hand, implied sort may have mixed even and odd gaps
depending on the array size.

Run----------------------------------------------
Sedgwick gap sequence
1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609, 587521, 1045505, 2354689, 4188161, 9427969, 16764929, 37730305, 67084289, 150958081, 268386305,
Sedgwick gap sequence 2
1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609, 587521, 1045505, 2354689, 4188161, 9427969, 16764929, 37730305, 67084289, 150958081, 268386305,
Sedgwick gap sequence 3(no math.pow calls)
1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609, 587521, 1045505, 2354689, 4188161, 9427969, 16764929, 37730305, 67084289, 150958081, 268386305, 603906049,
Hibbard gap sequence
1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863,

Array size: 10000
Elapsed Time:   0.0142 seconds. Shell's implied gap sequence
Elapsed Time:   0.0383 seconds. Shell's explicit gap sequence
Elapsed Time:    0.026 seconds. Sedgewick's gap sequence
Elapsed Time:   0.0039 seconds. Hibbard's gap sequence

Array size: 50000
Elapsed Time:   0.0729 seconds. Shell's implied gap sequence
Elapsed Time:   0.1405 seconds. Shell's explicit gap sequence
Elapsed Time:   0.0971 seconds. Sedgewick's gap sequence
Elapsed Time:   0.0787 seconds. Hibbard's gap sequence

Array size: 75000
Elapsed Time:   0.1019 seconds. Shell's implied gap sequence
Elapsed Time:   0.1704 seconds. Shell's explicit gap sequence
Elapsed Time:   0.0547 seconds. Sedgewick's gap sequence
Elapsed Time:    0.073 seconds. Hibbard's gap sequence

Array size: 100000
Elapsed Time:    0.233 seconds. Shell's implied gap sequence
Elapsed Time:   0.3927 seconds. Shell's explicit gap sequence
Elapsed Time:     0.11 seconds. Sedgewick's gap sequence
Elapsed Time:   0.1308 seconds. Hibbard's gap sequence

Array size: 150000
Elapsed Time:   0.1782 seconds. Shell's implied gap sequence
Elapsed Time:   0.4671 seconds. Shell's explicit gap sequence
Elapsed Time:   0.1379 seconds. Sedgewick's gap sequence
Elapsed Time:   0.1696 seconds. Hibbard's gap sequence

Array size: 200000
Elapsed Time:   0.2553 seconds. Shell's implied gap sequence
Elapsed Time:    0.868 seconds. Shell's explicit gap sequence
Elapsed Time:    0.188 seconds. Sedgewick's gap sequence
Elapsed Time:     0.24 seconds. Hibbard's gap sequence

 */