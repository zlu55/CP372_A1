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
        String outputBook = "";
        int yr = Integer.parseInt(data[5].trim());
        if(checkISBN(data[1].trim())){
            for(book b : bookList){
                outputBook = b.toString();
            }
        }else{
            for(book b : bookList){
                if(b.getTitle().equals(data[2].trim())){
                    if((data[3].trim()).equals("") || b.getAuthor().equals((data[3].trim()))){
                        if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
                            if(yr == 0 || b.getYear() == yr){
                                outputBook = outputBook + b.toString() + "\n";
                            }
                        }
                    }
                }else if(b.getAuthor().equals(data[3].trim())){
                    if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
                        if(yr == 0 || b.getYear() == yr){
                            outputBook = outputBook + b.toString() + "\n";
                        }
                    }
                }else if(b.getPublisher().equals(data[4].trim())){
                    if(yr == 0 || b.getYear() == yr){
                        outputBook = outputBook + b.toString() + "\n";
                    }
                }else if(b.getYear().equals(yr)){
                    outputBook = outputBook + b.toString() + "\n";
                }else{
                    return "Sorry, No books exist in the Library with those attributes.";
                }
            }
        }
            return outputBook;
    }

	
	public String updateBook(String[] data){
		String updatedBook = "";
		if(checkISBN(data[1].trim())){
			for(book b : bookList){
				if(checkISBN(data[1].trim())){
					if(!(data[2].trim()).equals("") && !b.getTitle().equals((data[2].trim()))){
						b.setTitle(data[2].trim());
					}
					if(!(data[3].trim()).equals("") && !b.getAuthor().equals((data[3].trim()))){
						b.setAuthor(data[3].trim());
					}
					if(!(data[4].trim()).equals("") && !b.getPublisher().equals((data[4].trim()))){
						b.setPublisher(data[4].trim());
					}
					int yr = Integer.parseInt(data[5].trim());
					if(yr != 0 && b.getYear() != yr){
						b.setYear(yr);
					}
				}
				updatedBook = b.toString();
			}
			return "Book updated\n" + updatedBook;
		}else{
			return "Sorry, that ISBN is not in the library";
		}
	}
	
    private String removeBook(String[] data) {
        String output = "Book removed";
        int deleted = 0;
        //ArrayList<ArrayList<book>> allBookList = new ArrayList<>();
        
		
		
		
		
		
		
		
		
		
		/*for (String input : data) {
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
            }*/
        output = deleted + " book(s) removed";
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

	
    public static ArrayList<book> findByAttribute(ArrayList<book> allBooks, String attribute, String value) {
        ArrayList<book> foundSet = new ArrayList<>();
        /*for (book newBook : allBooks) {
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
            return null;*/
        return foundSet;
    }

    public static ArrayList<book> search(ArrayList<ArrayList<book>> allBookList) {
        ArrayList<book> search = null;
        for (ArrayList<book> bookList : allBookList) {
            search = search == null ? bookList : search;
            if (search == null) break;
            if (bookList != null)
                search.retainAll(bookList);
            else {
                search = null;
                break;
            }
        }
        return search;
    }
}
