import java.util.ArrayList;

public class KnapsackInstance {

  public int capacity;
  public int numItems;
  public ArrayList<KnapsackItem> items;
  
  public KnapsackInstance(int numItems, int capacity, ArrayList<KnapsackItem> items) {
    this.capacity = capacity;
    this.numItems = numItems;
    this.items = items;
  }
}
