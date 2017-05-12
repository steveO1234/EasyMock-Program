package chap2.controller;

/**
 * This class encasuplates related information when error happens in handling
 * a request.
 * 
 * @author Dahai Guo
 *
 */
public class ErrorResponse implements Response{
	private Request originalRequest;
	private Exception originalException;
	
	/**
	 * Creates an object of ErrorResponse.
	 * 
	 * This is the way to input data.
	 * 
	 * @param request 
	 * @param exception
	 */
	public ErrorResponse(Request request, Exception exception){
		this.originalException=exception;
		this.originalRequest=request;
	}
	
	/** 
	 * Returns the request passed to the constructor 
	 * @return originalRequest
	 */
	public Request getOriginalRequest(){
		return originalRequest;
	}
	
	/**
	 * Returns the exception passed to the constructor
	 * @return originalException
	 */
	public Exception getOriginalException(){
		return originalException;
	}
}
