package db;

public class DBIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private DBIntegrityException(String msg) {
		super(msg);
	}
}
