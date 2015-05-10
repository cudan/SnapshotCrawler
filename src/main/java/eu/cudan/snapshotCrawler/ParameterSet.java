package eu.cudan.snapshotCrawler;


/**
 * Parameters for the configuration of the SnapshotCrawler
 * 
 * @author cudan
 *
 */
/**
 * @author cudan
 *
 */
/**
 * @author cudan
 *
 */
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
	
	
	/**
	 * @return URL of the XML Sitemap to download
	 */
	public String getSitemap() {
		return sitemap;
	}
	
	/**
	 *
	 * @param sitemap URL of the XML Sitemap to download
	 * 
	 */
	public void setSitemap(String sitemap) {
		this.sitemap = sitemap;
	}
	
	
	/**
	 * @return String Added to all the URLs to inform your Webserver.
	 */
	public String getAddurloption() {
		return addurloption;
	}
	/**
	 * @param addurloption String to append to your URLs before download HTML Snapshot
	 */
	public void setAddurloption(String addurloption) {
		this.addurloption = addurloption;
	}
	
	/**
	 * @return single URL to make a HTML Snapshot 
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url set the URL to make a single HTML Snapshot
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return The local path where the HTML Snapshots are stored 
	 */
	public String getOutPath() {
		return outPath;
	}
	/**
	 * Set the local path where HTML Snapshots are stored
	 * @param outPath
	 */
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	/**
	 * Get URL where the HTML Snapshot is posted
	 * @return URL
	 */
	public String getPostUrl() {
		return postUrl;
	}
	/**
	 * Set the URL where the HTML Snapshots are called with POST Message to your Server
	 * @param postUrl 
	 */
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	
	/**
	 * @return Location and Name of the HTML Log File
	 */
	public String getLog() {
		return log;
	}
	/**
	 * @param log set the Location and Name of the HTML Log File
	 */
	public void setLog(String log) {
		this.log = log;
	}
	
	/**
	 * @return true, if the HTML Snapshots should be compressed
	 */
	public boolean isCompress() {
		return compress;
	}
	
	/**
	 * Set compress true, if the HTML Snapshots should be reduced in size
	 * @param compress
	 */
	public void setCompress(boolean compress) {
		this.compress = compress;
	}
	
	/**
	 * Get Number of Instances to calculate HTML Snapshots
	 * @return Number of Instances
	 */
	public int getConcurrent() {
		return concurrent;
	}
	
	/**
	 * Set Number of Instances that contemporarily calculate HTML Snapshots
	 * @param concurrent Number of Instances
	 */
	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}
	
	/**
	 * Get duration in seconds, the SnapshotCrawler waits for no change in the HTML Snapshot to assume it is complete.
	 * @return duration in seconds
	 */
	public int getWaitChange() {
		return waitChange;
	}
	
	/**
	 * Set Number of seconds, the SnapshotCrawler waits for no change in the HTML Snapshot to assume it is complete. Default: 120s
	 * @param waitChange Number of seconds
	 */
	public void setWaitChange(int waitChange) {
		this.waitChange = waitChange;
	}
	
	/**
	 * @return duration in seconds, the Snapshot Crawler waits until it forces an Instance to shut down. Default 120s
	 */
	public int getDeadline() {
		return deadline;
	}
	

	/**
	 * Set duration in seconds, the Snapshot Crawler waits until it forces an Instance to shut down. Default 120s.
	 * @param deadline in seconds
	 */
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	
	
	
	
	
	
	
	
}
