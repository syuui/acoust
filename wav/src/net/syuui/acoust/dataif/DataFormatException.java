package net.syuui.acoust.dataif;

public class DataFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataFormatException() {
		super();
	}
	
	public DataFormatException(String msg) {
		super(msg);
	}
	
	public DataFormatException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public DataFormatException(Throwable cause ){
		super(cause);
	}
}
