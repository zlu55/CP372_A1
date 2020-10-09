import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ConnectionRequest extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
	
	public boolean newConnect(String IPAddress, int portNum) throws Exception{
		socket = new Socket();
		try{
			socket.connect(new InetSocketAddress(IPAddress, portNum), 3000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}

    
    public void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
        System.out.println("Disconnected from server.");
    }

    public boolean isConnected(){
		return true;
	}
	

    public void clientRequest(String req) {
        /*
        switch(req){
            case "submit":
				submitBook();
				break;
			case "get":
				getBook();
				break;
            case "update":
				updateBook();
				break;
			case "remove":
				removeBook();
				break;
        }*/
    }
	
}