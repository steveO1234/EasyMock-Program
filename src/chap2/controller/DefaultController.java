package chap2.controller;

import java.util.*;

/**
* Implements Controller and uses Map to register at most one handler
* per each request.
* 
* @author Dahai Guo
*
*/
public class DefaultController implements Controller{
	private Map <String, RequestHandler>requestHandlers=
		new HashMap<String, RequestHandler>(); 
	
	/** 
	 * Given a request, returns a predefined handler if it exists;
	 * otherwise throw RuntimeException. 
	 * 
	 * @param request
	 * @return a request handler defined for request
	 */
	protected RequestHandler getHandler(Request request){
		if(!this.requestHandlers.containsKey(request.getName())){
			String message = "Cannot find handler for request name"
				+ "["+request.getName()+"]";
			throw new RuntimeException(message);
		} 
		return this.requestHandlers.get(request.getName());
	} 
	
	/**
	 * If a corresponding handler is found, lets it handle the request;
	 * otherwise returns an error response
	 * @param request
	 * @return response
	 */
	public Response processRequest(Request request){
		Response response;
		try{
			response=getHandler(request).process(request);
		}catch(Exception e){
			response=new ErrorResponse(request, e);
		}
		return response; 
	}
	 
	/**
	 * Registers handler for request.
	 * 
	 * Ignores the case where requestHandler==null.
	 * 
	 * It is ok for request to be null.
	 * 
	 * @param request
	 * @param requestHandler
	 */
	public void addHandler(Request request, RequestHandler requestHandler){
		if(this.requestHandlers.containsKey(request.getName())){
			throw new RuntimeException("A request handler has "
					+ "already been registered for request name "
					+ "[" + request.getName() + "]");
		}else{
			this.requestHandlers.put(request.getName(), requestHandler);
		}
	}
} 
