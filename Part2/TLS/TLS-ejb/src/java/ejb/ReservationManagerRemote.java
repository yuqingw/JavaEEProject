package ejb;

import appHelper.ReservationState;
import java.util.ArrayList;
import javax.ejb.Remote;

@Remote
public interface ReservationManagerRemote {
    public ArrayList viewReservation(Long memId);
    public String delete(Long id);
    public ReservationState makeReservation(Long memId, Long bookId);
    
}
