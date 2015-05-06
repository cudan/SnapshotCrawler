package eu.cudan.snapshotCrawler;

public class HtmlSnapshotter {

	private ParameterSet params;
	private String url;
	private String htmlSnapshot="";
	
	public HtmlSnapshotter(ParameterSet params, String url) {
		this.params=params;
		this.url=url;
	}

	public String getUrl() {
		return url;
	}

	public String getHtmlSnapshot() {
		return htmlSnapshot;
	}

	
	
	
	

}
