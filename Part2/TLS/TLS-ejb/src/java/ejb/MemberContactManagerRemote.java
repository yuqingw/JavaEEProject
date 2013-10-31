/*
 *MemberContactManagerRemote.java
 */
package ejb;

import javax.ejb.Remote;

@Remote
public interface MemberContactManagerRemote {
    public void createMember(String name, String type, String password);
    public String removeMember(Long memberId);
    public void createContact(String department, String faculty,
                String phoneNumber, String emailAddress);
    public String persist();
    public void remove();
}
