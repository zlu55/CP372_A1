import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class connectionRequest extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
	
	public void newConnect(String IPAddress, int portNum) throws Exception{
		socket = new Socket();
		try{
			socket.connect(new InetSocketAddress(IPAddress, portNum), 3000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}catch(Exception e){
			System.out.println(e);
		}
	}

    
    public void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
        System.out.println("Disconnected from server.");
    }

    public void listen() {
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

}