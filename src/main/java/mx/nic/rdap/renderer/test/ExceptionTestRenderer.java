package mx.nic.rdap.renderer.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import mx.nic.rdap.renderer.DevNullOutputStream;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.object.ExceptionResponse;

/**
 * Creates an {@link ExceptionResponse} and render the response with the given
 * renderer instance.
 * 
 * Each attribute of the object is set one by one, and each time an attribute is
 * set, the Response is rendered to validate that the renderer does not throw an
 * {@link NullPointerException}.
 *
 */
public class ExceptionTestRenderer extends CommonRenderer {

	private PrintWriter pWriter;
	private ExceptionResponse response;
	private Renderer renderer;

	/**
	 * Verify that the renderer, render an {@link ExceptionResponse}
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
		renderer.renderException(response, new PrintWriter(out));
		System.out.println(out.toString());
	}

	@Override
	protected void render() {
		renderer.renderException(response, pWriter);
	}

	private void createRequestResponse(Renderer renderer) throws UnknownHostException {
		String nullString = null;
		ExceptionResponse response = new ExceptionResponse(nullString, null, null);
		// this.pWriter = pWriter;
		this.response = response;
		render();

		response.setErrorCode("501");
		render();

		response.setErrorTitle("some error title");
		render();

		List<String> errorDescriptions = new ArrayList<>();
		response.setDescription(errorDescriptions);
		render();

		errorDescriptions.add("first err desc");
		render();

		errorDescriptions.add("second err desc");
		render();

		fillRdapResponse(response);

	}

}
