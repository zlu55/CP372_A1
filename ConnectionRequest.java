import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ConnectionRequest extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

	public void ConnectionRequest(){
		
	}
	
	public void connect(String IPAddress, int portNum) throws Exception{
		socket = new Socket();
		try{
			socket.connect(new InetAddress(IPAddress, portNum), 3000);
		}catch(Exception e){
			System.out.println(e);
		}
	}

    

    private void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
        System.out.println("Disconnected from server.");
        this.interrupt();
    }

    private void listen() {
        String line, input, output;

        try{
            line = in.readLine();
            while (line != null){
                input = "";

                if (line.equals("in")) {
                    output = "out";
                }else{
                    while (!line.contains("\\EOF")){
                        input = input.concat(line + "\r\n");
                        line = in.readLine();
                    }
                    
                    output = clientRequest(input.split("\n")).trim() + "\r\n\\EOF";
                }

                out.println(output);
                line = in.readLine();
            }
        } 
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private String clientRequest(String[] data) {
        final String request = data[0].trim();
        /*
        if(request == "SUBMIT"){
            return //submitfunction
        }
        if(request == "GET"){
            return //getfunction
        }
		if(request == "UPDATE"){
            return //updatefunction
        }
		if(request == "REMOVE"){
            return //removefunction
        }else{
            return "ERROR: Invalid request(Valid Requests: SUBMIT, GET, UPDATE, REMOVE)";
        }*/
		return "";
    }

}