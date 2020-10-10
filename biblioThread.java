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
        int addBook = 2;
        int yr = Integer.parseInt(data[5].trim());
        if((data[6].trim()).equals("ALL")){
            for(book b : bookList){
                outputBook = outputBook + b.toString() + "\n";
            }
        }else{
            for(book b : bookList){
                if(!(data[1].trim().equals(""))){
                    if(b.getISBN().equals(data[1].trim())){
                        addBook = 1;
                    }else{
                        addBook = 0;
                    }
                }else if(!(data[2].trim()).equals("")){
                    if(b.getTitle().equals((data[2].trim()))){
                        if(addBook != 0){
                           addBook = 1; 
                        } 
                    }else{
                        addBook = 0;
                    }
                }else if(!(data[3].trim()).equals("")){
                    if(b.getAuthor().equals((data[3].trim()))){
                        if(addBook != 0){
                           addBook = 1; 
                        } 
                    }else{
                        addBook = 0;
                    }

                }else if(!(data[4].trim()).equals("")){
                    if(b.getPublisher().equals((data[4].trim()))){
                        if(addBook != 0){
                           addBook = 1; 
                        } 
                    }else{
                        addBook = 0;
                    }
                }else if(yr != 0){
                    if(b.getYear() == yr){
                        if(addBook != 0){
                           addBook = 1; 
                        } 
                    }else{
                        addBook = 0;
                    }
                }else{
                   outputBook = "Sorry, No books exist in the Library with those attributes."; 
                }

                if(addBook == 1){
                    outputBook = outputBook + b.toString() + "\n";
                }
            }
            //     if(!(data[1].trim()).equals("") && b.getISBN().equals(data[1].trim())){
            //         if((data[2].trim()).equals("") || b.getTitle().equals((data[2].trim()))){
            //             if((data[3].trim()).equals("") || b.getAuthor().equals((data[3].trim()))){
            //                 if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
            //                     if(yr == 0 || b.getYear() == yr){
            //                         outputBook = outputBook + b.toString() + "\n";
            //                     }
            //                 }
            //             }
            //         }
            //     }else if(!(data[2].trim()).equals("") && b.getTitle().equals(data[2].trim())){
            //         if((data[3].trim()).equals("") || b.getAuthor().equals((data[3].trim()))){
            //             if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
            //                 if(yr == 0 || b.getYear() == yr){
            //                     outputBook = outputBook + b.toString() + "\n";
            //                 }
            //             }
            //         }
            //     }else if(!(data[3].trim()).equals("") && b.getAuthor().equals(data[3].trim())){
            //         if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
            //             if(yr == 0 || b.getYear() == yr){
            //                 outputBook = outputBook + b.toString() + "\n";
            //             }
            //         }
            //     }else if(!(data[4].trim()).equals("") && b.getPublisher().equals(data[4].trim())){
            //         if(yr == 0 || b.getYear() == yr){
            //             outputBook = outputBook + b.toString() + "\n";
            //         }
            //     }else if(yr != 0 && b.getYear() == (yr)){
            //         outputBook = outputBook + b.toString() + "\n";
            //     }else{
            //         return "Sorry, No books exist in the Library with those attributes.";
            //     }
            // }
        }
        return outputBook;
    }
	
	public String updateBook(String[] data){
		String updatedBook = "";
		if(checkISBN(data[1].trim())){
			for(book b : bookList){
				if(!(data[1].trim()).equals("") && b.getISBN().equals((data[1].trim()))){
					System.out.println(b.toString());
					if(!(data[2].trim()).equals("") && !b.getTitle().equals((data[2].trim()))){
						b.setTitle(data[2].trim());
						System.out.println("New title: "+data[2]);
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
					updatedBook = b.toString();
					break;
				}
			}
			return "Book updated\n" + updatedBook;
		}else{
			return "Sorry, that ISBN is not in the library";
		}
	}
	
    private String removeBook(String[] data) {
        String output = "";
        int deleted = 0;
        int yr = Integer.parseInt(data[5].trim());
        ArrayList<book> toRemove = new ArrayList<book>();
        if((data[6].trim()).equals("ALL")){
            for(book b : bookList){
                toRemove.add(b);  
                deleted++; 
            } 
        }else{
            for(book b : bookList){
                if(!(data[1].trim()).equals("") && b.getISBN().equals(data[1].trim())){
                    if((data[2].trim()).equals("") || b.getTitle().equals((data[2].trim()))){
                        if((data[3].trim()).equals("") || b.getAuthor().equals((data[3].trim()))){
                            if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
                                if(yr == 0 || b.getYear() == yr){
                                    toRemove.add(b);  
                                    deleted++; 
                                }
                            }
                        }
                    }
                }else if(!(data[2].trim()).equals("") && b.getTitle().equals(data[2].trim())){
                    if((data[3].trim()).equals("") || b.getAuthor().equals((data[3].trim()))){
                        if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
                            if(yr == 0 || b.getYear() == yr){
                                toRemove.add(b);  
                                deleted++; 
                            }
                        }
                    }
                }else if(!(data[3].trim()).equals("") && b.getAuthor().equals(data[3].trim())){
                    if((data[4].trim()).equals("") || b.getPublisher().equals((data[4].trim()))){
                        if(yr == 0 || b.getYear() == yr){
                            toRemove.add(b);  
                            deleted++; 
                        }
                    }
                }else if(!(data[4].trim()).equals("") && b.getPublisher().equals(data[4].trim())){
                    if(yr == 0 || b.getYear() == yr){
                        toRemove.add(b);  
                        deleted++; 
                    }
                }else if(yr != 0 && b.getYear() == (yr)){
                    toRemove.add(b);  
                    deleted++; 
                }else{
                    output = "Sorry, No books exist in the Library with those attributes.";
                }
            }
        }

        bookList.removeAll(toRemove);

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

}
