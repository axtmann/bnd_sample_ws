package test.api.spi;

import java.io.IOException;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property={
		"osgi.command.scope=hello", 
		"osgi.command.function=createHello"
	},
	service = HelloCommand.class
)
public class HelloCommand {
	
	@Reference
	ConfigurationAdmin cm;

	public String createHello(String s) {
		Configuration c;
		try {
			c = cm.createFactoryConfiguration("SayHello", "?");
			Hashtable<String, Object> p = new Hashtable<>();
			p.put("name", s);
			c.update(p);
			return "Created Hello Service for " + s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error creating Hello Service for" + s;
		}
	}
}