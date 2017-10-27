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
import mx.nic.rdap.renderer.object.SearchResponse;

/**
 * Creates a {@link SearchResponse} that contains a List of {@link Domain}
 * objects, and render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class DomainSearchTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private SearchResponse<Domain> response;
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
		renderer.renderDomains(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderDomains(response, pWriter);
	}

	private void createRequestResponse(PrintWriter pWriter, Renderer renderer) throws UnknownHostException {
		SearchResponse<Domain> response = new SearchResponse<>();
		this.pWriter = pWriter;
		this.response = response;

		List<Domain> domains = new ArrayList<>();
		response.setRdapObjects(domains);
		renderer.renderDomains(response, pWriter);

		fillRdapResponse(response);

		for (int x = 0; x < 2; x++) {
			Domain d = new Domain();
			d.setNameServers(null);
			d.setVariants(null);
			d.setPublicIds(null);
			d.setEntities(null);
			d.setEvents(null);
			d.setLinks(null);
			d.setRemarks(null);
			d.setStatus(null);
			domains.add(d);
			renderer.renderDomains(response, pWriter);

			d.setId(15L);
			renderer.renderDomains(response, pWriter);

			d.setPublicIds(new ArrayList<>());
			renderer.renderDomains(response, pWriter);

			PublicId pid1 = new PublicId();
			d.getPublicIds().add(pid1);
			renderer.renderDomains(response, pWriter);

			pid1.setId(1L);

			pid1.setType("algo");
			renderer.renderDomains(response, pWriter);

			pid1.setPublicId("pid1");

			PublicId pid2 = new PublicId();
			d.getPublicIds().add(pid2);
			renderer.renderDomains(response, pWriter);

			pid2.setPublicId("pid2");
			renderer.renderDomains(response, pWriter);

			pid2.setType("algo2");

			fillCommonRdapObject(d, false);

			IpNetwork ip = new IpNetwork();
			d.setIpNetwork(ip);
			renderer.renderDomains(response, pWriter);

			ip.setId(1L);
			renderer.renderDomains(response, pWriter);

			ip.setIpVersion(IpVersion.V6);
			renderer.renderDomains(response, pWriter);

			ip.setStartAddress(InetAddress.getByName("64:ff9b::"));
			renderer.renderDomains(response, pWriter);

			ip.setEndAddress(InetAddress.getByName("64:ff9b:0:0:0:0:ffff:ffff"));

			ip.setName("ip");

			ip.setType("ip type ");

			ip.setCountry("mx");

			ip.setParentHandle("parent handle");

			ip.setPrefix(96);

			fillCommonRdapObject(ip, false);
			renderer.renderDomains(response, pWriter);

			List<Variant> variants = new ArrayList<>();
			d.setVariants(variants);
			renderer.renderDomains(response, pWriter);

			for (int a = 0; a < 2; a++) {
				Variant v = new Variant();
				v.setRelations(null);
				v.setVariantNames(null);
				variants.add(v);
				renderer.renderDomains(response, pWriter);

				v.setDomainId(1L);
				renderer.renderDomains(response, pWriter);

				v.setId(1L);
				renderer.renderDomains(response, pWriter);

				v.setIdnTable("some idn Table");

				List<VariantRelation> variantRelations = new ArrayList<>();
				v.setRelations(variantRelations);
				renderer.renderDomains(response, pWriter);

				variantRelations.add(VariantRelation.CONJOINED);
				renderer.renderDomains(response, pWriter);

				variantRelations.add(VariantRelation.REGISTERED);

				List<VariantName> variantNames = new ArrayList<>();
				v.setVariantNames(variantNames);
				renderer.renderDomains(response, pWriter);

				for (int b = 0; b < 2; b++) {
					VariantName vn = new VariantName();
					variantNames.add(vn);
					renderer.renderDomains(response, pWriter);

					vn.setLdhName("somevariantldh.com");
					renderer.renderDomains(response, pWriter);

					vn.setUnicodeName("somevariantunicode.com");
					renderer.renderDomains(response, pWriter);
				}

			}

			SecureDNS sdns = new SecureDNS();
			sdns.setDsData(null);
			sdns.setKeyData(null);
			d.setSecureDNS(sdns);
			renderer.renderDomains(response, pWriter);

			sdns.setDelegationSigned(true);
			renderer.renderDomains(response, pWriter);

			sdns.setDomainId(1L);

			sdns.setId(1L);

			sdns.setMaxSigLife(1000);
			renderer.renderDomains(response, pWriter);

			sdns.setZoneSigned(true);

			List<DsData> dss = new ArrayList<>();
			sdns.setDsData(dss);
			renderer.renderDomains(response, pWriter);

			for (int a = 0; a < 2; a++) {
				DsData ds = new DsData();
				ds.setEvents(null);
				ds.setLinks(null);
				dss.add(ds);
				renderer.renderDomains(response, pWriter);

				ds.setAlgorithm(1);
				renderer.renderDomains(response, pWriter);

				ds.setDigest("somedigest");

				ds.setDigestType(23);

				ds.setId(1L);

				ds.setKeytag(432);

				ds.setSecureDNSId(1L);

				// ds links
				List<Link> dsLinks = new ArrayList<>();
				ds.setLinks(dsLinks);
				renderer.renderDomains(response, pWriter);

				for (int c = 0; c < 2; c++) {
					Link l = new Link();
					l.setHreflang(null);
					dsLinks.add(l);
					renderer.renderDomains(response, pWriter);

					l.setHref("r1l1 href");
					renderer.renderDomains(response, pWriter);

					l.setId(1L);

					l.setMedia("r1l1 media");

					l.setRel("r1l1 rel");

					l.setTitle("r1l1 title");

					l.setType("r1l1 type");

					l.setValue("r1l1 value");

					List<String> hrefr1l1 = new ArrayList<>();
					l.setHreflang(hrefr1l1);
					renderer.renderDomains(response, pWriter);

					hrefr1l1.add("mx");
					renderer.renderDomains(response, pWriter);

					hrefr1l1.add("us");
				}

				// Events
				List<Event> dsEvents = new ArrayList<>();
				ds.setEvents(dsEvents);
				renderer.renderDomains(response, pWriter);

				for (int c = 0; c < 2; c++) {
					Event ev1 = new Event();
					ev1.setLinks(null);
					dsEvents.add(ev1);
					renderer.renderDomains(response, pWriter);

					ev1.setEventAction(EventAction.DELETION);
					renderer.renderDomains(response, pWriter);

					ev1.setEventActor("ev1 actor");

					ev1.setEventDate(new Date());
					renderer.renderDomains(response, pWriter);

					ev1.setId(1L);

					// eventLinks
					List<Link> ev1Links = new ArrayList<>();
					ev1.setLinks(ev1Links);
					renderer.renderDomains(response, pWriter);
					for (int b = 0; b < 2; b++) {
						Link l = new Link();
						l.setHreflang(null);
						ev1Links.add(l);
						renderer.renderDomains(response, pWriter);

						l.setHref("ev1l1 href");
						renderer.renderDomains(response, pWriter);

						l.setId(1L);

						l.setMedia("ev1l1 media");

						l.setRel("ev1l1 rel");

						l.setTitle("ev1l1 title");

						l.setType("ev1l1 type");

						l.setValue("ev1l1 value");

						List<String> hrefev1l1 = new ArrayList<>();
						l.setHreflang(hrefev1l1);
						renderer.renderDomains(response, pWriter);

						hrefev1l1.add("mx");

						hrefev1l1.add("us");
						renderer.renderDomains(response, pWriter);
					}

				}

			}

			List<KeyData> keys = new ArrayList<>();
			sdns.setKeyData(keys);
			renderer.renderDomains(response, pWriter);

			for (int a = 0; a < 2; a++) {
				KeyData k = new KeyData();
				k.setEvents(null);
				k.setLinks(null);
				keys.add(k);
				renderer.renderDomains(response, pWriter);

				k.setAlgorithm(1);
				renderer.renderDomains(response, pWriter);

				k.setFlags(123);

				k.setId(1L);

				k.setProtocol(123);
				renderer.renderDomains(response, pWriter);

				k.setPublicKey("some public key");

				k.setSecureDNSId(1L);
				renderer.renderDomains(response, pWriter);

				// ds links
				List<Link> keyLinks = new ArrayList<>();
				k.setLinks(keyLinks);
				renderer.renderDomains(response, pWriter);

				for (int c = 0; c < 2; c++) {
					Link l = new Link();
					l.setHreflang(null);
					keyLinks.add(l);
					renderer.renderDomains(response, pWriter);

					l.setHref("r1l1 href");
					renderer.renderDomains(response, pWriter);

					l.setId(1L);

					l.setMedia("r1l1 media");

					l.setRel("r1l1 rel");

					l.setTitle("r1l1 title");

					l.setType("r1l1 type");

					l.setValue("r1l1 value");
					renderer.renderDomains(response, pWriter);

					List<String> hrefr1l1 = new ArrayList<>();
					l.setHreflang(hrefr1l1);
					renderer.renderDomains(response, pWriter);

					hrefr1l1.add("mx");
					renderer.renderDomains(response, pWriter);

					hrefr1l1.add("us");
				}

				// Events
				List<Event> keyEvents = new ArrayList<>();
				k.setEvents(keyEvents);
				renderer.renderDomains(response, pWriter);

				for (int c = 0; c < 2; c++) {
					Event ev1 = new Event();
					ev1.setLinks(null);
					keyEvents.add(ev1);
					renderer.renderDomains(response, pWriter);

					ev1.setEventAction(EventAction.DELETION);
					renderer.renderDomains(response, pWriter);

					ev1.setEventActor("ev1 actor");

					ev1.setEventDate(new Date());

					ev1.setId(1L);
					renderer.renderDomains(response, pWriter);

					// eventLinks
					List<Link> ev1Links = new ArrayList<>();
					ev1.setLinks(ev1Links);
					renderer.renderDomains(response, pWriter);
					for (int b = 0; b < 2; b++) {
						Link l = new Link();
						l.setHreflang(null);
						ev1Links.add(l);
						renderer.renderDomains(response, pWriter);

						l.setHref("ev1l1 href");
						renderer.renderDomains(response, pWriter);

						l.setId(1L);

						l.setMedia("ev1l1 media");

						l.setRel("ev1l1 rel");

						l.setTitle("ev1l1 title");

						l.setType("ev1l1 type");

						l.setValue("ev1l1 value");
						renderer.renderDomains(response, pWriter);

						List<String> hrefev1l1 = new ArrayList<>();
						l.setHreflang(hrefev1l1);
						renderer.renderDomains(response, pWriter);

						hrefev1l1.add("mx");
						renderer.renderDomains(response, pWriter);

						hrefev1l1.add("us");
					}

				}

			}

			List<Nameserver> nSs = new ArrayList<>();
			d.setNameServers(nSs);
			renderer.renderDomains(response, pWriter);

			for (int a = 0; a < 2; a++) {
				Nameserver ns = new Nameserver();
				ns.setEntities(null);
				ns.setEvents(null);
				ns.setLinks(null);
				ns.setRemarks(null);
				ns.setStatus(null);
				nSs.add(ns);
				renderer.renderDomains(response, pWriter);

				ns.setId(1L);

				ns.setLdhName("testns.jool");
				renderer.renderDomains(response, pWriter);

				ns.setUnicodeName("testns.jool");
				renderer.renderDomains(response, pWriter);

				NameserverIpAddressesStruct ipAddresses = new NameserverIpAddressesStruct();
				ns.setIpAddresses(ipAddresses);
				renderer.renderDomains(response, pWriter);

				List<IpAddress> ipv4Adresses = new ArrayList<>();
				ipAddresses.setIpv4Adresses(ipv4Adresses);
				renderer.renderDomains(response, pWriter);

				for (int b = 0; b < 2; b++) {
					IpAddress ipns = new IpAddress();
					ipv4Adresses.add(ipns);
					renderer.renderDomains(response, pWriter);

					ipns.setId(1L);

					ipns.setType(4);
					renderer.renderDomains(response, pWriter);

					ipns.setNameserverId(1L);

					ipns.setAddress(InetAddress.getByName("192.168.1.0"));
					renderer.renderDomains(response, pWriter);
				}

				List<IpAddress> ipv6Adresses = new ArrayList<>();
				ipAddresses.setIpv6Adresses(ipv6Adresses);
				renderer.renderDomains(response, pWriter);
				for (int b = 0; b < 2; b++) {
					IpAddress ipns = new IpAddress();
					ipv6Adresses.add(ipns);
					renderer.renderDomains(response, pWriter);

					ipns.setId(1L);

					ipns.setType(6);
					renderer.renderDomains(response, pWriter);

					ipns.setNameserverId(1L);

					ipns.setAddress(InetAddress.getByName("64:ff9b::"));
					renderer.renderDomains(response, pWriter);
				}

				ns.setLdhName("testns.jool");
				renderer.renderDomains(response, pWriter);

				fillCommonRdapObject(ns, false);

			}

		}

	}

}
