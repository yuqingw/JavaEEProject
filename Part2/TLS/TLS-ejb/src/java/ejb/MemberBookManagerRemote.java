/*
 *MemberBookManagerRemote.java
 */
package ejb;

import appHelper.BookState;
import java.util.ArrayList;
import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MemberBookManagerRemote {
    public String borrowBook(Long memberId, Long bookId);
    public String returnBook(Long memberId, Long bookId);
    public void createFine(float amount);
    public float computeFine();
    public void remove();
    public ArrayList view(Long memberId);
    public boolean checkMem(Long memberId);
    public String extendLoan(Long memberId, Long bookId);
    public List<BookState> searchBookByTitle(String title);
    public List<BookState> searchBookByAuthor(String name);
    public MemberEntity findMem(Long memberId);
    public BookState findBook(Long bookId);
}
