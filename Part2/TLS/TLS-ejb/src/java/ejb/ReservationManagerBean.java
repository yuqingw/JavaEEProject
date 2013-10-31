package ejb;

import appHelper.ReservationState;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateful
public class ReservationManagerBean implements ReservationManagerRemote {

    @PersistenceContext
    EntityManager em;
    private BookEntity bookEntity;
    private MemberEntity memberEntity;

    public ArrayList viewReservation(Long memId) {
        MemberEntity me = em.find(MemberEntity.class, memId);
        List<ReservationEntity> list1 = me.getReservations();
        ArrayList<ReservationState> list2 = new ArrayList<ReservationState>();

        for (int i = 0; i < list1.size(); i++) {
            ReservationState bs = new ReservationState(list1.get(i));
            list2.add(bs);
        }
        return list2;
    }

    public ReservationState makeReservation(Long memId, Long bookId) {
        ReservationEntity re = new ReservationEntity();
        ReservationState rs = new ReservationState();
        if (em.find(BookEntity.class, bookId) == null) {
            return null;
        }
        memberEntity = em.find(MemberEntity.class, memId);
        bookEntity = em.find(BookEntity.class, bookId);
        for (int i = 0; i < memberEntity.getReservations().size(); i++) {
            if (memberEntity.getReservations().get(i).getBook().getId() == bookId) {
                rs.setStatus("Reservation failed! You already reserved the book!");
                return rs;
            }
        }
        for (int j = 0; j < memberEntity.getBooks().size(); j++) {
            if (memberEntity.getBooks().get(j).getId() == bookId) {
                rs.setStatus("Reservation failed! You already borrowed the book!");
                return rs;
            }
        }
        re.setBook(bookEntity);
        re.setMem(memberEntity);
        List<ReservationEntity> re1 = memberEntity.getReservations();
        List<ReservationEntity> re2 = bookEntity.getReservations();
        re1.add(re);
        re2.add(re);
        memberEntity.setReservations(re1);
        bookEntity.setReservations(re2);
        em.persist(re);
        rs = new ReservationState(re);
        rs.setStatus("Reservation successful!");
        return rs;
    }

    public String delete(Long id) {
        if (em.find(ReservationEntity.class, id) == null) {
            return ("Reservation not found!");
        } else {
            ReservationEntity re = em.find(ReservationEntity.class, id);
            MemberEntity me = re.getMem();
            me.getReservations().remove(re);
            BookEntity be = re.getBook();
            be.getReservations().remove(re);
            em.remove(re);
        }
        return ("Reservation successfully deleted!");
    }
}
