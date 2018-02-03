

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet(urlPatterns = {"/Start","/Startup", "/Startup/YorkBank"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String startPage="/UI.jspx";
	String resultPage="/Result.jspx";
	private static final String PRINCIPLE = "principle";
	private static final String INTEREST = "interest";
	private static final String PPERIOD = "pPeriod";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("calculate")==null)
			   request.getRequestDispatcher(startPage).forward(request,response);
		else
		{
			//request.getRequestDispatcher(resultPage).forward(request, response);
			response.setContentType("text/plain");
			Writer resOut = response.getWriter();
			
			String interest = request.getParameter("interest");
			String period = request.getParameter("pPeriod");
			String principle = request.getParameter("principle");			
			String gracePeriod = request.getParameter("gPeriod");
			Double inter;
			Double pPeriod;
			Double prin;
			Double total;
			Double gPeriod;
			Double graceInterest;
			Double fixedInterest = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
			Double monthlyPayments;

			if(gracePeriod!=null)//IF TEXT BOX FOR GRACE PERIOD IS CHECKED, DO THIS
				gPeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
			else// else do this
				gPeriod = 0.0;
			if(principle != null)
				prin = Double.parseDouble(principle);
			else
				prin = Double.parseDouble(this.getServletContext().getInitParameter("principle"));
			if(interest != null)
				inter = Double.parseDouble(interest);
			else
				inter = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
			if(period != null)
				pPeriod = Double.parseDouble(period);
			else
				pPeriod = Double.parseDouble(this.getServletContext().getInitParameter("period"));
			
			graceInterest = prin * (((inter + fixedInterest)*0.01)/12) * gPeriod;
			monthlyPayments = (((inter+fixedInterest)*0.01)/12) * prin/(1-(Math.pow(1+(((inter+fixedInterest)*0.01)/12), (-1)*pPeriod)));
			if(gracePeriod!=null)
				total = monthlyPayments + (graceInterest/gPeriod);
			else
				total = monthlyPayments;
			DecimalFormat df = new DecimalFormat("#.####");
			df.setMaximumFractionDigits(1);
			request.setAttribute(PRINCIPLE,df.format(total));
			request.setAttribute(INTEREST,df.format(graceInterest));
			request.getRequestDispatcher(resultPage).forward(request,response);
			//resOut.write("your monthly payment is: " + df.format(total) + "\n");
			

		}		
	}
//	public static double round(double value, int places) {
//	    if (places < 0) throw new IllegalArgumentException();
//
//	    long factor = (long) Math.pow(10, places);
//	    value = value * factor;
//	    long tmp = Math.round(value);
//	    return (double) tmp / factor;
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
