import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server {
    public static void main(String argv[]) throws Exception{
        int port;

        //Get Port value
        if(argv.length == 0)
        {
            port = 3000;//default port set to 3000 if no port entered
        }
        else
        {
            port = Integer.parseInt(argv[0]);
        }

        ArrayList<book> newBook = new ArrayList<book>();
        ServerSocket socket = new ServerSocket(port);

        System.out.println("Server starting.");

        while(true) 
        {
            Socket connection = socket.accept();
            ConnectionRequest serverThread = new ConnectionRequest(Thread.activeCount() + "", connection, newBook);
            serverThread.start();
            System.out.println("Connection Granted.");
        }
    }
}