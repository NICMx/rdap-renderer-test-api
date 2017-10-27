package mx.nic.rdap.renderer.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.nic.rdap.core.catalog.EventAction;
import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.catalog.Status;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.Event;
import mx.nic.rdap.core.db.Link;
import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.core.db.RdapObject;
import mx.nic.rdap.core.db.Remark;
import mx.nic.rdap.core.db.RemarkDescription;
import mx.nic.rdap.renderer.object.RdapResponse;

public abstract class CommonRenderer {

	protected CommonRenderer() {
		// no code
	}

	protected abstract void render();

	protected void fillRdapResponse(RdapResponse response) {

		List<String> rdapConformance = new ArrayList<>();
		response.setRdapConformance(rdapConformance);

		rdapConformance.add("rdap_level_0");
		render();

		rdapConformance.add("some_extra_conformance_for_test");
		render();

		List<Remark> remarks = new ArrayList<>();
		response.setNotices(remarks);
		render();

		for (int a = 0; a < 2; a++) {
			// remark1
			Remark r1 = new Remark();
			r1.setDescriptions(null);
			r1.setLinks(null);
			remarks.add(r1);
			render();

			r1.setId(1L);
			render();

			r1.setLanguage("MX LANG");
			render();

			r1.setTitle("notice title");
			render();

			r1.setType("notice type");
			render();

			// remark description
			List<RemarkDescription> descs1 = new ArrayList<>();
			r1.setDescriptions(descs1);
			render();

			for (int b = 0; b < 2; b++) {
				RemarkDescription rd1 = new RemarkDescription();
				descs1.add(rd1);
				render();

				rd1.setRemarkId(1L);
				render();

				rd1.setOrder(1);
				render();
				rd1.setDescription("start notice");
				render();
			}

			// remark links
			List<Link> r1Links = new ArrayList<>();
			r1.setLinks(r1Links);
			render();

			// remark link 1
			for (int c = 0; c < 2; c++) {
				Link r1l1 = new Link();
				r1Links.add(r1l1);
				render();

				r1l1.setHref("notice href");
				render();

				r1l1.setId(1L);
				render();

				r1l1.setMedia("notice media");
				render();

				r1l1.setRel("notice rel");
				render();

				r1l1.setTitle("notice title");
				render();

				r1l1.setType("notice type");
				render();

				r1l1.setValue("notice value");
				render();

				List<String> hrefr1l1 = new ArrayList<>();
				r1l1.setHreflang(hrefr1l1);
				render();

				hrefr1l1.add("es");
				render();

				hrefr1l1.add("en");
				render();
			}

		}
	}

