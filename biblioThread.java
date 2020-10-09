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
		
	}
	
    private String remove(String[] data) {
        String output;
        int deleted = 0;

        ArrayList<ArrayList<book>> bookList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] clientIn = line.split(" ");
            String key = line.substring(clientIn[0].length()).trim();
            if(clientIn[0] == "ISBN" || clientIn[0] == "TITLE" || clientIn[0] == "AUTHOR"|| clientIn[0] == "PUBLISHER" || clientIn[0] == "YEAR" )
                if (value.length() > 0){
                   bookList.add(Util.findByAttribute(bookList, clientIn[0], key)); 
                }
        }
        ArrayList<book> intersection = Util.intersection(bookList);
        if (intersection != null)
            for (book book : intersection) {
                bookEntries.remove(book);
                deleted++;
            }

        output = deleted + " book(s) removed";
        return output;

    }

    private String clientRequest(String[] data) {
        final String request = data[0].trim();
        /*
        if(request == "SUBMIT"){
            return //submitfunction
        }
        else if(request == "GET"){
            return //getfunction
        }
		else if(request == "UPDATE"){
            return //updatefunction
        }
		else if(request == "REMOVE"){
            return //removefunction
        }else{
            return "ERROR: Invalid request(Valid Requests: SUBMIT, GET, UPDATE, REMOVE)";
        }*/
		return "";
    }
	
    public static ArrayList<BookEntry> findByAttribute(ArrayList<BookEntry> bookEntries, String attribute, String value) {
        ArrayList<BookEntry> foundSet = new ArrayList<>();
        for (BookEntry bookEntry : bookEntries) {
            switch (attribute) {
                case "ISBN":
                    if (bookEntry.getISBN().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "TITLE":
                    if (bookEntry.getTITLE().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "AUTHOR":
                    if (bookEntry.getAUTHOR().equals(value))
                        foundSet.add(bookEntry);
                    break;
                case "PUBLISHER":
                    if (bookEntry.getPUBLISHER().equals(value))
                        foundSet.add(bookEntry);
                case "YEAR":
                    if (Integer.toString(bookEntry.getYEAR()).equals(value))
                        foundSet.add(bookEntry);
                default:
                    break;
            }
        }
        if (foundSet.size() == 0)
            return null;
        return foundSet;
    }
}

