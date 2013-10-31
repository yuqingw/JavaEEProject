package lps;

import java.text.ParseException;
import java.util.Scanner;
import javax.ejb.EJB;
import ejb.FinePaymentManagerRemote;

/**
 *
 * @author Yao Wenchi
 */
public class Main {

    @EJB
    private static FinePaymentManagerRemote bean;
    private Scanner sc;

    public Main() {
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, InterruptedException {
        Main clientTest = new Main();
        clientTest.welcome();
    }

    public void welcome() throws ParseException, InterruptedException {
        int selection = -1;
        System.out.println("\n\n\n[Welcome to fine payment system]\n");
        System.out.println("1. Display outstanding fine");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Your selection: ");
        sc = new Scanner(System.in);
        selection = sc.nextInt();
        
        switch (selection) {
            case 1: {
                this.displayFine();
                this.welcome();
            }
            case 0: {
                System.out.println("\nThank you for using the libraray fine payment system! Good-bye!\n\n");
                System.exit(0);
            }
            default: {
                System.out.println("Invalid selection...");
                this.welcome();
            }
        }
    }

    public String displayFine() throws InterruptedException{
        System.out.println("Please enter your member ID: ");
        Long memId = sc.nextLong();
        bean.sendMessageMem(memId, "display", 0);
        Thread.sleep(15000);

        String result = bean.getResult();
        if ((result.equals("No outstanding fine"))||(result.equals("Invalid member ID"))){
            System.out.println(result);
            return (result);
        }
        else {
            System.out.println(result);
            return (pay(memId));
        }
    }

    public String pay(Long memId) throws InterruptedException{
        System.out.println("Please enter the amount you want to pay: ");
        float amt = sc.nextFloat();
        bean.sendMessageMem(memId, "pay", amt);
        Thread.sleep(15000);
        String result = bean.getResult();
        System.out.println(result);
        return (result);
    }    
}