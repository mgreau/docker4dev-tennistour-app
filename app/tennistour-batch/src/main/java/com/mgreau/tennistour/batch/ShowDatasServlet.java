package com.mgreau.tennistour.batch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mgreau.tennistour.entities.Player;
import com.mgreau.tennistour.entities.PlayerBean;
import com.mgreau.tennistour.entities.Tournament;
import com.mgreau.tennistour.entities.TournamentBean;

@WebServlet(urlPatterns = {"/list"})
public class ShowDatasServlet extends HttpServlet {

  @Inject
  PlayerBean pBean;

  @Inject
  TournamentBean tBean;

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<html>");
      out.println("<head>");
      out.println("<title>All Players</title>");
      out.println("</head>");
      out.println("<body><a href=\"./\">Home</a>");

      out.println("<h1>All Tournament " + request.getContextPath() + "</h1>");
      out.println("<ul>");
      List<Tournament> ts = tBean.listTournaments();
      if (ts == null)
	            out.println("<li>No tournament or Error!!</li>");
            else
            	for (Tournament t : ts){
            		out.println("<li>"+t.getName()+" "+t.getYear()+" ");
        			out.println("</li>");
            	}
            out.println("</ul>");
            
            
            out.println("<h1>All players " + request.getContextPath() + "</h1>");
          
            List<Player> players = pBean.listPlayers();
            if (players == null)
	            out.println("<h2>No player or Error!!</h2>");
            else{
            	out.println("<h2>"+players.size()+" players!</h2>");
            
            out.println("<h2> MENS :</h2>");
            out.println("<ul>");
            List<Player> mens = pBean.listMenPlayers();
            	for (Player m : mens){
            		out.println("<li>"+m.getName());
        			out.println("</li>");
            	}
            out.println("</ul>");
            
            out.println("<h2> WOMENS :</h2>");
            out.println("<ul>");
            List<Player> women = pBean.listWomenPlayers();
            	for (Player m : women){
            		out.println("<li>"+m.getName());
        			out.println("</li>");
            	}
            out.println("</ul>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (JobStartException | JobSecurityException ex) {
            Logger.getLogger(ShowDatasServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
