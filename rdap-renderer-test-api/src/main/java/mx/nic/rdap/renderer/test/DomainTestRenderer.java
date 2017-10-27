package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;

import mx.nic.rdap.core.catalog.EventAction;
import mx.nic.rdap.core.catalog.IpVersion;
import mx.nic.rdap.core.catalog.VariantRelation;
import mx.nic.rdap.core.db.Domain;
import mx.nic.rdap.core.db.DsData;
import mx.nic.rdap.core.db.Event;
import mx.nic.rdap.core.db.IpAddress;
import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.core.db.KeyData;
import mx.nic.rdap.core.db.Link;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.core.db.SecureDNS;
import mx.nic.rdap.core.db.Variant;
import mx.nic.rdap.core.db.VariantName;
import mx.nic.rdap.core.db.struct.NameserverIpAddressesStruct;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.RequestResponse;

/**
 * Creates a {@link RequestResponse} that contains an {@link Domain} object, and
 * render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class DomainTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private RequestResponse<Domain> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Domain} {@link RequestResponse}
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
		renderer.renderDomain(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderDomain(response, pWriter);
	}

	private void createRequestResponse(PrintWriter pWriter, Renderer renderer) throws UnknownHostException {
		RequestResponse<Domain> response = new RequestResponse<>();
		this.pWriter = pWriter;
		this.response = response;

		Domain d = new Domain();
		d.setNameServers(null);
		d.setVariants(null);
		d.setPublicIds(null);
		d.setEntities(null);
		d.setEvents(null);
		d.setLinks(null);
		d.setRemarks(null);
		d.setStatus(null);
		response.setRdapObject(d);
		renderer.renderDomain(response, pWriter);

		fillRdapResponse(response);

		d.setId(15L);
		renderer.renderDomain(response, pWriter);

		d.setPublicIds(new ArrayList<>());
		renderer.renderDomain(response, pWriter);

		PublicId pid1 = new PublicId();
		d.getPublicIds().add(pid1);
		renderer.renderDomain(response, pWriter);

		pid1.setId(1L);
		renderer.renderDomain(response, pWriter);

		pid1.setType("algo");
		renderer.renderDomain(response, pWriter);

		pid1.setPublicId("pid1");
		renderer.renderDomain(response, pWriter);

		PublicId pid2 = new PublicId();
		d.getPublicIds().add(pid2);
		renderer.renderDomain(response, pWriter);

		pid2.setPublicId("pid2");
		renderer.renderDomain(response, pWriter);

		pid2.setType("algo2");

		fillCommonRdapObject(d, false);

		IpNetwork ip = new IpNetwork();
		d.setIpNetwork(ip);
		renderer.renderDomain(response, pWriter);

		ip.setId(1L);
		renderer.renderDomain(response, pWriter);

		ip.setIpVersion(IpVersion.V6);
		renderer.renderDomain(response, pWriter);

		ip.setStartAddress(InetAddress.getByName("64:ff9b::"));
		renderer.renderDomain(response, pWriter);

		ip.setEndAddress(InetAddress.getByName("64:ff9b:0:0:0:0:ffff:ffff"));
		renderer.renderDomain(response, pWriter);

		ip.setName("ip");
		renderer.renderDomain(response, pWriter);

		ip.setType("ip type ");
		renderer.renderDomain(response, pWriter);

		ip.setCountry("mx");
		renderer.renderDomain(response, pWriter);

		ip.setParentHandle("parent handle");
		renderer.renderDomain(response, pWriter);

		ip.setPrefix(96);
		renderer.renderDomain(response, pWriter);
		fillCommonRdapObject(ip, false);
		renderer.renderDomain(response, pWriter);

		List<Variant> variants = new ArrayList<>();
		d.setVariants(variants);
		renderer.renderDomain(response, pWriter);

		for (int a = 0; a < 2; a++) {
			Variant v = new Variant();
			v.setRelations(null);
			v.setVariantNames(null);
			variants.add(v);
			renderer.renderDomain(response, pWriter);

			v.setDomainId(1L);
			renderer.renderDomain(response, pWriter);

			v.setId(1L);
			renderer.renderDomain(response, pWriter);

			v.setIdnTable("some idn Table");
			renderer.renderDomain(response, pWriter);

			List<VariantRelation> variantRelations = new ArrayList<>();
			v.setRelations(variantRelations);
			renderer.renderDomain(response, pWriter);

			variantRelations.add(VariantRelation.CONJOINED);
			renderer.renderDomain(response, pWriter);

			variantRelations.add(VariantRelation.REGISTERED);
			renderer.renderDomain(response, pWriter);

			List<VariantName> variantNames = new ArrayList<>();
			v.setVariantNames(variantNames);
			renderer.renderDomain(response, pWriter);

			for (int b = 0; b < 2; b++) {
				VariantName vn = new VariantName();
				variantNames.add(vn);
				renderer.renderDomain(response, pWriter);

				vn.setLdhName("somevariantldh.com");
				renderer.renderDomain(response, pWriter);

				vn.setUnicodeName("somevariantunicode.com");
				renderer.renderDomain(response, pWriter);
			}

		}
		
		SecureDNS sdns =  new SecureDNS();
		sdns.setDsData(null);
		sdns.setKeyData(null);
		d.setSecureDNS(sdns);
		renderer.renderDomain(response, pWriter);
		
		sdns.setDelegationSigned(true);
		renderer.renderDomain(response, pWriter);

		sdns.setDomainId(1L);
		renderer.renderDomain(response, pWriter);

		sdns.setId(1L);
		renderer.renderDomain(response, pWriter);

		sdns.setMaxSigLife(1000);
		renderer.renderDomain(response, pWriter);

		sdns.setZoneSigned(true);
		renderer.renderDomain(response, pWriter);

		List<DsData> dss = new ArrayList<>();
		sdns.setDsData(dss);
		renderer.renderDomain(response, pWriter);

		for (int a = 0; a < 2; a++) {
			DsData ds = new DsData();
			ds.setEvents(null);
			ds.setLinks(null);
			dss.add(ds);
			renderer.renderDomain(response, pWriter);
			
			ds.setAlgorithm(1);
			renderer.renderDomain(response, pWriter);

			ds.setDigest("somedigest");
			renderer.renderDomain(response, pWriter);

			ds.setDigestType(23);
			renderer.renderDomain(response, pWriter);

			ds.setId(1L);
			renderer.renderDomain(response, pWriter);

			ds.setKeytag(432);
			renderer.renderDomain(response, pWriter);

			ds.setSecureDNSId(1L);
			renderer.renderDomain(response, pWriter);

			// ds links
			List<Link> dsLinks = new ArrayList<>();
			ds.setLinks(dsLinks);
			renderer.renderDomain(response, pWriter);

			for (int c = 0; c < 2; c++) {
				Link l = new Link();
				l.setHreflang(null);
				dsLinks.add(l);
				renderer.renderDomain(response, pWriter);

				l.setHref("r1l1 href");
				renderer.renderDomain(response, pWriter);

				l.setId(1L);
				renderer.renderDomain(response, pWriter);

				l.setMedia("r1l1 media");
				renderer.renderDomain(response, pWriter);

				l.setRel("r1l1 rel");
				renderer.renderDomain(response, pWriter);

				l.setTitle("r1l1 title");
				renderer.renderDomain(response, pWriter);

				l.setType("r1l1 type");
				renderer.renderDomain(response, pWriter);

				l.setValue("r1l1 value");
				renderer.renderDomain(response, pWriter);

				List<String> hrefr1l1 = new ArrayList<>();
				l.setHreflang(hrefr1l1);
				renderer.renderDomain(response, pWriter);

				hrefr1l1.add("mx");
				renderer.renderDomain(response, pWriter);

				hrefr1l1.add("us");
				renderer.renderDomain(response, pWriter);
			}

			// Events
			List<Event> dsEvents = new ArrayList<>();
			ds.setEvents(dsEvents);
			renderer.renderDomain(response, pWriter);

			for (int c = 0; c < 2; c++) {
				Event ev1 = new Event();
				ev1.setLinks(null);
				dsEvents.add(ev1);
				renderer.renderDomain(response, pWriter);

				ev1.setEventAction(EventAction.DELETION);
				renderer.renderDomain(response, pWriter);

				ev1.setEventActor("ev1 actor");
				renderer.renderDomain(response, pWriter);

				ev1.setEventDate(new Date());
				renderer.renderDomain(response, pWriter);

				ev1.setId(1L);
				renderer.renderDomain(response, pWriter);

				// eventLinks
				List<Link> ev1Links = new ArrayList<>();
				ev1.setLinks(ev1Links);
				renderer.renderDomain(response, pWriter);
				for (int b = 0; b < 2; b++) {
					Link l = new Link();
					l.setHreflang(null);
					ev1Links.add(l);
					renderer.renderDomain(response, pWriter);

					l.setHref("ev1l1 href");
					renderer.renderDomain(response, pWriter);

					l.setId(1L);
					renderer.renderDomain(response, pWriter);

					l.setMedia("ev1l1 media");
					renderer.renderDomain(response, pWriter);

					l.setRel("ev1l1 rel");
					renderer.renderDomain(response, pWriter);

					l.setTitle("ev1l1 title");
					renderer.renderDomain(response, pWriter);

					l.setType("ev1l1 type");
					renderer.renderDomain(response, pWriter);

					l.setValue("ev1l1 value");
					renderer.renderDomain(response, pWriter);

					List<String> hrefev1l1 = new ArrayList<>();
					l.setHreflang(hrefev1l1);
					renderer.renderDomain(response, pWriter);

					hrefev1l1.add("mx");
					renderer.renderDomain(response, pWriter);

					hrefev1l1.add("us");
					renderer.renderDomain(response, pWriter);
				}

			}

		}

		List<KeyData> keys = new ArrayList<>();
		sdns.setKeyData(keys);
		renderer.renderDomain(response, pWriter);

		for (int a = 0; a < 2; a++) {
			KeyData k = new KeyData();
			k.setEvents(null);
			k.setLinks(null);
			keys.add(k);
			renderer.renderDomain(response, pWriter);

			k.setAlgorithm(1);
			renderer.renderDomain(response, pWriter);

			k.setFlags(123);
			renderer.renderDomain(response, pWriter);

			k.setId(1L);
			renderer.renderDomain(response, pWriter);

			k.setProtocol(123);
			renderer.renderDomain(response, pWriter);

			k.setPublicKey("some public key");
			renderer.renderDomain(response, pWriter);

			k.setSecureDNSId(1L);
			renderer.renderDomain(response, pWriter);

			// ds links
			List<Link> keyLinks = new ArrayList<>();
			k.setLinks(keyLinks);
			renderer.renderDomain(response, pWriter);

			for (int c = 0; c < 2; c++) {
				Link l = new Link();
				l.setHreflang(null);
				keyLinks.add(l);
				renderer.renderDomain(response, pWriter);

				l.setHref("r1l1 href");
				renderer.renderDomain(response, pWriter);

				l.setId(1L);
				renderer.renderDomain(response, pWriter);

				l.setMedia("r1l1 media");
				renderer.renderDomain(response, pWriter);

				l.setRel("r1l1 rel");
				renderer.renderDomain(response, pWriter);

				l.setTitle("r1l1 title");
				renderer.renderDomain(response, pWriter);

				l.setType("r1l1 type");
				renderer.renderDomain(response, pWriter);

				l.setValue("r1l1 value");
				renderer.renderDomain(response, pWriter);

				List<String> hrefr1l1 = new ArrayList<>();
				l.setHreflang(hrefr1l1);
				renderer.renderDomain(response, pWriter);

				hrefr1l1.add("mx");
				renderer.renderDomain(response, pWriter);

				hrefr1l1.add("us");
				renderer.renderDomain(response, pWriter);
			}

			// Events
			List<Event> keyEvents = new ArrayList<>();
			k.setEvents(keyEvents);
			renderer.renderDomain(response, pWriter);

			for (int c = 0; c < 2; c++) {
				Event ev1 = new Event();
				ev1.setLinks(null);
				keyEvents.add(ev1);
				renderer.renderDomain(response, pWriter);

				ev1.setEventAction(EventAction.DELETION);
				renderer.renderDomain(response, pWriter);

				ev1.setEventActor("ev1 actor");
				renderer.renderDomain(response, pWriter);

				ev1.setEventDate(new Date());
				renderer.renderDomain(response, pWriter);

				ev1.setId(1L);
				renderer.renderDomain(response, pWriter);

				// eventLinks
				List<Link> ev1Links = new ArrayList<>();
				ev1.setLinks(ev1Links);
				renderer.renderDomain(response, pWriter);
				for (int b = 0; b < 2; b++) {
					Link l = new Link();
					l.setHreflang(null);
					ev1Links.add(l);
					renderer.renderDomain(response, pWriter);

					l.setHref("ev1l1 href");
					renderer.renderDomain(response, pWriter);

					l.setId(1L);
					renderer.renderDomain(response, pWriter);

					l.setMedia("ev1l1 media");
					renderer.renderDomain(response, pWriter);

					l.setRel("ev1l1 rel");
					renderer.renderDomain(response, pWriter);

					l.setTitle("ev1l1 title");
					renderer.renderDomain(response, pWriter);

					l.setType("ev1l1 type");
					renderer.renderDomain(response, pWriter);

					l.setValue("ev1l1 value");
					renderer.renderDomain(response, pWriter);

					List<String> hrefev1l1 = new ArrayList<>();
					l.setHreflang(hrefev1l1);
					renderer.renderDomain(response, pWriter);

					hrefev1l1.add("mx");
					renderer.renderDomain(response, pWriter);

					hrefev1l1.add("us");
					renderer.renderDomain(response, pWriter);
				}

			}

		}

		List<Nameserver> nSs = new ArrayList<>();
		d.setNameServers(nSs);
		renderer.renderDomain(response, pWriter);

		for (int a = 0; a < 2; a++) {
			Nameserver ns = new Nameserver();
			ns.setEntities(null);
			ns.setEvents(null);
			ns.setLinks(null);
			ns.setRemarks(null);
			ns.setStatus(null);
			nSs.add(ns);
			renderer.renderDomain(response, pWriter);

			ns.setId(1L);
			renderer.renderDomain(response, pWriter);

			ns.setLdhName("testns.jool");
			renderer.renderDomain(response, pWriter);

			ns.setUnicodeName("testns.jool");
			renderer.renderDomain(response, pWriter);

			NameserverIpAddressesStruct ipAddresses = new NameserverIpAddressesStruct();
			ns.setIpAddresses(ipAddresses);
			renderer.renderDomain(response, pWriter);

			List<IpAddress> ipv4Adresses = new ArrayList<>();
			ipAddresses.setIpv4Adresses(ipv4Adresses);
			renderer.renderDomain(response, pWriter);

			for (int b = 0; b < 2; b++) {
				IpAddress ipns = new IpAddress();
				ipv4Adresses.add(ipns);
				renderer.renderDomain(response, pWriter);

				ipns.setId(1L);
				renderer.renderDomain(response, pWriter);

				ipns.setType(4);
				renderer.renderDomain(response, pWriter);

				ipns.setNameserverId(1L);
				renderer.renderDomain(response, pWriter);

				ipns.setAddress(InetAddress.getByName("192.168.1.0"));
				renderer.renderDomain(response, pWriter);
			}

			List<IpAddress> ipv6Adresses = new ArrayList<>();
			ipAddresses.setIpv6Adresses(ipv6Adresses);
			renderer.renderDomain(response, pWriter);
			for (int b = 0; b < 2; b++) {
				IpAddress ipns = new IpAddress();
				ipv6Adresses.add(ipns);
				renderer.renderDomain(response, pWriter);

				ipns.setId(1L);
				renderer.renderDomain(response, pWriter);

				ipns.setType(6);
				renderer.renderDomain(response, pWriter);

				ipns.setNameserverId(1L);
				renderer.renderDomain(response, pWriter);

				ipns.setAddress(InetAddress.getByName("64:ff9b::"));
				renderer.renderDomain(response, pWriter);
			}

			ns.setLdhName("testns.jool");
			renderer.renderDomain(response, pWriter);

			fillCommonRdapObject(ns, false);

		}



	}

}
