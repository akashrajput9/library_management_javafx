package application;

public class ModelTable {
	public ModelTable(String id, String stock, String bookName, String authorName) {
		super();
		this.id = id;
		this.stock = stock;
		this.bookName = bookName;
		this.authorName = authorName;
	}

	public String id,stock,bookName, authorName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
