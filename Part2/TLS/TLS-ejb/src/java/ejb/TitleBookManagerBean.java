/*
 *TitleBookManagerBean.java
 */
package ejb;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class TitleBookManagerBean implements TitleBookManagerRemote {

    @PersistenceContext
    EntityManager em;
    private BookEntity bookEntity;
    private TitleEntity titleEntity;
    private List<BookEntity> books;

    public TitleBookManagerBean() {
    }

    public void createTitle(String isbn, String title, String author, String publisher) {
        titleEntity = new TitleEntity();
        titleEntity.create(isbn, title, author, publisher);
    }

    public String createBook(String isbn) {
        int maxCopy = 0;
        int temp = 0;
        if (em.find(TitleEntity.class, isbn) == null) {
            return ("ISBN invalid!");
        } else {
            titleEntity = em.find(TitleEntity.class, isbn);
            bookEntity = new BookEntity();
            books = titleEntity.getBooks();

            for (int i = 0; i < books.size(); i++) {
                temp = books.get(i).getCopyNumber();
                if (temp >= maxCopy) {
                    maxCopy = temp;
                }
            }
            bookEntity.setCopyNumber(maxCopy + 1);
            bookEntity.setTitle(titleEntity);
            bookEntity.setMember(null);
            books = titleEntity.getBooks();
            books.add(bookEntity);
            titleEntity.setBooks(books);
            em.persist(bookEntity);
            return ("New book successfully added!\nThe total number of books under title "+bookEntity.getTitle().getTitle()+ " is " + books.size());
        }
    }

    public String removeBook(long bookId) {
        if (em.find(BookEntity.class, bookId) == null) {
            return ("Book not found!");
        } else {
            bookEntity = em.find(BookEntity.class, bookId);
            titleEntity = bookEntity.getTitle();
            titleEntity.getBooks().remove(bookEntity);
            int amount = titleEntity.getBooks().size();
            if (bookEntity.getMember() != null) {
                return ("Book must be returned first!");
            }
            em.remove(bookEntity);
            return ("Book successfully deleted!\nThe number of book with this title is now " + amount);
        }
    }

    public String persistTitle(String isbn) {
        if (em.find(TitleEntity.class, isbn) == null) {
            em.persist(titleEntity);

            return ("New title successfully added!");
        } else {
            return ("ISBN code already exists!");
        }
    }

    @Remove
    public void remove() {
        System.out.println("MemberContactManagerBean:remove()");
    }
}
