package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import mx.nic.rdap.core.catalog.IpVersion;
import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.db.Autnum;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.RequestResponse;

/**
 * Creates a {@link RequestResponse} that contains an {@link Entity} object, and
 * render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class EntityTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private RequestResponse<Entity> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Entity} {@link RequestResponse}
	 * 
	 * @param renderer
	 *            Renderer to use to render a Response
	 */
	public void testRenderer(Renderer renderer) {
		this.renderer = renderer;
		PrintWriter writer = new PrintWriter(new DevNullOutputStream());

		try {
			createRequestResponse(writer, renderer);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		StringWriter out = new StringWriter();
		renderer.renderEntity(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderEntity(response, pWriter);
	}

	private void createRequestResponse(PrintWriter pWriter, Renderer renderer) throws UnknownHostException {
		RequestResponse<Entity> response = new RequestResponse<>();
		this.pWriter = pWriter;
		this.response = response;


		Entity e = new Entity();
		e.setPublicIds(null);
		e.setvCardList(null);
		e.setRoles(null);
		e.setIpNetworks(null);
		e.setAutnums(null);
		response.setRdapObject(e);

		fillRdapResponse(response);

		renderer.renderEntity(response, pWriter);

		e.setId(15L);
		renderer.renderEntity(response, pWriter);

		e.setRoles(new ArrayList<>());
		renderer.renderEntity(response, pWriter);

		e.getRoles().add(Role.ABUSE);
		renderer.renderEntity(response, pWriter);

		e.getRoles().add(Role.ADMINISTRATIVE);
		renderer.renderEntity(response, pWriter);

		e.setPublicIds(new ArrayList<>());
		renderer.renderEntity(response, pWriter);

		PublicId pid1 = new PublicId();
		e.getPublicIds().add(pid1);
		renderer.renderEntity(response, pWriter);

		pid1.setId(1L);
		renderer.renderEntity(response, pWriter);

		pid1.setType("algo");
		renderer.renderEntity(response, pWriter);

		pid1.setPublicId("pid1");
		renderer.renderEntity(response, pWriter);

		PublicId pid2 = new PublicId();
		e.getPublicIds().add(pid2);
		renderer.renderEntity(response, pWriter);

		pid2.setPublicId("pid2");
		renderer.renderEntity(response, pWriter);

		pid2.setType("algo2");

		fillCommonRdapObject(e, false);

		List<IpNetwork> networks = new ArrayList<>();
		e.setIpNetworks(networks);
		renderer.renderEntity(response, pWriter);

		for (int a = 0; a < 1; a++) {
			IpNetwork ip = new IpNetwork();
			networks.add(ip);
			renderer.renderEntity(response, pWriter);

			ip.setId(1L);
			renderer.renderEntity(response, pWriter);

			ip.setIpVersion(IpVersion.V4);
			renderer.renderEntity(response, pWriter);

			ip.setStartAddress(InetAddress.getByName("192.168.1.0"));
			renderer.renderEntity(response, pWriter);

			ip.setEndAddress(InetAddress.getByName("192.168.1.255"));
			renderer.renderEntity(response, pWriter);

			ip.setName("ip" + a);
			renderer.renderEntity(response, pWriter);

			ip.setType("ip type " + a);
			renderer.renderEntity(response, pWriter);

			ip.setCountry("mx");
			renderer.renderEntity(response, pWriter);

			ip.setParentHandle("parent handle" + a);
			renderer.renderEntity(response, pWriter);

			ip.setPrefix(24);
			renderer.renderEntity(response, pWriter);

			fillCommonRdapObject(ip, false);

		}

		for (int a = 0; a < 1; a++) {
			IpNetwork ip = new IpNetwork();
			networks.add(ip);
			renderer.renderEntity(response, pWriter);

			ip.setId(1L);
			renderer.renderEntity(response, pWriter);

			ip.setIpVersion(IpVersion.V6);
			renderer.renderEntity(response, pWriter);

			ip.setStartAddress(InetAddress.getByName("64:ff9b::"));
			renderer.renderEntity(response, pWriter);

			ip.setEndAddress(InetAddress.getByName("64:ff9b:0:0:0:0:ffff:ffff"));
			renderer.renderEntity(response, pWriter);

			ip.setName("ip" + a);
			renderer.renderEntity(response, pWriter);

			ip.setType("ip type " + a);
			renderer.renderEntity(response, pWriter);

			ip.setCountry("mx");
			renderer.renderEntity(response, pWriter);

			ip.setParentHandle("parent handle" + a);
			renderer.renderEntity(response, pWriter);

			ip.setPrefix(96);
			renderer.renderEntity(response, pWriter);

			fillCommonRdapObject(ip, false);

		}

		ArrayList<Autnum> autnums = new ArrayList<>();
		e.setAutnums(autnums);
		renderer.renderEntity(response, pWriter);

		for (int a = 0; a < 2; a++) {
			Autnum asn = new Autnum();
			autnums.add(asn);
			renderer.renderEntity(response, pWriter);

			asn.setId(1L);
			renderer.renderEntity(response, pWriter);

			asn.setStartAutnum(1L);
			renderer.renderEntity(response, pWriter);

			asn.setEndAutnum(10L);
			renderer.renderEntity(response, pWriter);

			asn.setName("asn" + a);
			renderer.renderEntity(response, pWriter);

			asn.setType("asn" + a + " type");
			renderer.renderEntity(response, pWriter);

			asn.setCountry("mx");
			renderer.renderEntity(response, pWriter);

			fillCommonRdapObject(asn, false);
		}


	}


}
