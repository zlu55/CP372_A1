import java.io.* ;
import java.net.* ;
import java.util.* ;

public class server {
    public static void main(String argv[]) throws Exception{
        int port;

        //Get Port value
        if(argv.length == 0){
            port = 3000;//default port set to 3000 if no port entered
        }
        else{
            port = Integer.parseInt(argv[0]);
        }

        ArrayList<book> bookList = new ArrayList<book>();
        ServerSocket socket = new ServerSocket(port);

        System.out.println("Server starting.");

        while(true){
            Socket connection = socket.accept();
            biblioThread serverThread = new biblioThread(Thread.activeCount() + "", connection, bookList);
            serverThread.start();
            System.out.println("Connection Granted.");
        }
    }
}
