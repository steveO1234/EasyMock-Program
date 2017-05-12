package chap2.controller;

import static org.junit.Assert.*;
import java.util.Random;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for verifying DefaultController.
 * 
 * @author Dahai Guo
 *
 */
//@RunWith(EasyMockRunner.class)
public class TestDefaultController {
	private DefaultController controller;
	private Request request;
	private RequestHandler handler;
	private Response response;
 	
 	/**
 	 * Common code, shared by test methods.
 	 * 
 	 * @throws Exception
 	 */
	@Before 
	public void setUp(){
		controller = new DefaultController();
		// define EasyMock for interfaces
		request = EasyMock.createNiceMock(Request.class);
		handler = EasyMock.createNiceMock(RequestHandler.class);
		response = EasyMock.createNiceMock(Response.class);

	} 
	 
	/**
	 * Reports error when DefaultController.getHandler fails to
	 * return correct handler. 
	 */	
 	@Test 
 	public void testGetHandler(){
 		//expect method, return answer 
  		EasyMock.expect(request.getName()).andReturn("testing");
 		// activate request 
  		EasyMock.replay(request);
  		// add request to the handler 
 		controller.addHandler(request, handler);
 		// create new handler set equal to getHandler with current request 
 		RequestHandler handler2 = controller.getHandler(request);
 		// both handler should test to be the same 
  		assertSame("Handler we set in controller should be the same handler we get",
  				handler2 , controller.getHandler(request));
 	}
 	
 	/**
 	 * Reports error when DefaultController.processRequest fails to
 	 * <ul>
 	 * <li> return a response
 	 * <li> return correct typed response
 	 * <li> return response with correct content.
 	 * <ul> 
 	 * @throws Exception 
 	 */
 	 
 	@Test
 	public void testProcessRequest() throws Exception{
 		
 		EasyMock.expect(request.getName()).andReturn("testing");
 		EasyMock.replay(request);
 		//test process request return response mock
 		EasyMock.expect(handler.process(request)).andReturn(response);
 		EasyMock.replay(handler);
 		//System.out.println(response.getClass()); 
 		
 		// add request with response return to the handler 
		controller.addHandler(request,handler);
		// create new respose object to test 
		Response testResponse = controller.processRequest(request);
		//System.out.println(testResponse.getClass());
 
		// test should not be null 
 		assertNotNull("Must not return a null response", response);
 		// both response mocks shoould be of same type and class
 		assertEquals("Response should be of type", response.getClass(), testResponse.getClass());
 		assertEquals("Wrong", testResponse, response);
 	}

 	/** 
 	 * Checks if DefaultController.getHandler will throw an run time
 	 * exception when getting an undefined handler.
 	 */
 	@Test(expected=RuntimeException.class)
 	public void testGetHandlerUnDefined(){
 		// set expect but never add to handler leaving result undefined 
 		// should throw RuntimeException
 		EasyMock.expect(request.getName()).andReturn("fail");
 		EasyMock.replay(request);
 		// never call addHandler so request never gets in
 		controller.getHandler(request); // throws RuntimeException 
 	}
 	
 	/**
 	 * Checks if DefaultController.addHandler will throw an run time
 	 * exception when setting handler for a request for which another
 	 * handler has been set.
 	 * @throws Exception  
 	 */
 // NEED THIS ONE UNCOMMENT!!!
 	@Test(expected=RuntimeException.class)
 	public void testAddRequestDuplicateName() throws Exception{
 		 
 		EasyMock.expect(request.getName()).andReturn("testing");
 		EasyMock.replay(request);
 		EasyMock.expect(handler.process(request)).andThrow(new RuntimeException());
 		EasyMock.replay(handler);
 		//add request twice to handler for duplicate entry. the duplicate should throw exception 
 		controller.addHandler(request, handler); 
 		controller.addHandler(request,handler);

 	}
}

