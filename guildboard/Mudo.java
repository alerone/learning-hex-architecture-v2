import java.net.ServerSocket;
import java.net.Socket;

/**
 * Mudo
 */
public class Mudo {

    public static void main(String[] args) throws Exception {
        try(ServerSocket server = new ServerSocket(8082)) {
            System.out.println("escuchando en el puerto 8082 sin contestar");
            while(true) {
                Socket connection = server.accept();
                Thread.sleep(30_000);
                connection.close();
            }
        }
    }
}
