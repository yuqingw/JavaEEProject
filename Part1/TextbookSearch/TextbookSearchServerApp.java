package textbooksearch;
import java.rmi.Naming;

public class TextbookSearchServerApp {

  public static void main(String args[]) {
    try {
      TextbookSearchServerImpl textbooksearchServerImpl;
      textbooksearchServerImpl = new TextbookSearchServerImpl();
      Naming.rebind("TextbookSearchServer", textbooksearchServerImpl);
      System.out.println("TextbookSearchServer Started \n");

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
