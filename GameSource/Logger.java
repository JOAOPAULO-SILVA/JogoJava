import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static boolean modoSilencioso = false;

    private Logger() {}

    public static void setModoSilencioso(boolean silencioso) {
        modoSilencioso = silencioso;
    }

    public static void log(String message) {
        String timestamp = LocalTime.now().format(timeFormatter);
        String logMessage = String.format("[%s] %s", timestamp, message);

        if (!modoSilencioso) {
            System.out.println(message);
        }
        logs.add(logMessage);
    }

    public static void exibirLogs() {
        System.out.println("\n--- REGISTRO DE EVENTOS DO JOGO ---");
        if (logs.isEmpty()) {
            System.out.println("Nenhum evento foi registrado.");
        } else {
            for (String logMessage : logs) {
                System.out.println(logMessage);
            }
        }
        System.out.println("-----------------------------------");
    }

    public static void limparLogs() {
        logs.clear();
        modoSilencioso = false;
    }
}