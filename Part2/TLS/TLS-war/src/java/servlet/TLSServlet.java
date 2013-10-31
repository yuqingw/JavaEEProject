/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ejb.*;
import appHelper.BookState;
import appHelper.ReservationState;
import java.util.ArrayList;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author surfer
 */
public class TLSServlet extends HttpServlet {

    @EJB
    private MemberBookManagerRemote mbm;
    @EJB
    private ReservationManagerRemote rm;

    private MemberEntity mem= null;

    private ArrayList data = null;

    public void init() {
        System.out.println("TLSServlet: init()");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TLSServlet: processRequest()");

        try {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();

            String page = request.getPathInfo();
            page = page.substring(1);

            if ("index".equals(page)) {

            } else if ("ListBooks".equals(page)) {
                data = (ArrayList) mbm.view(mem.getId());
                request.setAttribute("data", data);
            } else if ("action".equals(page)) {
                System.out.println("action");
                Long memId = Long.parseLong(request.getParameter("ID"));
                System.out.println(memId);
                String password = request.getParameter("password");
                System.out.println(password);
                mem = mbm.findMem(memId);
                if (mem != null) {
                    if (!(mem.getPassword().equals(password))||!(mbm.checkMem(memId))) {
                        page = "logFail";
                    }
                }else page = "logFail";
            } else if ("Renew".equals(page)){
                data = (ArrayList)renew(request);
                request.setAttribute("data", data);                
            } else if ("search".equals(page)){
                
            } else if ("searchAuthor".equals(page)){
                String name = request.getParameter("name");
                System.out.println(name);
                data = (ArrayList) mbm.searchBookByAuthor(name);
                request.setAttribute("data",data);
                if (data==null){
                    page = "searchError";
                }
            } else if ("searchTitle".equals(page)){
                String title = request.getParameter("title");
                System.out.println(title);
                data = (ArrayList)mbm.searchBookByTitle(title);
                request.setAttribute("data",data);
                if (data==null)
                    page = "searchError";
            } else if ("ViewReservations".equals(page)){
                data = (ArrayList)rm.viewReservation(mem.getId());
                request.setAttribute("data",data);
            } else if ("re".equals(page)){
                data = (ArrayList) reserve(request);
                request.setAttribute("data", data);
            } else if ("delete".equals(page)){
                delete(request);
            } else if ("logFail".equals(page)){

            }

            dispatcher = servletContext.getNamedDispatcher(page);
            if (dispatcher == null) {
                dispatcher = servletContext.getNamedDispatcher("Error");
            }
            dispatcher.forward(request, response);

        } catch (Exception e) {
            log("Exception in TLSServlet.processRequest()");
        }
    }

    protected ArrayList reserve(HttpServletRequest request){
        ArrayList<ReservationState> al = new ArrayList();
        String[] books = request.getParameterValues("bookId");
        for (int i=0; i<books.length; i++){
            System.out.println(books[i]);
            ReservationState rs = rm.makeReservation(mem.getId(), Long.parseLong(books[i]));
            al.add(rs);
        }
        return al;
    }

    protected void delete(HttpServletRequest request){
        String[] books = request.getParameterValues("reId");
        for (int i=0; i<books.length; i++){
            rm.delete(Long.parseLong(books[i]));
        }
    }

    protected ArrayList renew(HttpServletRequest request) {
        ArrayList<BookState> al = new ArrayList();
        String[] books = request.getParameterValues("bookId");
        for (int i=0; i<books.length;i++){             
                BookState b = mbm.findBook(Long.parseLong(books[i]));
                b.setRenew(mbm.extendLoan(mem.getId(), Long.parseLong(books[i])));
                al.add(b);            
        }
        return al;
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("TLSServlet: doGet()");
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("TLSServlet: doPost()");
        processRequest(request, response);
    }

    public void destroy() {
        System.out.println("TLSServlet: destroy()");
    }
}
