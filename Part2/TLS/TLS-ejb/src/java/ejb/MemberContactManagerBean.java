/*
 *MemberContactManagerBean.java
 */
package ejb;

import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class MemberContactManagerBean implements MemberContactManagerRemote {

    @PersistenceContext
    EntityManager em;

    private MemberEntity memberEntity;
    private ContactEntity contactEntity;

    public MemberContactManagerBean(){
    }

    public void createMember(String name, String type, String password) {
        memberEntity = new MemberEntity();
        memberEntity.create(name, type, password);
    }

    public void createContact(String department, String faculty,
            String phoneNumber, String emailAddress) {
        contactEntity = new ContactEntity();
        contactEntity.create (department, faculty, phoneNumber, emailAddress);
    }

    public String persist(){
        em.persist(memberEntity);
        em.persist(contactEntity);
        memberEntity.setContact(contactEntity);
        return ("New member successfully added!");
    }

    public String removeMember(Long memberId) {
        if(em.find(MemberEntity.class, memberId)==null)
            return ("Member not found!") ;
        MemberEntity me = em.find(MemberEntity.class, memberId);

        if(me.getBooks().size()!=0)
            return ("Member has outstanding loans!\nMember cannot be deleted!");
        
        if(me.getFine()!=null){
            if(me.getFine().getAmount()>0.0001)
                //to avoid float number calculation error(eg. 2.2-2.20 = 0.0000001)
                return ("Member has fine!\nMember cannot be deleted");
        }

        em.remove(me);
        return ("Member successfully deleted!");
    }

    @Remove
    public void remove() {
        System.out.println("MemberContactManagerBean:remove()");
    }
}