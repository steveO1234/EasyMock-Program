package chap2.controller;

public interface RequestHandler {
	Response process(Request request) throws Exception;
}
