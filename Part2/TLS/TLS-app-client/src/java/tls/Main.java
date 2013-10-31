/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tls;
import ejb.MemberBookManagerRemote;
import ejb.MemberContactManagerRemote;
import ejb.TitleBookManagerRemote;
import javax.ejb.EJB;
import java.util.*;

public class Main {
    @EJB
    private static MemberContactManagerRemote mcm;
    @EJB
    private static TitleBookManagerRemote tbm;
    @EJB
    private static MemberBookManagerRemote mbm;

    public static void main(String[] args) {
        Main client = new Main();
        client.manage(args);
        mcm.remove();
        tbm.remove();
        mbm.remove();
    }

    public void manage(String[] args) {

        try {
/*
            Long a = Long.parseLong("111");
            System.out.println(mbm.checkMem(a));
            System.out.println(mbm.findMem(a));*/
            int choice = 10;
            while (choice!=0){
                System.out.println("\n\n-------------Welcome to the Library System--------------");
                System.out.println("1. Register a member and the associated contact");
                System.out.println("2. Delete a member");
                System.out.println("3. Add a title");
                System.out.println("4. Add a book");
                System.out.println("5. Delete a book");
                System.out.println("6. Facilitate loans");
                System.out.println("7. Facilitate book returns");
                System.out.println("0. Exit");

                System.out.print("\nEnter choice: ");
                Scanner sc=new Scanner(System.in);
                choice = sc.nextInt();

                if (choice==1)
                    addMember();
                else if (choice==2)
                    deleteMember();
                else if (choice==3)
                    addTitle();
                else if (choice==4)
                    addBook();
                else if (choice==5)
                    deleteBook();
                else if (choice==6)
                    borrowBook();
                else if (choice==7)
                    returnBook();
                else if (choice==0)
                    return;
                else
                    System.out.println("Invalid Choice");
            }
            return;

        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void addMember() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println();
            System.out.println("-------------Member Registration-------------");
            System.out.println();
            System.out.println("Enter the member's name:");
            String name = sc.nextLine();
            System.out.println("Enter the member type(student or staff):");
            String type = sc.next();
            System.out.println("Enter the password:");
            String password = sc.next();
            System.out.println("Enter the member's department:");
            String department = sc.next();
            System.out.println("Enter the member's faculty:");
            String faculty = sc.next();
            System.out.println("Enter the member's phone number:");
            String phone = sc.next();
            System.out.println("Enter the member's email address:");
            String email = sc.next();
            System.out.println();

            mcm.createMember(name, type, password);
            mcm.createContact(department,faculty,phone,email);
            String result = mcm.persist();
            System.out.println();
            System.out.println(result);

            return;
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void deleteMember() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("-------------Delete Member-------------");
            System.out.println();
            System.out.println("Enter the member's ID:");
            Long memberId = Long.valueOf(sc.next()).longValue();
            String result = mcm.removeMember(memberId);
            System.out.println(result);

        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void addTitle() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println();
            System.out.println("-------------Add New Title-------------");
            System.out.println();
            System.out.println("Enter the ISBN code:");
            String isbn = sc.next();
            System.out.println("Enter the title:");
            sc.nextLine();
            String title = sc.nextLine();
            System.out.println("Enter the author:");
            String author = sc.nextLine();
            System.out.println("Enter the publisher:");
            String publisher = sc.next();

            tbm.createTitle(isbn, title, author, publisher);
            String result = tbm.persistTitle(isbn);

            System.out.println();
            System.out.println(result);

            } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void addBook() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println();
            System.out.println("-------------Add New Book-------------");
            System.out.println();
            System.out.println("Enter the ISBN of the title of the book:");
            String isbn = sc.next();
            String result =tbm.createBook(isbn);

            System.out.println(result);
            } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void deleteBook() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("-------------Delete Book-------------");
            System.out.println();
            System.out.println("Enter the book ID:");
            Long bookId = Long.valueOf(sc.next()).longValue();
            String result = tbm.removeBook(bookId);
            System.out.println(result);

            } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void borrowBook() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("-------------Facilitate Loans-------------");
            System.out.println();
            System.out.println("Enter the member ID:");
            Long memberId = Long.valueOf(sc.next()).longValue();
            System.out.println("Enter the book ID:");
            Long bookId = Long.valueOf(sc.next()).longValue();

            String result = mbm.borrowBook(memberId, bookId);

            System.out.println(result);

        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }

    private void returnBook() {

        try{
            Scanner sc=new Scanner(System.in);
            System.out.println("-------------Facilitate returns-------------");
            System.out.println();
            System.out.println("Enter the member ID:");
            Long memberId = Long.valueOf(sc.next()).longValue();
            System.out.println("Enter the book ID:");
            Long bookId = Long.valueOf(sc.next()).longValue();

            String result = mbm.returnBook(memberId, bookId);

            System.out.println(result);

        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception!");
            ex.printStackTrace();
        }
    }
}

