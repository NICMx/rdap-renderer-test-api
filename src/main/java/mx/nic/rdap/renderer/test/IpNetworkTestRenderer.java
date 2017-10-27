package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;

import mx.nic.rdap.core.catalog.IpVersion;
import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.RequestResponse;

/**
 * Creates a {@link RequestResponse} that contains an {@link IpNetwork} object,
 * and render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class IpNetworkTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private RequestResponse<IpNetwork> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link IpNetwork} {@link RequestResponse}
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
		renderer.renderIpNetwork(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderIpNetwork(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) throws UnknownHostException {
		RequestResponse<IpNetwork> response = new RequestResponse<>();
		// this.pWriter = pWriter;
		this.response = response;

		IpNetwork ip = new IpNetwork();
		ip.setEntities(null);
		ip.setEvents(null);
		ip.setLinks(null);
		ip.setRemarks(null);
		ip.setStatus(null);
		response.setRdapObject(ip);
		renderer.renderIpNetwork(response, pWriter);

		fillRdapResponse(response);

		ip.setId(1L);
		renderer.renderIpNetwork(response, pWriter);

		ip.setIpVersion(IpVersion.V6);
		renderer.renderIpNetwork(response, pWriter);

		ip.setStartAddress(InetAddress.getByName("64:ff9b::"));
		renderer.renderIpNetwork(response, pWriter);

		ip.setEndAddress(InetAddress.getByName("64:ff9b:0:0:0:0:ffff:ffff"));
		renderer.renderIpNetwork(response, pWriter);

		ip.setName("ip");
		renderer.renderIpNetwork(response, pWriter);

		ip.setType("ip type");
		renderer.renderIpNetwork(response, pWriter);

		ip.setCountry("mx");
		renderer.renderIpNetwork(response, pWriter);

		ip.setParentHandle("parent handle");
		renderer.renderIpNetwork(response, pWriter);

		ip.setPrefix(96);
		renderer.renderIpNetwork(response, pWriter);

		fillCommonRdapObject(ip, false);

	}

}
