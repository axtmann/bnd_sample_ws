package test.api.spi;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import test.api.ExampleProviderInterface;

@Component(name="Example",immediate=true)
public class ExampleProvider implements ExampleProviderInterface {

	@Override
	public void sayHello(String name) {
		System.out.println("Hello " + name);
	}
	
	@Activate
	public void activate() {
		System.out.println("Activate sayHello");
	}
	
	@Deactivate
	public void deactivate() {
		System.out.println("Deactivate sayHello");
	}

}
