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
		if(checkISBN(data[1].trim()) == false){
			int yr = Integer.parseInt(data[5].trim());
			book newBook = new book(data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), yr);
			bookList.add(newBook);
			return "Book Submitted\n" + newBook.toString();
		}else{
			return "Sorry. That ISBN is already in the library";
		}
		
	}
	
	public String getBook(String[] data){
        StringBuilder output = new StringBuilder();
        ArrayList<ArrayList<book>> allBookList = new ArrayList<>();

        for (String input : data) {
            input = input.trim();
            String[] clientIn = input.split(" ");
            String key = input.substring(clientIn[0].length()).trim();
            
            if(clientIn[0] == "ISBN" || clientIn[0] == "TITLE" || clientIn[0] == "AUTHOR"|| clientIn[0] == "PUBLISHER" || clientIn[0] == "YEAR" ){
                if (key.length() > 0){
                    allBookList.add(findByAttribute(bookList, clientIn[0], key));
                }
            }else if(clientIn[0] == "YEAR" ){
                if (Integer.parseInt(key) > 0){
                    allBookList.add(findByAttribute(bookList, "YEAR", key));
                }
            }else if(clientIn[0] == "ALL"){
                if(bookList.size() == 0){
                    return "Book List empty.";
                }
                for(book newBook : bookList){
                    output.append(newBook.toString());
                    output.append("\r\n");
                }
            }
        }

        ArrayList<book> search = search(allBookList);
        if (search == null)
            return "No books found.";
        for (book newBook : search) {
            output.append(newBook.toString());
            output.append("\r\n");
        }

        return output.toString();
	}
	
	public String updateBook(String[] data){
		return "Book updated";
	}
	
	
    private String removeBook(String[] data) {
        String output = "Book removed";
        /*int deleted = 0;
        ArrayList<ArrayList<book>> allBookList = new ArrayList<>();
        for (String input : data) {
            input = input.trim();
            String[] clientIn = input.split(" ");
            String key = input.substring(clientIn[0].length()).trim();
            if(clientIn[0] == "ISBN" || clientIn[0] == "TITLE" || clientIn[0] == "AUTHOR"|| clientIn[0] == "PUBLISHER"){
                if (value.length() > 0){
                   allBookList.add(findByAttribute(bookList, clientIn[0], key)); 
                }
            }else if(clientIn[0] == "YEAR" ){
                if (Integer.parseInt(value) > 0){
                    allBookList.add(findByAttribute(bookList, "YEAR", value));
                }
            }
        }
        ArrayList<book> search = search(allBookList);
        if (search != null)
            for (book newBook : search) {
                allBooks.remove(book);
                deleted++;
            }
        output = deleted + " book(s) removed";*/
        return output;

    }
	
	public boolean checkISBN(String ISBN){
		for(book b : bookList){
			if(b.getISBN().equals(ISBN)){
				System.out.println("Found dupe");
				return true;
			}
		}
		return false;
	}

	/*
    public static ArrayList<book> findByAttribute(ArrayList<newBook> allBooks, String attribute, String value) {
        ArrayList<book> foundSet = new ArrayList<>();
        for (book newBook : allBooks) {
            switch (attribute) {
                case "ISBN":
                    if (newBook.getISBN().equals(value))
                        foundSet.add(newBook);
                    break;
                case "TITLE":
                    if (newBook.getTITLE().equals(value))
                        foundSet.add(newBook);
                    break;
                case "AUTHOR":
                    if (newBook.getAUTHOR().equals(value))
                        foundSet.add(newBook);
                    break;
                case "PUBLISHER":
                    if (newBook.getPUBLISHER().equals(value))
                        foundSet.add(newBook);
                case "YEAR":
                    if (Integer.toString(newBook.getYEAR()).equals(value))
                        foundSet.add(newBook);
                default:
                    break;
            }
        }
        if (foundSet.size() == 0)
            return null;
        return foundSet;
    }*/
}
