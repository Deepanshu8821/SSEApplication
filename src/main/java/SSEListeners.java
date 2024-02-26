
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSEListeners {

    private static final Map<String, List<HttpServletResponse>> listeners = new HashMap<>();

    public static void addListener(HttpServletResponse response, String processId) {

        List<HttpServletResponse> processListeners = listeners.computeIfAbsent(processId, k -> new ArrayList<>());
        processListeners.add(response);

        listeners.put(processId, processListeners);


        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        // Send initial progress
        sendInitialProgress(response, processId);
    }

    public static void removeListener(HttpServletResponse response) {
        listeners.remove(response);
    }

//    public static List<HttpServletResponse> getListeners(String processId) {
//        List<HttpServletResponse> processListeners = new ArrayList<>();
//        for (HttpServletResponse response : listeners) {
//            String id = "process-1";
//            if (id != null && id.equals(processId)) {
//                processListeners.add(response);
//            }
//        }
//        return processListeners;
//    }

    public static synchronized List<HttpServletResponse> getListeners(String processId) {
        List<HttpServletResponse> processListeners = listeners.get(processId);
        if (processListeners != null) {
            return processListeners;
        }
        return new ArrayList<>();
    }

    private static void sendInitialProgress(HttpServletResponse response, String processId) {
        int initialProgress = ProgressUpdater.getProgress(processId);
        try {
            response.getWriter().write("data: {\"progress\": " + initialProgress + "}\n\n");
            System.out.println("1 data: {\"progress\": " + initialProgress + "}");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
