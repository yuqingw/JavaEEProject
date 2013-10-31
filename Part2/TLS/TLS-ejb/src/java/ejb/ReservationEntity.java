
package ejb;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name="ReservationEntity")
public class ReservationEntity implements Serializable {

    @Id
    private Long reservationId;
    @Temporal(value = TemporalType.DATE)
    private Calendar reservationDate;
    @ManyToOne
    private MemberEntity mem;
    @ManyToOne
    private BookEntity book;

    public ReservationEntity() {
        setReservationId(System.nanoTime());
        Calendar c = Calendar.getInstance();
        setReservationDate(c);
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Calendar getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Calendar reservationDate) {
        this.reservationDate = reservationDate;
    }

    public MemberEntity getMem() {
        return mem;
    }

    public void setMem(MemberEntity mem) {
        this.mem = mem;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}