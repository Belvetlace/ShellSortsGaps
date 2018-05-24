public class Foothill
{
   public static void main(String[] args)
   {
      final int ARRAY_SIZE = 10000;

      Integer[] arrayOfInts1 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts2 = new Integer[ARRAY_SIZE];

      int[] gapArray = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
              2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
              1048576};
      int[] sedgewickArray = new int[30]; // to be computed using formulas
      // ... other gap arrays ...

      // fill distinct arrays with identical random values so we can compare gaps


      shellSortX(arrayOfInts1, gapArray);  // time this


      shellSortX(arrayOfInts2, sedgewickArray);  // time this

   }

   private static void shellSortX(Integer[] arrayOfInts1, int[] gapArray)
   {

   }
}
