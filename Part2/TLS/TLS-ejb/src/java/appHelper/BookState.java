

package appHelper;
import ejb.BookEntity;
import ejb.MemberEntity;
import ejb.ReservationEntity;
import ejb.TitleEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
public class BookState implements Serializable{
    private long id;
    private int copyNumber;
    private String renew=null;
    private Calendar loanDate;
    private Calendar dueDate;
    private TitleEntity title = new TitleEntity();
    private MemberEntity member = new MemberEntity();
    private List<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public BookState(BookEntity be){
        id=be.getId();
        copyNumber=be.getCopyNumber();
        loanDate=be.getLoanDate();
        dueDate=be.getDueDate();
        title=be.getTitle();
        member=be.getMember();
        reservations=be.getReservations();
    }
    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }
/*
    public String getStatus() {
        return status;
    }

    public void setLoanStatus() {
        this.status=("On Loan");
    }
*/

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Calendar loanDate) {
        this.loanDate = loanDate;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public TitleEntity getTitle() {
        return title;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }
}
