package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import mx.nic.rdap.core.db.IpAddress;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.core.db.struct.NameserverIpAddressesStruct;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.SearchResponse;

/**
 * Creates a {@link SearchResponse} that contains a List of {@link Nameserver}
 * objects, and render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class NameserverSearchTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private SearchResponse<Nameserver> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Nameserver} {@link SearchResponse}
	 * 
	 * @param renderer
	 *            Renderer to use to render a Response
	 */
	public void testRenderer(Renderer renderer) {
		this.renderer = renderer;
		pWriter = new PrintWriter(new DevNullOutputStream());

		try {
			createRequestResponse(renderer);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		StringWriter out = new StringWriter();
		renderer.renderNameservers(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderNameservers(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) throws UnknownHostException {
		SearchResponse<Nameserver> response = new SearchResponse<>();
		this.response = response;

		List<Nameserver> rdapObjects = new ArrayList<>();
		response.setRdapObjects(rdapObjects);
		renderer.renderNameservers(response, pWriter);

		fillRdapResponse(response);

		for (int x = 0; x < 2; x++) {

			Nameserver ns = new Nameserver();
			ns.setEntities(null);
			ns.setEvents(null);
			ns.setLinks(null);
			ns.setRemarks(null);
			ns.setStatus(null);
			rdapObjects.add(ns);
			renderer.renderNameservers(response, pWriter);

			ns.setId(1L);
			renderer.renderNameservers(response, pWriter);

			ns.setLdhName("testns.jool");
			renderer.renderNameservers(response, pWriter);

			ns.setUnicodeName("testns.jool");
			renderer.renderNameservers(response, pWriter);

			NameserverIpAddressesStruct ipAddresses = new NameserverIpAddressesStruct();
			ns.setIpAddresses(ipAddresses);
			renderer.renderNameservers(response, pWriter);

			List<IpAddress> ipv4Adresses = new ArrayList<>();
			ipAddresses.setIpv4Adresses(ipv4Adresses);
			renderer.renderNameservers(response, pWriter);

			for (int a = 0; a < 2; a++) {
				IpAddress ip = new IpAddress();
				ipv4Adresses.add(ip);
				renderer.renderNameservers(response, pWriter);

				ip.setId(1L);
				renderer.renderNameservers(response, pWriter);

				ip.setType(4);
				renderer.renderNameservers(response, pWriter);

				ip.setNameserverId(1L);
				renderer.renderNameservers(response, pWriter);

				ip.setAddress(InetAddress.getByName("192.168.1.0"));
				renderer.renderNameservers(response, pWriter);
			}

			List<IpAddress> ipv6Adresses = new ArrayList<>();
			ipAddresses.setIpv6Adresses(ipv6Adresses);
			renderer.renderNameservers(response, pWriter);
			for (int a = 0; a < 2; a++) {
				IpAddress ip = new IpAddress();
				ipv6Adresses.add(ip);
				renderer.renderNameservers(response, pWriter);

				ip.setId(1L);
				renderer.renderNameservers(response, pWriter);

				ip.setType(6);
				renderer.renderNameservers(response, pWriter);

				ip.setNameserverId(1L);
				renderer.renderNameservers(response, pWriter);

				ip.setAddress(InetAddress.getByName("64:ff9b::"));
				renderer.renderNameservers(response, pWriter);
			}

			ns.setLdhName("testns.jool");
			renderer.renderNameservers(response, pWriter);

			fillCommonRdapObject(ns, false);
		}

	}

}
