import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ConnectionThread extends Thread {
    private final Socket socket;
    private final ArrayList<book> newBook;
    private BufferReader in;
    private PrintWriter out;

    public ConnectionThread(String client, Socket socket, ArrayList<book> newBook){
        super(client);
        this.socket = socket;
        this.newBook = newBook;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            listen();
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
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

        try 
        {
            line = in.readLine();
            while (line != null) 
            {
                input = "";

                if (line.equals("in")) {
                    output = "out";
                }
                else 
                {
                    while (!line.contains("\\EOF")) 
                    {
                        input = input.concat(line + "\r\n");
                        line = in.readLine();
                    }
                    
                    output = clientRequest(input.split("\n")).trim() + "\r\n\\EOF";
                }

                out.println(output);
                line = in.readLine();
            }
        } 
        catch (IOException exception) 
        {
            exception.printStackTrace();
        }
    }

    private String clientRequest(String[] data) {
        final String request = data[0].trim();
        
        if(request == "SUBMIT") 
        {
            return //submitfunction
        }
        if(request == "GET")
        {
            return //getfunction
        }
        if(request == "UPDATE")
        {
            return //updatefunction
        }
        if(request == "REMOVE")
        {
            return //removefunction
        }
        else
        {
            return "ERROR: Invalid request(Valid Requests: SUBMIT, GET, UPDATE, REMOVE)";
        }
    }

}