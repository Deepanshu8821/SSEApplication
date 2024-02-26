

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProgressEventServlet", value = "/progressEvents")
public class ProgressEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String processId = req.getParameter("processId");
        if (processId != null) {
            SSEListeners.addListener(resp, processId);

            // Keep the connection open
            while (!resp.isCommitted()) {
                try {
                    Thread.sleep(2000); // Adjust as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            SSEListeners.removeListener(resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ProcessId header is required.");
        }
    }
}
