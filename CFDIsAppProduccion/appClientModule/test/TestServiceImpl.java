package test;

import javax.jws.WebService;


public class TestServiceImpl implements ServiceInterface {

	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}

}
