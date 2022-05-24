import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IntersectionCounter {

  public static IntersectionContent intersectSort(ArrayList<Line> sequence) {

    // Base Case
    if (sequence.size() < 2)
      return new IntersectionContent(0, sequence);

    // get middle index of ArrayList
    double length = (double) sequence.size();
    double middle = Math.ceil(length / 2);
    int middleInt = (int) middle;
    Integer middleIndex = (Integer) middleInt;

    // Split sequence into halves
    List<Line> frontList = sequence.subList(0, middleIndex);
    List<Line> backList = sequence.subList(middleIndex, sequence.size());
    ArrayList<Line> frontHalf = new ArrayList<Line>(frontList);
    ArrayList<Line> backHalf = new ArrayList<Line>(backList);

    // make recursive calls on each half
    IntersectionContent frontCount = intersectSort(frontHalf);
    IntersectionContent backCount = intersectSort(backHalf);

    // call intersectCount to find intersections between two sublists
    IntersectionContent crossoverCount = intersectCount(frontCount.content, backCount.content);

    // calculate and return the total num of intersections, and return merged List;
    long totalCount =
        frontCount.numIntersections + backCount.numIntersections + crossoverCount.numIntersections;
    return new IntersectionContent(totalCount, crossoverCount.content);

  }

  public static IntersectionContent intersectCount(ArrayList<Line> A, ArrayList<Line> B) {
    long count = 0;
    ArrayList<Line> mergedList = new ArrayList<Line>();
    while (!A.isEmpty() || !B.isEmpty()) {
      if (A.isEmpty()) {
        //append from B
        mergedList.add(B.get(0));
        B.remove(0);
      } else if (B.isEmpty()) {
        //append from A
        mergedList.add(A.get(0));
        A.remove(0);
      }
      else {
        if(B.get(0).q < A.get(0).q) {
          //if point q from B comes before point q from A
          //then Line from B intersects with all lines from A
          count += A.size();
          mergedList.add(B.get(0));
          B.remove(0);
        }
        else {
          //point q from A comes before point q from b
          //no intersection
          mergedList.add(A.get(0));
          A.remove(0);
        }
      }
    }
    return new IntersectionContent(count, mergedList);
  
  }
  
  public static ArrayList<Line> pSort(ArrayList<Line> sequence){
    if (sequence.size() < 2)
      return sequence;

    double length = (double) sequence.size();
    double middle = Math.ceil(length / 2);
    int middleInt = (int) middle;
    Integer middleIndex = (Integer) middleInt;

    List<Line> frontList = sequence.subList(0, middleIndex);
    List<Line> backList = sequence.subList(middleIndex, sequence.size());
    ArrayList<Line> frontHalf = new ArrayList<Line>(frontList);
    ArrayList<Line> backHalf = new ArrayList<Line>(backList);

    // make recursive calls on each half
    ArrayList<Line> sortedFront = pSort(frontHalf);
    ArrayList<Line> sortedBack = pSort(backHalf);
    
    ArrayList<Line> sortedPointList = pMerge(sortedFront, sortedBack);
    
    return sortedPointList;
  }
  
  public static ArrayList<Line> pMerge(ArrayList<Line> A, ArrayList<Line> B){
    ArrayList<Line> mergedList = new ArrayList<Line>(A.size() + B.size());
    while (!A.isEmpty() || !B.isEmpty()) {
      if (A.isEmpty()) {
        mergedList.add(B.get(0));
        B.remove(0);
      } else if (B.isEmpty()) {
        mergedList.add(A.get(0));
        A.remove(0);
      } else {
        long aFront = A.get(0).p;
        long bFront = B.get(0).p;
        if (bFront < aFront) {
          mergedList.add(B.get(0));
          B.remove(0);
        } else {
          mergedList.add(A.get(0));
          A.remove(0);
        }


      }

    }
    return mergedList;
  }

  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int numInstances = Integer.parseInt(reader.readLine());

    // loop through all Instances
    for (int i = 0; i < numInstances; i++) {
      String nextInstanceSize = reader.readLine();
      if (nextInstanceSize.equals(null) || nextInstanceSize.length() == 0) {
        break;
      }
      Integer currentInstanceSize = Integer.parseInt(nextInstanceSize);
      ArrayList<Long> firstPoints = new ArrayList<Long>(currentInstanceSize);
      ArrayList<Line> currentLines = new ArrayList<Line>(currentInstanceSize);
      for (int j = 0; j < currentInstanceSize; j++) {
        // q
        Integer currentPoint = Integer.parseInt(reader.readLine());
        long currentLong = currentPoint.longValue();
        firstPoints.add(currentLong);
      }
      for (int j = 0; j < currentInstanceSize; j++) {
        // p
        Integer currentPoint = Integer.parseInt(reader.readLine());
        long currentLong = currentPoint.longValue();
        Line currentLine = new Line(firstPoints.get(j), currentLong);
        currentLines.add(currentLine);
      }


      //sort input lines by ascending x value of p coordinates
      //meaning the line with the lowest value p coordinate should be at the start
      //of the list, and the highest at the end
      
      //so pSort is just mergeSort by p values of the lines
      ArrayList<Line> sortedPoints = pSort(currentLines);
      
      //now sort again on list sorted by p value, this time count intersections
      IntersectionContent numIntersections = intersectSort(sortedPoints);

      System.out.println(numIntersections.numIntersections);

    }
    
    reader.close();

    
  }

}
