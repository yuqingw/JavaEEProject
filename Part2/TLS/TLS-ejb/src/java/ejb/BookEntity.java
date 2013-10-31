package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity(name = "BOOK")
public class BookEntity implements Serializable {

    @Id
    private long id;
    private int copyNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar loanDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dueDate;
    @ManyToOne
    private TitleEntity title = new TitleEntity();
    @ManyToOne
    private MemberEntity member = new MemberEntity();
    @OneToMany(cascade={CascadeType.ALL},mappedBy ="book")
    private List<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public BookEntity(){
        this.setId(System.nanoTime());
    }

    public void create(int copyNumber){
        this.copyNumber = copyNumber;
    }

    public TitleEntity getTitle() {
        return title;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Calendar getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Calendar loanDate) {
        this.loanDate = loanDate;
    }
}