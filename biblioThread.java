import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class biblioThread extends Thread{
	private Socket socket;
    private ArrayList<book> bookList;
    private BufferedReader in;
    private PrintWriter out;
	
	public biblioThread(String client, Socket socket, ArrayList<book> bookList){
        super(client);
        this.socket = socket;
        this.bookList = bookList;
    }
	
	public synchronized void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            listen();
            //disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void listen() {
        String line, input, output;

        try{
            line = in.readLine();
            while (line != null){
                input = "";
                if (line.equals("Testing")) {
                    output = "123";
                }else{
                    while (!line.contains("/END/")){
                        input = input.concat(line + "\r\n");
                        line = in.readLine();
                    }
                    output = dataReturn(input.split("\n")).trim() + "\r\n/END/";
                }
                out.println(output);
                line = in.readLine();
            }
        } 
        catch (IOException exception){
            exception.printStackTrace();
        }
    }
	
	public String dataReturn(String[] data){
		switch(data[0].trim()){
			case "SUBMIT":
				return submitBook(data);
			case "GET":
				return getBook(data);
			case "UPDATE":
				return updateBook(data);
			case "REMOVE":
				return removeBook(data);
			default:
				return "Error returning data";
		}
		
	}
	
	public void disconnect() throws IOException{
		out.close();
		in.close();
		socket.close();
	}
	
	public String submitBook(String[] data){
		if(!checkISBN(data[1].trim())){
			int yr = Integer.parseInt(data[5].trim());
			book newBook = new book(data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), yr);
			bookList.add(newBook);
			return "Book Submitted\n" + newBook.toString();
		}else{
			return "Sorry. That ISBN is already in the library";
		}
		
	}
	
	public String getBook(String[] data){
		return "Book gotten";
	}
	
	public String updateBook(String[] data){
		for(book b : bookList){
			if(checkISBN(data[1].trim())){
				if(b.getTitle().equals("")){
					b.setTitle(data[2].trim());
				}
			}
		}
		return "Book updated";
	}
	
	
    private String removeBook(String[] data) {
        String output = "Book removed";
        /*int deleted = 0;

        ArrayList<ArrayList<book>> bookList = new ArrayList<>();
        for (String line : data) {
            line = line.trim();
            String[] clientIn = line.split(" ");
            String key = line.substring(clientIn[0].length()).trim();
            if(clientIn[0] == "ISBN" || clientIn[0] == "TITLE" || clientIn[0] == "AUTHOR"|| clientIn[0] == "PUBLISHER" || clientIn[0] == "YEAR" )
                if (value.length() > 0){
                   bookList.add(findByAttribute(bookList, clientIn[0], key)); 
                }
        }
        ArrayList<book> intersection = intersection(bookList);
        if (intersection != null)
            for (book book : intersection) {
                bookEntries.remove(book);
                deleted++;
            }

        output = deleted + " book(s) removed";*/
        return output;

    }
	
	public boolean checkISBN(String ISBN){
		for(book b : bookList){
			if(b.getISBN().equals(ISBN)){
				return true;
			}
		}
		return false;
	}

	/*
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
    }*/
}

