package test;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
public class TestServiceClient {
	
	public static void main(String[] args) throws Exception {
		   
		URL url = new URL("http://localhost:9999/ws/hello?wsdl");
		
	        //1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://ws.mkyong.com/", "HelloWorldImplService");

	        Service service = Service.create(url, qname);

	        ServiceInterface hello = service.getPort(ServiceInterface.class);

	        System.out.println(hello.getHelloWorldAsString("mkyong"));

	    }


}
