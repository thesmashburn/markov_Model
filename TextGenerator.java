import java.io.File;
import java.io.IOException;

/**
 * TextGenerator.java. Creates an order K Markov model of the supplied source
 * text, and then outputs M characters generated according to the model.
 *
 * @author     Spencer Mashburn (dsm0015@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2016-04-19
 *
 */
public class TextGenerator {

   /** Drives execution. */
   public static void main(String[] args) {

      if (args.length < 3) {
         System.out.println("Usage: java TextGenerator k length input");
         return;
      }

      // No error checking!
      int K = Integer.parseInt(args[0]);
      int M = Integer.parseInt(args[1]);
      if ((K < 0) || (M < 0)) {
         System.out.println("Error: Both K and M must be non-negative.");
         return;
      }

      File text;
      try {
         text = new File(args[2]);
         if (!text.canRead()) {
            throw new Exception();
         }
      }
      catch (Exception e) {
         System.out.println("Error: Could not open " + args[2] + ".");
         return;
      }


      // instantiate a MarkovModel with the supplied parameters and
      // generate sample output text ...
      String result = "";
      MarkovModel showTime = new MarkovModel(K, text);
      String initKgram = showTime.getFirstKgram();
     
      
      result = result.concat(initKgram);
      
      for (int i = 0; i < M - K; i++) {
      char add = showTime.getNextChar(initKgram);
      result = result.concat(Character.toString(add));
      
      initKgram = initKgram.substring(1) + Character.toString(add);
      
      }
     
      //Output
           
       System.out.print(result);



   }
}
