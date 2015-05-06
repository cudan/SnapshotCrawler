package eu.cudan.snapshotCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SitemapDownloader {
	List<String> linkList = new ArrayList<>();
	ParameterSet param;
	boolean isRetreiveSuccess = false;

	public SitemapDownloader(ParameterSet param) {
		this.param = param;
		isRetreiveSuccess = getSitemap();
	}

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
			System.out.println("ERROR: Problem downloading Sitemap");
			return false;
		} catch (IOException e) {
			System.out.println("ERROR: Problem downloading Sitemap");
			return false;
		}
	}

	public List<String> getLinkList() {
		return linkList;
	}

	public boolean isRetreiveSuccess() {
		return isRetreiveSuccess;
	}

}
