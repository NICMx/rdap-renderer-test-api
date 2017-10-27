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
import mx.nic.rdap.core.db.Domain;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.SearchResponse;

/**
 * Creates a {@link SearchResponse} that contains a List of {@link Entity}
 * objects, and render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class EntitySearchTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private SearchResponse<Entity> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Domain} {@link SearchResponse}
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
		renderer.renderEntities(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderEntities(response, pWriter);
	}

	private void createRequestResponse(PrintWriter pWriter, Renderer renderer) throws UnknownHostException {
		SearchResponse<Entity> response = new SearchResponse<>();
		this.pWriter = pWriter;
		this.response = response;

		List<Entity> rdapObjects = new ArrayList<>();
		response.setRdapObjects(rdapObjects);
		renderer.renderEntities(response, pWriter);

		fillRdapResponse(response);

		for (int x = 0; x < 2; x++) {
			Entity e = new Entity();
			e.setPublicIds(null);
			e.setvCardList(null);
			e.setRoles(null);
			e.setIpNetworks(null);
			e.setAutnums(null);
			rdapObjects.add(e);
			renderer.renderEntities(response, pWriter);

			e.setId(15L);
			renderer.renderEntities(response, pWriter);

			e.setRoles(new ArrayList<>());
			renderer.renderEntities(response, pWriter);

			e.getRoles().add(Role.ABUSE);
			renderer.renderEntities(response, pWriter);

			e.getRoles().add(Role.ADMINISTRATIVE);

			e.setPublicIds(new ArrayList<>());
			renderer.renderEntities(response, pWriter);

			PublicId pid1 = new PublicId();
			e.getPublicIds().add(pid1);
			renderer.renderEntities(response, pWriter);

			pid1.setId(1L);

			pid1.setType("algo");

			pid1.setPublicId("pid1");
			renderer.renderEntities(response, pWriter);

			PublicId pid2 = new PublicId();
			e.getPublicIds().add(pid2);
			renderer.renderEntities(response, pWriter);

			pid2.setPublicId("pid2");
			renderer.renderEntities(response, pWriter);

			pid2.setType("algo2");

			fillCommonRdapObject(e, false);

			List<IpNetwork> networks = new ArrayList<>();
			e.setIpNetworks(networks);
			renderer.renderEntities(response, pWriter);

			for (int a = 0; a < 1; a++) {
				IpNetwork ip = new IpNetwork();
				networks.add(ip);
				renderer.renderEntities(response, pWriter);

				ip.setId(1L);

				ip.setIpVersion(IpVersion.V4);

				ip.setStartAddress(InetAddress.getByName("192.168.1.0"));
				renderer.renderEntities(response, pWriter);

				ip.setEndAddress(InetAddress.getByName("192.168.1.255"));

				ip.setName("ip" + a);

				ip.setType("ip type " + a);

				ip.setCountry("mx");

				ip.setParentHandle("parent handle" + a);
				renderer.renderEntities(response, pWriter);

				ip.setPrefix(24);

				fillCommonRdapObject(ip, false);

			}

			for (int a = 0; a < 1; a++) {
				IpNetwork ip = new IpNetwork();
				networks.add(ip);
				renderer.renderEntities(response, pWriter);

				ip.setId(1L);
				renderer.renderEntities(response, pWriter);

				ip.setIpVersion(IpVersion.V6);
				renderer.renderEntities(response, pWriter);

				ip.setStartAddress(InetAddress.getByName("64:ff9b::"));
				renderer.renderEntities(response, pWriter);

				ip.setEndAddress(InetAddress.getByName("64:ff9b:0:0:0:0:ffff:ffff"));

				ip.setName("ip" + a);

				ip.setType("ip type " + a);

				ip.setCountry("mx");

				ip.setParentHandle("parent handle" + a);
				renderer.renderEntities(response, pWriter);

				ip.setPrefix(96);

				fillCommonRdapObject(ip, false);

			}

			ArrayList<Autnum> autnums = new ArrayList<>();
			e.setAutnums(autnums);
			renderer.renderEntities(response, pWriter);

			for (int a = 0; a < 2; a++) {
				Autnum asn = new Autnum();
				autnums.add(asn);
				renderer.renderEntities(response, pWriter);

				asn.setId(1L);
				renderer.renderEntities(response, pWriter);

				asn.setStartAutnum(1L);
				renderer.renderEntities(response, pWriter);

				asn.setEndAutnum(10L);

				asn.setName("asn" + a);

				asn.setType("asn" + a + " type");

				asn.setCountry("mx");
				renderer.renderEntities(response, pWriter);

				fillCommonRdapObject(asn, false);
			}

		}
	}

}
