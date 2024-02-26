
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProgressUpdater {

    private static Map<String, Integer> progressMap = new HashMap<>();
    private static Map<String, Boolean> completeMap = new HashMap<>();

    public static void sendProgress(String processId, int progress, HttpServletResponse response) {
        progressMap.put(processId, progress);
         notifyClients(processId, progress);
    }

    public static int getProgress(String processId) {
        return progressMap.getOrDefault(processId, 0);
    }

    public static void sendComplete(String processId) {
        completeMap.put(processId, true);
        System.out.println("sendComplete : " + processId);
        notifyClients(processId, 100); // Notify clients that process is complete
    }


    private static void notifyClients(String processId, int progress) {
        System.out.println("progress 0 : " + progress);

        for (HttpServletResponse response : SSEListeners.getListeners(processId)) {
//            System.out.println("progress 1 : " + progress);

            try {
                System.out.println("progress 2 : " + progress);
                response.getWriter().write("data: {\"progress\": " + progress + "}\n\n");
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
