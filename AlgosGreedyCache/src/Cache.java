import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Hashtable;

public class Cache {

  public static Boolean checkHit(Integer[] cache, Integer request) {
    for (int i = 0; i < cache.length; i++) {
      if (cache[i] == request)
        return true;
    }
    return false;
  }

  public static int checkOpen(Integer[] cache, Integer request) {
    Boolean hit = false;
    Boolean opening = false;
    int openingIndex = -1;
    for (int i = cache.length - 1; i >= 0; i--) {
      if (cache[i] == null) {
        opening = true;
        openingIndex = i;
      } else if (cache[i] == request) {
        hit = true;
      }
    }
    if(hit) return -2;
    if(opening) return openingIndex;
    return -1;
  }

  public static void main(String[] args) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //File sampleIn =
    //    new File("C:\\Users\\Ryan\\Desktop\\EclipseWorkspace\\AlgosGreedyCache\\samplecache.txt");
    //BufferedReader reader = new BufferedReader(new FileReader(sampleIn));
    int numInstances = Integer.parseInt(reader.readLine());
    String currentLine = reader.readLine();
    int cacheSize = Integer.parseInt(currentLine);
    int numPages = Integer.parseInt(reader.readLine());

    LinkedList<CacheContents> allInstances = new LinkedList<CacheContents>();


    for (int i = 0; i < numInstances; i++) {
      ArrayList<Integer> currentInstance = new ArrayList<Integer>(numPages);
      currentLine = reader.readLine();
      if (currentLine == null || currentLine.length() == 0) {
        break;
      }
      String[] lineContents = currentLine.split("\\s+");
      for (int j = 0; j < numPages; j++) {
        currentInstance.add(Integer.parseInt(lineContents[j]));
      }
      allInstances.push(new CacheContents(cacheSize, currentInstance));
      String nextLine = reader.readLine();
      if (nextLine == null || nextLine.length() == 0) {
        break;
      }
      String nextLine2 = reader.readLine();
      if (nextLine2 == null || nextLine2.length() == 0) {
        break;
      }
      cacheSize = Integer.parseInt(nextLine);
      numPages = Integer.parseInt(nextLine2);
    }
    reader.close();

    for (int i = numInstances - 1; i >= 0; i--) {
      CacheContents currentContent = allInstances.get(i);
      Integer[] cache = new Integer[currentContent.size];
      Hashtable<Integer, PriorityQueue<Integer>> indexMappings =
          new Hashtable<Integer, PriorityQueue<Integer>>(currentContent.pages.size());
      int evictionCounter = 0;
      // loop through requests and create hashtable to map elements to MaxPriorityQueue of index
      // mappings
      for (int j = currentContent.pages.size() - 1; j >= 0; j--) {
        Integer currentElement = currentContent.pages.get(j);
        if (indexMappings.containsKey(currentElement)) {
          PriorityQueue<Integer> currentIndexList = indexMappings.get(currentElement);
          currentIndexList.add(j);
        } else {
          PriorityQueue<Integer> newIndexList = new PriorityQueue<Integer>();
          newIndexList.add(j);
          indexMappings.put(currentElement, newIndexList);
        }
      }

      int j = 0;
      // loop through rest of requests
      while (!currentContent.pages.isEmpty()) {
        Integer currentNum = currentContent.pages.get(0);
        int isOpen = checkOpen(cache, currentNum);
        if (isOpen > -1) {
          cache[isOpen] = currentNum;
          indexMappings.get(currentNum).remove((Integer) j);
          evictionCounter++;
        } 
        else if(isOpen == -2) {
          indexMappings.get(currentNum).remove((Integer) j);
        }
        else {
          Boolean cacheHit = checkHit(cache, currentNum);
          //indexMappings.get(currentNum).remove((Integer) j);
          if (!cacheHit) {
            int curMaxElementValue = 0;
            int indexOfVictim = 0;
            // loop through cache to find victim
            for (int k = 0; k < cache.length; k++) {
              PriorityQueue<Integer> currentIndexList = indexMappings.get(cache[k]);
              if (currentIndexList.size() < 1) {
                indexOfVictim = k;
                break;
              } else {
                Integer testElement = currentIndexList.peek();
                if (testElement > curMaxElementValue) {
                  curMaxElementValue = testElement;
                  indexOfVictim = k;
                }
              }
            }
            cache[indexOfVictim] = currentNum;
            // indexMappings.get(currentNum).remove((Integer) j);
            evictionCounter++;
            indexMappings.get(currentNum).remove((Integer) j);

          }
        }
        // indexMappings.get(currentNum).remove((Integer) j);
        currentContent.pages.remove(0);
        j++;
      }
      allInstances.remove(i);
      System.out.println(evictionCounter);
    }

  }

}
