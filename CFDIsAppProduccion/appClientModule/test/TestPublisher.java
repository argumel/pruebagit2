package test;


import javax.xml.ws.Endpoint;



public class TestPublisher {

	public static void main(String[] args) {
		System.out.println("into main");
		   Endpoint.publish("http://localhost:9999/ws/hello", new TestServiceImpl());
	    }

}
