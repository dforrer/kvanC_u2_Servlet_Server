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

import ch.fhnw.kvan.chat.general.ChatRoom;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
// http://localhost:8080/kvanC_u2_Servlet_Server/Server
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Server() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ChatRoom cr = ChatRoom.getInstance();

		String query = request.getQueryString();
		System.out.println("query:" + query);

		// ----------------------------------
		// Parsing Query Params

		/*
		 * "?action=addParticipant & name=<Name>"
		 * "?action=removeParticipant & name=<Name>"
		 * "?action=addTopic & topic=<Thema>"
		 * "?action=removeTopic & topic=<Thema>"
		 * "?action=postMessage & message=<Nachricht> & topic=<Thema>"
		 * ￼"?action=getMessages & topic=<Thema>"
		 * "?action=refresh & topic=<Thema>"
		 */

		String action = null;
		String name = null;
		String topic = null;
		String message = null;

		Map<String, String[]> queryMap = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : queryMap.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue()[0]);
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
		 * ￼"?action=getMessages & topic=<Thema>"
		 * "?action=refresh & topic=<Thema>"
		 */
		switch (action) {
		case "addParticipant":
			cr.addParticipant(name); // Add client to model
			break;
		case "removeParticipant":
			cr.removeParticipant(name); // Remove client from model
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
		case "getMessages":
			// MIME-Typ der Antwort bestimmen
			response.setContentType("text/html");

			// Writer holen
			PrintWriter out = response.getWriter();

			// Messages ausgeben
			out.println(cr.getMessages(topic));

			break;
		case "refresh":
			// MIME-Typ der Antwort bestimmen
			response.setContentType("text/html");

			// Writer holen
			PrintWriter out2 = response.getWriter();

			// Messages ausgeben
			out2.println(cr.getMessages(topic));
			out2.println(cr.getParticipants());
			out2.println(cr.getTopics());

			break;
		default:
			response.setStatus(404);
		}
/*
		// ----------------------------------
		// Output Response

		// MIME-Typ der Antwort bestimmen
		response.setContentType("text/html");

		// Writer holen
		PrintWriter out = response.getWriter();

		// HTML-Seite ausgeben
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Test</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h1>Hello World_5f_Testen ist schön... <br /><span style=\"color:red;\">Fehler suchen noch schöner.. *g*</span></h1>");
		out.println("</body>");
		out.println("</html>");
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
