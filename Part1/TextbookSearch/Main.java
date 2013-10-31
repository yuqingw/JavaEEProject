package textbooksearch;
import java.rmi.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {

  public static void main(String args[]) {
	
try{


	String textbooksearchServerURL;
	textbooksearchServerURL = "rmi://" + args[0] + "/TextbookSearchServer";
	// Obtain a reference to that remote object
      	TextbookSearchServer textbooksearchServer;
      	textbooksearchServer = (TextbookSearchServer) Naming.lookup(textbooksearchServerURL);
	


      	// Display numbers
	String userinput;

	while (true)
      	{

                Scanner scanner = new Scanner(System.in);
                System.out.print("\nEnter course code <'Q' or 'q' to exit>: ");
                userinput = scanner.next();
		if (userinput.equalsIgnoreCase("q"))
                {

                    System.out.println("You have exited the program.");
                    break;
                }

                int courseCode= Integer.parseInt(userinput);
		
		boolean found = textbooksearchServer.search(courseCode);
                String textbookName = textbooksearchServer.getTextbook(courseCode);
                int numofCopies= textbooksearchServer.checkCopies(textbookName);
                if (!found){
		System.out.println("Course code not found");
                }
                if (found && numofCopies >0)
                {

		System.out.println ("Course code " + courseCode + " found");
                System.out.println ("Textbook for Course Code " + courseCode + " is \"" + textbookName + "\"");
                System.out.println ("There are " + numofCopies + " copies of Textbook \"" + textbookName + "\"");
                }

                if (found && numofCopies ==0)
                {
                    System.out.println ("Course code " + courseCode + " found");
                    System.out.println ("Textbook for Course Code " + courseCode + " is not available");
                }

}
}catch (Exception ex)
{ex.printStackTrace();
}
}
}   