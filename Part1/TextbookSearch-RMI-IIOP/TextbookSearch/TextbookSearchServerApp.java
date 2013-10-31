package textbooksearch;
import javax.naming.*;
import java.util.Properties;

public class TextbookSearchServerApp {

static final String FACTORY = "java.naming.factory.initial";
static final String FACTORY_NAME = "com.sun.jndi.cosnaming.CNCtxFactory";
static final String PROVIDER = "java.naming.provider.url";
static final String PROVIDER_URL = "iiop://localhost:900";


  public static void main(String args[]) {
    try {
      TextbookSearchServerImpl textbooksearchServerImpl;
      textbooksearchServerImpl = new TextbookSearchServerImpl();

	Properties props = new Properties();
	props.put(TextbookSearchServerApp.FACTORY, TextbookSearchServerApp.FACTORY_NAME);
	props.put(TextbookSearchServerApp.PROVIDER, TextbookSearchServerApp.PROVIDER_URL);
	InitialContext ic = new InitialContext(props);
	
	ic.rebind("TextbookSearchServer", textbooksearchServerImpl);
      
      System.out.println("TextbookSearchServer Started \n");

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
