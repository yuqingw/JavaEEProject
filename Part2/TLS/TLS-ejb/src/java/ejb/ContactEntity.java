/*
 * ContactEntity.java
 */

package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Contact")
public class ContactEntity implements Serializable {

    @Id
    private long   id;
    private String department;
    private String faculty;
    private String phoneNumber;
    private String emailAddress;

    /** Creates a new instance of ContactEntity */
    public ContactEntity() {
        setId(System.nanoTime());
    }

    public void create(String department, String faculty, String phoneNumber,
            String emailAddress) {
        this.setDepartment(department);
        this.setFaculty(faculty);
        this.setPhoneNumber(phoneNumber);
        this.setEmailAddress(emailAddress);
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFaculty() {
        return faculty;
    }

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

