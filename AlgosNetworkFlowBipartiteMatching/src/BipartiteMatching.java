import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
public class BipartiteMatching {
  
  public static int ffHelper(int[][] residualGraph, int numNodes){

    int currPath[] = new int[numNodes];
    
    int totalFlow = 0;

    while (bfs(residualGraph, currPath, numNodes)) {
      
      int currNode = 0;
      int currPathFlow = 2147483647;
      
      int i = numNodes - 1;
      while(!(i == 0)) {
        //get current node along path
        currNode = currPath[i];
        
        //check residual graph and update flow
        if(!(currPathFlow < residualGraph[currNode][i])) {
          currPathFlow = residualGraph[currNode][i];
  
        }
        //update residual graph
        residualGraph[i][currNode] += currPathFlow;
        residualGraph[currNode][i] -= currPathFlow;
        
        //go to next node in Graph
        i = currPath[i];
      }
      

      totalFlow += currPathFlow;
    }

    return totalFlow;
    
  }
  
  public static boolean bfs(int residualGraph[][], int currPath[], int numNodes) {
    
    boolean visitedArr[] = new boolean[numNodes];
    
    //initialize visited array to false
    for (int i = 0; i < numNodes; i++) {
      visitedArr[i] = false;
    }
    
      //set source to -1
      currPath[0] = -1;
    
      PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
      
      queue.add(0);
      
      //visit source
      visitedArr[0] = true;
      
      
      //loop while paths remain
      while (queue.size() != 0) {
        int headNodeIndex = queue.poll();
        //check every node
        for (int i = 0; i < numNodes; i++) {
          //if edge exists from head node and node not yet visited
          if ((residualGraph[headNodeIndex][i] > 0) && !visitedArr[i]) {
            
            //extendPath to head node
            currPath[i] = headNodeIndex;
            
            //if at sink return true
            if (i == numNodes-1) {
              return true;
            }
            //otherwise continue path
            visitedArr[i] = true;
            queue.add(i);
          }
        }
      }
      
      return false;
    }

  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    int numInstances = Integer.parseInt(reader.readLine());
    
    for (int i = 0; i < numInstances; i++) {
      String nextInstanceSize = reader.readLine();
      if (nextInstanceSize.equals(null) || nextInstanceSize.length() == 0) {
        break;
      }
      String[] instanceContents = nextInstanceSize.split("\\s+");
      int numNodesA = Integer.parseInt(instanceContents[0]);
      int numNodesB = Integer.parseInt(instanceContents[1]);
      int numEdges = Integer.parseInt(instanceContents[2]);
      
      int graphDimension = numNodesA + numNodesB + 2;
      
      int[][] flowGraph = new int[graphDimension][graphDimension];
      
      //connect source to A
      for(int j = 0; j<numNodesA;j++) {
        flowGraph[0][j+1] = 1;
      }
      
      
      //fill edges between A and B
      for(int j = 0; j < numEdges;j++) {
        String currentEdge = reader.readLine();
        String[] edgeContents = currentEdge.split("\\s+");
        int sourceA = Integer.parseInt(edgeContents[0]);
        int targetB = Integer.parseInt(edgeContents[1]);
        flowGraph[sourceA][targetB + numNodesA] = 1;
      }
      
      //connect B to sink
      for(int j=0;j <numNodesB;j++) {
        flowGraph[numNodesA + 1 + j][graphDimension - 1] = 1;
      }
      
      //call ffHelper to find max flow of graph
      int maxFlow = ffHelper(flowGraph, graphDimension);
      
      
      
      String outputChar;
      
      //check if matching is perfect
      if((maxFlow == Math.max(numNodesA, numNodesB))  && (numNodesA == numNodesB)){
        outputChar = "Y";
      }
      else {
        outputChar = "N";
      }
      
      System.out.println(maxFlow + " " + outputChar);

    }

    reader.close();

  }

}