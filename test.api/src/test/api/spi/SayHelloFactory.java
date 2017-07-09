package test.api.spi;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

import test.api.HelloService;

@Component(name="SayHello", configurationPolicy=ConfigurationPolicy.REQUIRE, immediate=true)
public class SayHelloFactory implements HelloService {
	
	private volatile String name;

	@Override
	public String sayHello() {
		String hello = "Hello " + name;
		return hello;
	}
	
	@Activate
	public void activate (Map<String, Object> properties) {
		name = (String) properties.getOrDefault("name", "Jon Doe");
		System.out.println("Activate with name " + name);
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Deactivate name " + name);
		name = null;
	}

}
