
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ProcessServlet", value = "/startProcess")
public class ProcessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Create a new process and start it
        String processId = "process-1"; // You can generate a unique process ID
        System.out.println("Inside getProcess");
        simulateProcess(resp, processId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("doGet Response dispatch");
        resp.getWriter().write("{\"processId\": \"" + processId + "\"}");
    }

    private void simulateProcess(HttpServletResponse response, String processId) {
        // Simulate the process and send progress updates
//        HttpSession session = request.getSession(true);

        // Create a new thread for simulating the process
        Thread processThread = new Thread(() -> {
            for (int i = 0; i <= 100; i += 10) {
                // Simulate progress updates
//                if (i >= 60 && i <= 70) {
//                    ProgressUpdater.sendProgress(processId, 20, response);
//                }
//                else {
//                    ProgressUpdater.sendProgress(processId, i, response);
//                }

                ProgressUpdater.sendProgress(processId, i, response);

                System.out.println("simulateProcess value of i : " + i);
                try {
                    Thread.sleep(2000); // Simulate delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ProgressUpdater.sendComplete(processId);
        });
        processThread.start();
    }
















}
