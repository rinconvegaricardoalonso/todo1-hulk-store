package main.java.com.todo1.hulkstore.exception;

@SuppressWarnings("serial")
public class HulkStoreException extends Exception{
	
	public HulkStoreException() {
		super();
	}
	
	public HulkStoreException(String message) {
		super(message);
	}
	
	public HulkStoreException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public HulkStoreException(Throwable cause) {
		super(cause);
	}

}
