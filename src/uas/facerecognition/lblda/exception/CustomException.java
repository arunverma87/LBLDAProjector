/**
 *
 */
package uas.facerecognition.lblda.exception;

/**
 * @author arunv
 *
 */
public class CustomException extends Exception {

	private String message;

	private Exception capturedException;

	/**
	 * @param message
	 * @param capturedException
	 */
	public CustomException(String message, Exception capturedException) {
		this.message = message;
		this.capturedException = capturedException;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the capturedException
	 */
	public Exception getCapturedException() {
		return capturedException;
	}



}
