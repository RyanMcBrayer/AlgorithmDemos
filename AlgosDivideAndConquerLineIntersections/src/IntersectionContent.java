import java.util.ArrayList;
public class IntersectionContent {

  public long numIntersections;
  public ArrayList<Line> content;
  
  public IntersectionContent(long numIntersections,ArrayList<Line> content) {
    this.content = content;
    this.numIntersections = numIntersections;
  }
}