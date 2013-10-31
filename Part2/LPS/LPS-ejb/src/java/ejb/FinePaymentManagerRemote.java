/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import javax.ejb.Remote;

@Remote
public interface FinePaymentManagerRemote {
    public void sendMessageMem(Long MemId, String type, float pAmt);
    public String getResult();
}