	protected void fillCommonRdapObject(RdapObject rdapObject, boolean isNested) {
		rdapObject.setStatus(null);
		rdapObject.setRemarks(null);
		rdapObject.setLinks(null);
		rdapObject.setEvents(null);
		rdapObject.setEntities(null);

		rdapObject.setHandle("test-handle");
		render();

		rdapObject.setPort43(" port 43 string");
		render();

		rdapObject.setLang("Lang MX");
		render();

		// Remarks
		List<Remark> remarks = new ArrayList<>();
		rdapObject.setRemarks(remarks);
		render();

		for (int a = 0; a < 2; a++) {
			// remark1
			Remark r1 = new Remark();
			r1.setDescriptions(null);
			r1.setLinks(null);
			remarks.add(r1);
			render();

			r1.setId(1L);
			render();

			r1.setLanguage("MX LANG");
			render();

			r1.setTitle("title r1");
			render();

			r1.setType("type r1");
			render();

			// remark description
			List<RemarkDescription> descs1 = new ArrayList<>();
			r1.setDescriptions(descs1);
			render();

			for (int b = 0; b < 2; b++) {
				RemarkDescription rd1 = new RemarkDescription();
				descs1.add(rd1);
				render();

				rd1.setRemarkId(1L);
				render();

				rd1.setOrder(1);
				render();
				rd1.setDescription("start rd1");
				render();
			}

			// remark links
			List<Link> r1Links = new ArrayList<>();
			r1.setLinks(r1Links);
			render();

			// remark link 1
			for (int c = 0; c < 2; c++) {
				Link r1l1 = new Link();
				r1Links.add(r1l1);
				render();

				r1l1.setHref("r1l1 href");
				render();

				r1l1.setId(1L);
				render();

				r1l1.setMedia("r1l1 media");
				render();

				r1l1.setRel("r1l1 rel");
				render();

				r1l1.setTitle("r1l1 title");
				render();

				r1l1.setType("r1l1 type");
				render();

				r1l1.setValue("r1l1 value");
				render();

				List<String> hrefr1l1 = new ArrayList<>();
				r1l1.setHreflang(hrefr1l1);
				render();

				hrefr1l1.add("en");
				render();

				hrefr1l1.add("es");
				render();
			}

		}

		// Links
		List<Link> links = new ArrayList<>();
		rdapObject.setLinks(links);
		render();

		for (int a = 0; a < 2; a++) {
			Link l1 = new Link();
			l1.setHreflang(null);
			links.add(l1);
			render();

			l1.setHref("l1 href");
			render();

			l1.setId(1L);
			render();

			l1.setMedia("l1 media");
			render();

			l1.setRel("l1 rel");
			render();

			l1.setTitle("l1 title");
			render();

			l1.setType("l1 type");
			render();

			l1.setValue("l1 value");
			render();

			List<String> hrefl1 = new ArrayList<>();
			l1.setHreflang(hrefl1);
			render();

			hrefl1.add("en");
			render();

			hrefl1.add("es");
			render();

		}

		// status
		List<Status> status = new ArrayList<>();
		rdapObject.setStatus(status);
		render();

		status.add(Status.ACTIVE);
		render();

		status.add(Status.CLIENT_DELETE_PROHIBITED);
		render();

		// Events
		List<Event> events = new ArrayList<>();
		rdapObject.setEvents(events);
		render();

		for (int a = 0; a < 2; a++) {
			Event ev1 = new Event();
			ev1.setLinks(null);
			events.add(ev1);
			render();

			ev1.setEventAction(EventAction.DELETION);
			render();

			ev1.setEventActor("ev1 actor");
			render();

			ev1.setEventDate(new Date());
			render();

			ev1.setId(1L);
			render();

			// eventLinks
			List<Link> ev1Links = new ArrayList<>();
			ev1.setLinks(ev1Links);
			render();
			for (int b = 0; b < 2; b++) {
				Link ev1l1 = new Link();
				ev1Links.add(ev1l1);
				render();

				ev1l1.setHref("ev1l1 href");
				render();

				ev1l1.setId(1L);
				render();

				ev1l1.setMedia("ev1l1 media");
				render();

				ev1l1.setRel("ev1l1 rel");
				render();

				ev1l1.setTitle("ev1l1 title");
				render();

				ev1l1.setType("ev1l1 type");
				render();

				ev1l1.setValue("ev1l1 value");
				render();

				List<String> hrefev1l1 = new ArrayList<>();
				ev1l1.setHreflang(hrefev1l1);
				render();

				hrefev1l1.add("en");
				render();

				hrefev1l1.add("es");
				render();
			}

		}

		if (!isNested) {
			fillAnidatedEntity(rdapObject);
		}

	}

	protected void fillAnidatedEntity(RdapObject parent) {
		List<Entity> entities = new ArrayList<>();
		parent.setEntities(entities);
		render();

		for (int i = 0; i < 2; i++) {
			Entity nestedEntity = new Entity();
			nestedEntity.setPublicIds(null);
			nestedEntity.setvCardList(null);
			nestedEntity.setRoles(null);
			nestedEntity.setIpNetworks(null);
			nestedEntity.setAutnums(null);
			entities.add(nestedEntity);
			render();

			nestedEntity.setId(15L);
			render();

			nestedEntity.setRoles(new ArrayList<>());
			render();

			nestedEntity.getRoles().add(Role.ABUSE);
			render();

			nestedEntity.getRoles().add(Role.ADMINISTRATIVE);
			render();

			nestedEntity.setPort43(" port 43 string");
			render();

			nestedEntity.setPublicIds(new ArrayList<>());
			render();

			PublicId pid1 = new PublicId();
			nestedEntity.getPublicIds().add(pid1);
			render();

			pid1.setId(1L);
			render();

			pid1.setType("algo");
			render();

			pid1.setPublicId("pid1");
			render();

			PublicId pid2 = new PublicId();
			nestedEntity.getPublicIds().add(pid2);
			render();

			pid2.setPublicId("pid2");
			render();

			pid2.setType("algo2");

			fillCommonRdapObject(nestedEntity, true);

			nestedEntity.setIpNetworks(new ArrayList<>());
			render();

			nestedEntity.setAutnums(new ArrayList<>());
			render();
		}

	}

}
