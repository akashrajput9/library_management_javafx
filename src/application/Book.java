package application;

public class Book extends Library{
	private String bookName;
	private int stock;
	private String authorName;
	

	@Override
	public void addBook(String bookName, int stock) {
		// TODO Auto-generated method stub
		this.bookName = bookName;
		this.stock = stock;
	}

	@Override
	public int getStock() {
		// TODO Auto-generated method stub
		return this.stock;
	}

	@Override
	public String getBookName() {
		// TODO Auto-generated method stub
		return this.bookName;
	}


	
	
}
