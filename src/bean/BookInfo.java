package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookInfo {
	
	private String book_id;
	private String book_name;
	private String author;
	private boolean if_in_store;
	private boolean if_lended;
	private String borrower;
	private String lend_date;
	private String return_date;
	private boolean if_exceed;
	
	public BookInfo() {	}
	
	public BookInfo(ResultSet result) {
		try {
			book_id = result.getString(1);
			book_name = result.getString(2);
			author = result.getString(3);
			if_in_store = (result.getInt(4)==1?true:false);
			if_lended = (result.getInt(5)==1?true:false);
			borrower = result.getString(6);
			lend_date = result.getString(7);
			return_date = result.getString(8);
			if_exceed = (result.getInt(9)==1?true:false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isIf_in_store() {
		return if_in_store;
	}

	public void setIf_in_store(boolean if_in_store) {
		this.if_in_store = if_in_store;
	}

	public boolean isIf_lended() {
		return if_lended;
	}

	public void setIf_lended(boolean if_lended) {
		this.if_lended = if_lended;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getLend_date() {
		return lend_date;
	}

	public void setLend_date(String lend_date) {
		this.lend_date = lend_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public boolean isIf_exceed() {
		return if_exceed;
	}

	public void setIf_exceed(boolean if_exceed) {
		this.if_exceed = if_exceed;
	}
}
