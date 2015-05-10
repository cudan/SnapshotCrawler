package eu.cudan.snapshotCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Downloads an XML Sitemap and extract the URLs to make a snapshot.<br>
	 * 
	 * The passed XML should be build in this format:<br>
	 * https://support.google.com/webmasters/answer/183668?hl=en
 * 
 * @author cudan
 *
 */
public class SitemapDownloader {
	List<String> linkList = new ArrayList<>();
	ParameterSet param;
	boolean isRetreiveSuccess = false;

	/**
	 * Downloads the XML Sitemap and retreives all URLs insight
	 * 
	 * @param param Parameters and Settings
	 */
	public SitemapDownloader(ParameterSet param) {
		this.param = param;
		isRetreiveSuccess = getSitemap();
	}

	
	/**
	 * Downloads an XML Sitemap and extract the URLs to make a snapshot.<br>
	 * 
	 * The passed XML should be build in this format:<br>
	 * https://support.google.com/webmasters/answer/183668?hl=en
	 * 
	 * @return true, if the Sitemap was downloaded successfully
	 */
	private boolean getSitemap() {

		try {
			String sitemapXML = org.apache.commons.io.IOUtils.toString(new URL(
					param.getSitemap()));

			Pattern pattern = Pattern.compile("(<loc>)(.*?)(</loc>)");
			Matcher matcher = pattern.matcher(sitemapXML);

			linkList.clear();

			while (matcher.find()) {
				linkList.add(matcher.group(2));
			}

			System.err.println("Sitemap downloaded. Items Nr.: "
					+ linkList.size());

			return true;

		} catch (MalformedURLException e) {
			System.err.println("ERROR: Problem downloading Sitemap");
			return false;
		} catch (IOException e) {
			System.err.println("ERROR: Problem downloading Sitemap");
			return false;
		}
	}

	/**
	 * @return List of URL insight the Sitemap
	 */
	public List<String> getLinkList() {
		return linkList;
	}

	/**
	 * @return true, if the Sitemap was Downloaded successfully
	 */
	public boolean isRetreiveSuccess() {
		return isRetreiveSuccess;
	}

}
