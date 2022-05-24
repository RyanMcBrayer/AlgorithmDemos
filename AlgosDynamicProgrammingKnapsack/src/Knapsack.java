import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;

public class Knapsack {


  public static int findMaxValue(KnapsackInstance instance) {
    int maxCapacity = instance.capacity;
    int numItems = instance.numItems;
    int[][] bellmanMatrix = new int[numItems + 1][maxCapacity + 1];

    for (int i = 1; i <= numItems; i++) {
      for (int j = 1; j <= maxCapacity; j++) {
        KnapsackItem potentialItem = instance.items.get(i - 1);
        int potentialItemWeight = potentialItem.weight;
        
        //use value for previous item if not included
        int notIncludedValue = bellmanMatrix[i - 1][j];

        int includedValue = 0;
        
        //if statement represents indicator from bellman
        if (potentialItemWeight <= j) {
          includedValue = bellmanMatrix[i - 1][j - potentialItemWeight] + potentialItem.value;
        }
        
        //each cell is max of included/notIncluded
        bellmanMatrix[i][j] = Math.max(notIncludedValue, includedValue);
      }
    }
    return bellmanMatrix[numItems][maxCapacity];
  }

  public static void main(String[] args) throws IOException {

     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
     
     
    //this commented out section reads the default test from a local file
     
    //File sampleIn = new File(
    //    "C:\\Users\\Ryan\\Desktop\\EclipseWorkspace\\AlgosDynamicProgrammingKnapsack\\tests.txt");
    //BufferedReader reader = new BufferedReader(new FileReader(sampleIn));

    int numInstances = Integer.parseInt(reader.readLine());

    for (int i = 0; i < numInstances; i++) {
      String nextInstanceSize = reader.readLine();
      if (nextInstanceSize.equals(null) || nextInstanceSize.length() == 0) {
        break;
      }
      String[] instanceContents = nextInstanceSize.split("\\s+");
      int currentInstanceSize = Integer.parseInt(instanceContents[0]);
      int currentInstanceCapacity = Integer.parseInt(instanceContents[1]);
      ArrayList<KnapsackItem> currentIntervals = new ArrayList<>(currentInstanceSize);
      for (int j = 0; j < currentInstanceSize; j++) {
        String currentLine = reader.readLine();
        String[] lineContents = currentLine.split("\\s+");
        int weight = Integer.parseInt(lineContents[0]);
        int value = Integer.parseInt(lineContents[1]);
        currentIntervals.add(new KnapsackItem(weight, value));

      }
      KnapsackInstance currentSequence =
          new KnapsackInstance(currentInstanceSize, currentInstanceCapacity, currentIntervals);
      
      //call DP helper method
      int instanceValue = findMaxValue(currentSequence);
      
      //print result
      System.out.println(instanceValue);
    }



    reader.close();
  }

}
