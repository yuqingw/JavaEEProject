/*
 * FineEntity.java
 */

package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Fine")
public class FineEntity implements Serializable {

    @Id
    private long id;
    private float amount = 0;
    @OneToMany
    private List<PaymentEntity> payments = new ArrayList<PaymentEntity>();

    /** Creates a new instance of ContactEntity */
    public FineEntity() {
        setId(System.nanoTime());
    }

    public void create(float amount) {
        this.setAmount(amount);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amt) {
        this.amount = this.amount + amt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentEntity> payments) {
        this.payments = payments;
    }
}

