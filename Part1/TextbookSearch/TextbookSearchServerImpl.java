package textbooksearch;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;




public class TextbookSearchServerImpl extends UnicastRemoteObject
		implements TextbookSearchServer {
	
	private Vector course=new Vector();
    	private Vector<String> tbnames=new Vector<String>();

  	public TextbookSearchServerImpl() throws RemoteException{
	 BufferedReader courseInput= null;
        BufferedReader tbInput= null;
        String line;

	try{
            
            courseInput = new BufferedReader(new FileReader ("course.txt"));

             while ((line = courseInput.readLine()) != null) {

                Integer x = Integer.valueOf(line);
                course.addElement(x);
            }

            

            tbInput = new BufferedReader(new FileReader ("textbook.txt"));

             while ((line = tbInput.readLine()) != null) {
                 tbnames.addElement(line);
             }



        }
        catch (IOException e){
        System.out.println(e);
        System.exit(1);}

	}


	public boolean search(int courseCode) throws RemoteException{
	

        for (int i=0; i<course.size(); i++)
        {
            if (course.elementAt(i).equals(courseCode))
            {   
		System.out.println("\nsearch(): course code " + courseCode +" ==> true");
                return true;
            }
        
        }

	


	System.out.println("\nsearch(): course code " + courseCode +" ==> false");
        return false;
	
		
}

	public String getTextbook(int courseCode) throws RemoteException{



        
        for (int i=0; i<course.size(); i++)
        {

            if (course.elementAt(i).equals(courseCode) && i < tbnames.size())
            {
            	System.out.println("getTextbook(): " + courseCode + " textbook is \"" + tbnames.elementAt(i)+ "\"" );
            	return tbnames.elementAt(i);}
            if (course.elementAt(i).equals(courseCode) && !(i< tbnames.size()))
            {
                System.out.println("getTextbook(): " + courseCode +" textbook is \"" + "Textbook not available\"" );
                return "not available";
            }

        }
		
                    return "Course code not found";
        


    }
	

	public int checkCopies(String textbook) throws RemoteException{
	for (int i = 0; i < tbnames.size(); i++)
        {

         if (tbnames.elementAt(i).equals(textbook))
         {
              int counter = 0;

    	boolean word = false;
    	int endOfLine = textbook.length() - 1;

    	for (int j = 0; j < textbook.length(); j++) {
        // if the char is letter, word = true.
        if (Character.isLetter(textbook.charAt(j)) == true && j != endOfLine) {
            word = true;
            // if char isnt letter and there have been letters before (word
            // == true), counter goes up.
        } else if (Character.isLetter(textbook.charAt(j)) == false && word == true) {
            counter++;
            word = false;
            // last word of String, if it doesnt end with nonLetter it
            // wouldnt count without this.
        } else if (Character.isLetter(textbook.charAt(j)) && j == endOfLine) {
            counter++;
        }
    }
    System.out.println("checkCopies(): \"" + textbook + "\" ==> " + counter + " copies" );
    return counter;


        }
        }

        return 0;
}
	

}
