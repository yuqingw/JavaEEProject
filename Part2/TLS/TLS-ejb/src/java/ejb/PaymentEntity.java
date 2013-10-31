/*
 * PaymentEntity.java
 */

package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="PaymentEntity")
public class PaymentEntity implements Serializable {

    @Id
    private long id;
    private float amount = 0;

    /** Creates a new instance of ContactEntity */
    public PaymentEntity() {
        setId(System.nanoTime());
    }

    public void create(float amount) {
        this.setAmount(amount);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = this.amount + amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}