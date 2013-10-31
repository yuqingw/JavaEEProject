/*
 *MemberBookManagerBean.java
 */
package ejb;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import appHelper.BookState;

@Stateful
public class MemberBookManagerBean implements MemberBookManagerRemote {

    @PersistenceContext
    EntityManager em;
    private BookEntity bookEntity;
    private MemberEntity memberEntity;
    private List<BookEntity> books;
    private FineEntity fineEntity;

    public MemberBookManagerBean() {
    }

    public String borrowBook(Long memberId, Long bookId) {
        if (em.find(MemberEntity.class, memberId) == null ||
                em.find(BookEntity.class, bookId) == null) {
            return ("Member or book not found!");
        }

        memberEntity = em.find(MemberEntity.class, memberId);
        if (memberEntity.getBooks().size() >= 6) {
            return ("Member cannot borrow more than 6 books!");
        }
        if (memberEntity.getFine() != null && memberEntity.getFine().getAmount() > 0) {
            return ("There are still outstanding fines!");
        }
        bookEntity = em.find(BookEntity.class, bookId);
        if (bookEntity.getMember() != null) {
            return ("Book is not available!");
        }
        bookEntity.setMember(memberEntity);
        Calendar loanDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();
        dueDate.add(Calendar.DATE, 14);
        bookEntity.setLoanDate(loanDate);
        bookEntity.setDueDate(dueDate);

        books = memberEntity.getBooks();
        books.add(bookEntity);
        memberEntity.setBooks(books);
        em.persist(memberEntity);
        return ("New loan successfully created!");
    }

    public String returnBook(Long memberId, Long bookId) {

        if (em.find(MemberEntity.class, memberId) != null) {
            memberEntity = em.find(MemberEntity.class, memberId);
            if (memberEntity.getBooks().isEmpty()) {
                return ("No book on loan");
            } else if (em.find(BookEntity.class, bookId) == null) {
                return ("Book ID not found!");
            } else {
                bookEntity = em.find(BookEntity.class, bookId);
                for (int i = 0; i < memberEntity.getBooks().size(); i++) {
                    if (bookId == memberEntity.getBooks().get(i).getId()) {
                        System.out.println("lalalall");
                        float fine = this.computeFine();
                        System.out.println(fine);
                        bookEntity.setMember(null);
                        bookEntity.setLoanDate(null);
                        bookEntity.setDueDate(null);
                        memberEntity.getBooks().remove(bookEntity);
                        if (fine > 0) {
                            System.out.println("fine>0, enter");
                            if (memberEntity.getFine() != null) {
                                System.out.println("fine before");
                                fineEntity = memberEntity.getFine();
                                fineEntity.setAmount(fine);
                                memberEntity.setFine(fineEntity);
                            } else {
                                System.out.println("no fine before");
                                this.createFine(fine);
                                memberEntity.setFine(fineEntity);
                                em.persist(memberEntity);
                            }
                            return ("Book successfully returned!\nFine for late return: $" + fine);
                        }

                        return ("Book successfully returned!");
                    }
                }
                return ("Book does not belong to you!");                
            }
        }
        return ("Member not found!");
    }

    public void createFine(float amount) {
        fineEntity = new FineEntity();
        fineEntity.create(amount);
        em.persist(fineEntity);
    }

    public float computeFine() {

        Calendar dueDate = bookEntity.getDueDate();
        Calendar today = Calendar.getInstance();
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        long endInstant = today.getTimeInMillis();
        int presumedDays = (int) ((endInstant - dueDate.getTimeInMillis()) / MILLIS_IN_DAY);
        
        return (float) presumedDays;
    }

    public BookState findBook(Long bookId) {
        if (em.find(BookEntity.class, bookId) != null) {
            BookEntity be = em.find(BookEntity.class, bookId);
            BookState bs = new BookState(be);
            return bs;
        } else {
            return null;
        }
    }

    public boolean checkMem(Long memberId) {
        if (em.find(MemberEntity.class, memberId) != null) {
            return true;
        }
        return false;
    }

    public MemberEntity findMem(Long memberId) {
        if (checkMem(memberId)) {
            return em.find(MemberEntity.class, memberId);
        } else {
            return null;
        }
    }

    public ArrayList view(Long memberId) {
        MemberEntity me = em.find(MemberEntity.class, memberId);
        ArrayList<BookState> list = new ArrayList<BookState>();

        if (me != null) {
            for (Object o : me.getBooks()) {
                BookEntity be = (BookEntity) o;
                BookState bs = new BookState(be);
                list.add(bs);
            }
        }
        return list;

    }

    public String extendLoan(Long memberId, Long bookId) {
        MemberEntity m = em.find(MemberEntity.class, memberId);
        for (int i = 0; i < m.getBooks().size(); i++) {
            if (m.getBooks().get(i).getId() == bookId) {
                BookEntity bk = em.find(BookEntity.class, bookId);
                if (bk.getReservations().isEmpty()) {
                    Calendar temp = Calendar.getInstance();
                    if (temp.after(bk.getDueDate())) {
                        return ("The book is overdue!");
                    } else {
                        temp.add(Calendar.DATE, 14);
                        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
                        long endInstant = bk.getDueDate().getTimeInMillis();
                        int loanDays = (int) ((endInstant - bk.getLoanDate().getTimeInMillis()) / MILLIS_IN_DAY);
                        if (loanDays == 14) {
                            bk.setDueDate(temp);
                            return ("The book loan is extended successfully ");
                        } else {
                            return ("The book cannot be renewed more than once!");
                        }
                    }
                } else {
                    return ("Book is reserved by another person!");
                }
            }
        }
        return ("This book was not borrowed by this member!");
    }

    public ArrayList searchBookByTitle(String title) {

        Query q = em.createQuery("SELECT b FROM BOOK b ");
        List<BookEntity> list1 = new ArrayList();
        ArrayList<BookState> list2 = new ArrayList();
        for (Object o : q.getResultList()) {
            BookEntity b = (BookEntity) o;
            if (b.getTitle().getTitle().equals(title)) {
                list1.add(b);
            }
        }
        if (list1.isEmpty()) {
            return null;
        }
        for (int i = 0; i < list1.size(); i++) {
            BookState bs = new BookState(list1.get(i));
            list2.add(bs);
        }
        return list2;
    }

    public ArrayList searchBookByAuthor(String name) {
        Query q = em.createQuery("SELECT b FROM BOOK b ");
        List<BookEntity> list1 = new ArrayList<BookEntity>();
        ArrayList<BookState> list2 = new ArrayList<BookState>();
        for (Object o : q.getResultList()) {
            BookEntity b = (BookEntity) o;
            if (b.getTitle().getAuthor().equals(name)) {
                list1.add(b);
            }
        }
        if (list1.isEmpty()) {
            return null;
        }
        for (int i = 0; i < list1.size(); i++) {
            BookState bs = new BookState(list1.get(i));
            list2.add(bs);
        }
        System.out.println(list1.size() + "  " + list2.size());
        return list2;
    }

    @Remove
    public void remove() {
        System.out.println("MemberContactManagerBean:remove()");
    }
}
