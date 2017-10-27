package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import org.junit.Assert;

import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.HelpResponse;

/**
 * Creates a {@link HelpResponse} and render the response with the given
 * renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class HelpTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private HelpResponse response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link HelpResponse}
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
		renderer.renderHelp(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderHelp(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) throws UnknownHostException {
		HelpResponse response = new HelpResponse();
		// this.pWriter = pWriter;
		this.response = response;
		render();

		fillRdapResponse(response);

	}

}
