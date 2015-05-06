package eu.cudan.snapshotCrawler;

public class ParameterSet {
	private  String sitemap="";
	private String addurloption="";
	private  String url="";
	private  String outPath="";
	private  String postUrl="";
	private  String log="";
	private  boolean compress=false;
	private  int concurrent=1;
	private  int waitChange=30;
	private  int deadline=120;
	public String getSitemap() {
		return sitemap;
	}
	public void setSitemap(String sitemap) {
		this.sitemap = sitemap;
	}
	
	
	public String getAddurloption() {
		return addurloption;
	}
	public void setAddurloption(String addurloption) {
		this.addurloption = addurloption;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOutPath() {
		return outPath;
	}
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public boolean isCompress() {
		return compress;
	}
	public void setCompress(boolean compress) {
		this.compress = compress;
	}
	public int getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}
	public int getWaitChange() {
		return waitChange;
	}
	public void setWaitChange(int waitChange) {
		this.waitChange = waitChange;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	
	
	
	
	
	
	
	
}
