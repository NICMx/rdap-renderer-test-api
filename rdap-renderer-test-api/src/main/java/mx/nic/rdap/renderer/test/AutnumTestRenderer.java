package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import mx.nic.rdap.core.db.Autnum;
import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.RequestResponse;

/**
 * Creates a {@link RequestResponse} that contains an {@link Autnum} object, and
 * render the response with the given renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw a
 * {@link NullPointerException}.
 *
 */
public class AutnumTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private RequestResponse<Autnum> response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link Autnum} {@link RequestResponse}
	 * 
	 * @param renderer
	 *            Renderer to use to render a Response
	 */
	public void testRenderer(Renderer renderer) {
		this.renderer = renderer;
		pWriter = new PrintWriter(new DevNullOutputStream());

		createRequestResponse(renderer);

		StringWriter out = new StringWriter();
		renderer.renderAutnum(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderAutnum(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) {
		RequestResponse<Autnum> response = new RequestResponse<>();
		// this.pWriter = pWriter;
		this.response = response;

		Autnum asn = new Autnum();
		asn.setEntities(null);
		asn.setEvents(null);
		asn.setLinks(null);
		asn.setLinks(null);
		asn.setRemarks(null);
		asn.setStatus(null);
		response.setRdapObject(asn);
		renderer.renderAutnum(response, pWriter);

		fillRdapResponse(response);

		asn.setId(1L);
		renderer.renderAutnum(response, pWriter);

		asn.setStartAutnum(1L);
		renderer.renderAutnum(response, pWriter);

		asn.setEndAutnum(10L);
		renderer.renderAutnum(response, pWriter);

		asn.setName("asn");
		renderer.renderAutnum(response, pWriter);

		asn.setType("asn type");
		renderer.renderAutnum(response, pWriter);

		asn.setCountry("mx");
		renderer.renderAutnum(response, pWriter);

		fillCommonRdapObject(asn, false);

	}


}
