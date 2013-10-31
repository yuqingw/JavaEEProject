
package appHelper;
import ejb.BookEntity;
import ejb.MemberEntity;
import ejb.ReservationEntity;
import java.io.Serializable;
import java.util.Calendar;
 
public class ReservationState implements Serializable{
    private long id;
    private Calendar ReserveDate;
    private MemberEntity member = null;
    private BookEntity book = null;
    private String status = "";

    public ReservationState(){
        
    }

    public ReservationState(ReservationEntity re){
        id=re.getReservationId();
        ReserveDate = re.getReservationDate();
        member=re.getMem();
        book=re.getBook();
    }

    public Calendar getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(Calendar ReserveDate) {
        this.ReserveDate = ReserveDate;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
