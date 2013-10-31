/*
 *TitleBookManagerRemote.java
 */
package ejb;

import javax.ejb.Remote;

@Remote
public interface TitleBookManagerRemote {
    public void createTitle(String isbn, String title, String author, String publisher);
    public String createBook(String isbn);
    public String removeBook(long bookId);
    public String persistTitle(String isbn);
    public void remove();
}
