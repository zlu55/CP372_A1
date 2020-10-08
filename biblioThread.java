import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class biblioThread extends Thread{
	private Socket socket;
    private ArrayList<book> newBook;
    private BufferedReader in;
    private PrintWriter out;
	
	public biblioThread(String client, Socket socket, ArrayList<book> newBook){
        super(client);
        this.socket = socket;
        this.newBook = newBook;
    }
	
	public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            //listen();
            //disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void listen(){
		
	}
	
	public void disconnect(){
		in.close();
		out.close();
		socket.close();
	}
	
	
}

