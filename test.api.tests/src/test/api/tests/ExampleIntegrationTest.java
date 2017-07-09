package test.api.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import test.api.ExampleProviderInterface;
import test.api.HelloService;

@RunWith(MockitoJUnitRunner.class)
public class ExampleIntegrationTest {

	private final BundleContext context = FrameworkUtil.getBundle(ExampleIntegrationTest.class).getBundleContext();

	@Before
	public void before() {
		// TODO add test setup here
	}

	@After
	public void after() {
		// TODO add test clear up here
	}

	@Test
	public void testExample() throws InterruptedException {
		assertNotNull(context);
		ExampleProviderInterface sayHello = getService(ExampleProviderInterface.class);
		assertNotNull(sayHello);
	}
	
	@Test
	public void testConfigurationAdmin() throws InterruptedException, IOException
	{
		assertNotNull(context);
		ConfigurationAdmin configAdmin = getService(ConfigurationAdmin.class);
		assertNotNull(configAdmin);
		Configuration configuration = configAdmin.createFactoryConfiguration("SayHello", "?");
		assertNotNull(configuration);
		Dictionary<String,Object> properties = configuration.getProperties();
		assertNull(properties);
		HelloService helloService = getService(HelloService.class, 2000l);
		assertNull(helloService);
		
		Dictionary<String, Object> newConfig = new Hashtable<>();
		newConfig.put("name", "Emil");
		configuration.update(newConfig);
		
		helloService = getService(HelloService.class);
		assertNotNull(helloService);
		assertEquals("Hello Emil", helloService.sayHello());
	}
	
	<T> T getService(Class<T> clazz) throws InterruptedException {
		return getService(clazz, 5000l);
	}
	
	<T> T getService(Class<T> clazz, long timeout) throws InterruptedException {
		ServiceTracker<T, T> tracker = new ServiceTracker<>(context, clazz, null);
		tracker.open();
		return tracker.waitForService(timeout);
	}

}