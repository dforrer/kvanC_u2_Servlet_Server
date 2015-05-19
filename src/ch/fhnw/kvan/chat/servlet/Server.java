package ch.fhnw.kvan.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.general.ChatRoom;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
// http://localhost:8080/kvanC_u2_Servlet_Server/Server
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ChatRoom cr;
	private static Logger logger;


	/**
	 * Default constructor.
	 */
	public Server() {
		logger = Logger.getLogger(Server.class);
		logger.info("Server-Constructor called.");
		cr = ChatRoom.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String query = request.getQueryString();
		logger.info("query:" + query);

		// ----------------------------------
		// Parsing Query Params

		/*
		 * "?action=addParticipant & name=<Name>"
		 * "?action=removeParticipant & name=<Name>"
		 * "?action=addTopic & topic=<Thema>"
		 * "?action=removeTopic & topic=<Thema>"
		 * "?action=postMessage & message=<Nachricht> & topic=<Thema>"
		 * ￼"?action=getMessages & topic=<Thema>" ￼"?action=getTopics"
		 * "?action=refresh & topic=<Thema>"
		 */

		String action = null;
		String name = null;
		String topic = null;
		String message = null;

		Map<String, String[]> queryMap = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : queryMap.entrySet()) {
			logger.info(entry.getKey() + " = " + entry.getValue()[0]);
			switch (entry.getKey()) {
			case "action":
				action = entry.getValue()[0];
				break;
			case "name":
				name = entry.getValue()[0];
				break;
			case "topic":
				topic = entry.getValue()[0];
				break;
			case "message":
				message = entry.getValue()[0];
				break;
			default:
				response.setStatus(404);
				return;
			}
		}

		/*
		 * "?action=addParticipant & name=<Name>"
		 * "?action=removeParticipant & name=<Name>"
		 * "?action=addTopic & topic=<Thema>"
		 * "?action=removeTopic & topic=<Thema>"
		 * "?action=postMessage & message=<Nachricht> & topic=<Thema>"
		 * ￼"?action=getMessages & topic=<Thema>" ￼"?action=getTopics"
		 * "?action=getParticipants"
		 */
		
		// MIME-Typ der Antwort bestimmen
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		boolean rv;

		switch (action) {
		case "addParticipant":
			rv = cr.addParticipant(name); // Add client to model
			logger.info("addParticipant worked: " + rv);
			break;
		case "removeParticipant":
			rv = cr.removeParticipant(name); // Remove client from model
			logger.info("removeParticipant worked: " + rv);
			break;
		case "addTopic":
			cr.addTopic(topic); // add topic to chatroom-model
			break;
		case "removeTopic":
			cr.removeTopic(topic); // remove topic from chatroom-model
			break;
		case "postMessage":
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			message = dateFormat.format(new Date()) + " " + message;
			cr.addMessage(topic, message);
			break;
		case "getMessages": {
			// response-format: "messages=Meldung1;Meldung2;"

			
			// Writer holen
			PrintWriter out = response.getWriter();

			// Messages ausgeben
			out.println(cr.getMessages(topic));

			break;
		}
		case "getTopics": {
			// MIME-Typ der Antwort bestimmen
			response.setContentType("text/html");

			// Writer holen
			PrintWriter out = response.getWriter();

			// Messages ausgeben
			out.println(cr.getTopics());

			break;
		}
		case "getParticipants": {
			// MIME-Typ der Antwort bestimmen
			response.setContentType("text/html");

			// Writer holen
			PrintWriter out = response.getWriter();

			// Messages ausgeben
			out.println(cr.getParticipants());

			break;
		}
		default:
			response.setStatus(404);
		}
		/*
		 * // ---------------------------------- // Output Response
		 * 
		 * // MIME-Typ der Antwort bestimmen
		 * response.setContentType("text/html");
		 * 
		 * // Writer holen PrintWriter out = response.getWriter();
		 * 
		 * // HTML-Seite ausgeben out.println("<html>"); out.println("<head>");
		 * out.println("<title>Test</title>"); out.println("</head>");
		 * out.println("<body bgcolor=\"white\">"); out.println(
		 * "<h1>Hello World_5f_Testen ist schön... <br /><span style=\"color:red;\">Fehler suchen noch schöner.. *g*</span></h1>"
		 * ); out.println("</body>"); out.println("</html>");
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
