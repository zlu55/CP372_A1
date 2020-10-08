public class book{
	private String ISBN = "";
	private String title = "";
	private String author = "";
	private String publisher = "";
	private int year = 0;
	
	public String getISBN(){
		return ISBN;
	}
	
	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public String getPublisher(){
		return publisher;
	}
	
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	@Override
	public String toString(){
		return "ISBN: " + ISBN +
				"/nTitle: " + title +
				"/nAuthor: " + author +
				"/nPublisher: " + publisher +
				"/nYear: " + year + "n";
	}
}
