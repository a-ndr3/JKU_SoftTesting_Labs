package at.jku.swtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpClientTest {

	@Mock
	private URL mockURL;

	/**
	 * Disadvantages of this test:
	 * 1. The test tests not a single method but both HttpClient and a method of HttpClient class
	 * 2. Content that we check on the website(title) might change therefore the test might fail even if the site is online, it will fail if the site is down as well
	 * 3. The test depends on the network connection, if the connection is not available the test will fail as well as it may take longer time to run the
	 * test if there are some network issues or the connection is slow
	 * 4. The test is not repeatable, if the website is changed the test will fail even if the code is correct
	 * 5. The test contains only one check of the title and no other checks(i.e no exceptions from HttpClient could be caught, no coverage if the page is unavailable
	 */
	@Test
	public void testGetContentOnline() throws MalformedURLException {
		HttpClient myClient = new HttpClient();
		String page = myClient.getContent(new URL("https://www.jku.at"));
		assertTrue(page.contains("<title>JKU - Johannes Kepler UniversitÃ¤t Linz</title>"));
	}

	@Test
	public void testGetContentMocked() throws IOException {

		//arrange data and mocks
		String expectedContent = "Hello Mock World!";
		mockURL = Mockito.mock(URL.class);
		HttpClient httpClient = new HttpClient();

		//mocks
		HttpURLConnection mockedHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
		when(mockURL.openConnection()).thenReturn(mockedHttpUrlConnection);

		InputStream mockedInputStream = new ByteArrayInputStream(expectedContent.getBytes());
		when(mockedHttpUrlConnection.getInputStream()).thenReturn(mockedInputStream);

		//act
		String content = httpClient.getContent(mockURL);

		//assert
		assertEquals(expectedContent, content);
	}

}
