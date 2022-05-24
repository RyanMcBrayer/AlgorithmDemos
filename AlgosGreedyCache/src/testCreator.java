import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class testCreator {

  public static void main(String[] args) throws IOException{

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = reader.readLine();
    System.out.println(input);
    reader.close();
    
    for(int i= 0;i<input.length();i++) {
      
    }
  }

}
