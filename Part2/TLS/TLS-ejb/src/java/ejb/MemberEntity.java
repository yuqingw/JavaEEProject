/*
 * MemberEntity.java
 */

package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="Member")
public class MemberEntity implements Serializable {

    @Id
    private long id;
    private String name;
    private String type;
    private String password;
    @OneToOne(cascade={CascadeType.ALL})
    private ContactEntity contact;
    @OneToMany(mappedBy="member")
    private List<BookEntity> books = new ArrayList<BookEntity>();
    @OneToOne(cascade={CascadeType.ALL})
    private FineEntity fine;
    @OneToMany(cascade={CascadeType.ALL},mappedBy="mem")
    private List<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    /** Creates a new instance of MemberEntity */
    public MemberEntity() {
        this.setId(System.nanoTime());
    }

    public void create(String name, String type, String password) {
        this.setName(name);
        this.setType(type);
        this.setPassword(password);
    }

    public ContactEntity getContact() {
        return contact;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public FineEntity getFine() {
        return fine;
    }

    public void setFine(FineEntity fine) {
        this.fine = fine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
