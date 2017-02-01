import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Spencer Mashburn (dsm0015@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2016-04-19
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   private String firstKnugget;


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      int sourceSize = sourceText.length();
      for (int i = 0; i <= (sourceSize - K); i++) {
         String temp = "";
         for (int j = i; j < i + K; j++) {
            String currentChar = Character.toString(sourceText.charAt(j));
            temp += currentChar;
         }
         if (!model.containsKey(temp)) {
            String init = "";
            model.put(temp, init);
            if (i == 0) firstKnugget = temp;
         }
         String value;
         if (i != sourceSize - K) value = Character.toString(sourceText.charAt(i + K));
         else value = Character.toString('\u0000');
         String editKey = model.get(temp).concat(value);
         model.remove(temp);
         model.put(temp, editKey);
      }
   
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstKnugget;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      Random randomGen = new Random();
      List<String> keyValues =new ArrayList<String>(model.keySet());
      String result = keyValues.get(randomGen.nextInt(keyValues.size()));
            
      return result;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      if (!model.containsKey(kgram)) {
         return '\u0000';
      }
      String frequency = model.get(kgram);
      if (frequency.isEmpty()) {
         return '\u0000';}
      
      Random randomGen = new Random();
      int index = randomGen.nextInt(frequency.length());
      char result = frequency.charAt(index);
      
           
   
      return result;
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
