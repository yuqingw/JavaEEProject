package textbooksearch;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {

	static final String FACTORY = "java.naming.factory.initial";
	static final String FACTORY_NAME = "com.sun.jndi.cosnaming.CNCtxFactory";
	static final String PROVIDER = "java.naming.provider.url";
	static String providerUrl = "iiop://";


  public static void main(String args[]) {
	

	if (args.length <1) {
	System.out.println("Usage: java textbooksearch.Main" + "<serverIPAddress>:<serverPort>");
	System.exit(0);
	}

try{
	Main.providerUrl = Main.providerUrl + args[0];
	
	Properties props = new Properties();
	props.put(Main.FACTORY, Main.FACTORY_NAME);
	props.put(Main.PROVIDER, Main.providerUrl);
	InitialContext ic = new InitialContext(props);
	
      	TextbookSearchServer textbooksearchServer;
      	textbooksearchServer = (TextbookSearchServer) PortableRemoteObject.narrow(ic.lookup ("TextbookSearchServer"), TextbookSearchServer.class);
	


      	
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