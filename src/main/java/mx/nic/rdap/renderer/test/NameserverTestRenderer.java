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
import mx.nic.rdap.renderer.object.RequestResponse;

/**
 * Creates a {@link RequestResponse} that contains an {@link Nameserver} object,
 * and render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class NameserverTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private RequestResponse<Nameserver> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Nameserver}
	 * {@link RequestResponse}
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
		renderer.renderNameserver(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderNameserver(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) throws UnknownHostException {
		RequestResponse<Nameserver> response = new RequestResponse<>();
		// this.pWriter = pWriter;
		this.response = response;

		Nameserver ns = new Nameserver();
		ns.setEntities(null);
		ns.setEvents(null);
		ns.setLinks(null);
		ns.setRemarks(null);
		ns.setStatus(null);
		response.setRdapObject(ns);
		renderer.renderNameserver(response, pWriter);

		fillRdapResponse(response);

		ns.setId(1L);
		renderer.renderNameserver(response, pWriter);

		ns.setLdhName("testns.jool");
		renderer.renderNameserver(response, pWriter);

		ns.setUnicodeName("testns.jool");
		renderer.renderNameserver(response, pWriter);

		NameserverIpAddressesStruct ipAddresses = new NameserverIpAddressesStruct();
		ns.setIpAddresses(ipAddresses);
		renderer.renderNameserver(response, pWriter);

		List<IpAddress> ipv4Adresses = new ArrayList<>();
		ipAddresses.setIpv4Adresses(ipv4Adresses);
		renderer.renderNameserver(response, pWriter);

		for (int a = 0; a < 2; a++) {
			IpAddress ip = new IpAddress();
			ipv4Adresses.add(ip);
			renderer.renderNameserver(response, pWriter);

			ip.setId(1L);
			renderer.renderNameserver(response, pWriter);

			ip.setType(4);
			renderer.renderNameserver(response, pWriter);

			ip.setNameserverId(1L);
			renderer.renderNameserver(response, pWriter);

			ip.setAddress(InetAddress.getByName("192.168.1.0"));
			renderer.renderNameserver(response, pWriter);
		}

		List<IpAddress> ipv6Adresses = new ArrayList<>();
		ipAddresses.setIpv6Adresses(ipv6Adresses);
		renderer.renderNameserver(response, pWriter);
		for (int a = 0; a < 2; a++) {
			IpAddress ip = new IpAddress();
			ipv6Adresses.add(ip);
			renderer.renderNameserver(response, pWriter);

			ip.setId(1L);
			renderer.renderNameserver(response, pWriter);

			ip.setType(6);
			renderer.renderNameserver(response, pWriter);

			ip.setNameserverId(1L);
			renderer.renderNameserver(response, pWriter);

			ip.setAddress(InetAddress.getByName("64:ff9b::"));
			renderer.renderNameserver(response, pWriter);
		}

		ns.setLdhName("testns.jool");
		renderer.renderNameserver(response, pWriter);

		fillCommonRdapObject(ns, false);

	}

}
