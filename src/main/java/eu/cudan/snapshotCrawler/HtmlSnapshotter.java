package eu.cudan.snapshotCrawler;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlSnapshotter {

	private ParameterSet params;
	private String url;
	private String htmlSnapshot="";
	private boolean isSnapshotReady=false;
	
	Long elapsedTime=0L;
	long repeatedTimes=0;
	long sizeSnapshot=0;
	
	
	
	private WebClient webClient = null;

	private static final long timePumpJavascriptMilliSeconds = 300;
	private static final long timeJavascriptTimeout = 1000;


	
	public HtmlSnapshotter(ParameterSet params, String url) {
		this.params=params;
		this.url=url;
		
		try {
			htmlSnapshot=getSnapshot(this.url + this.params.getAddurloption());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getUrl() {
		return url;
	}

	public String getHtmlSnapshot() {
		return htmlSnapshot;
	}
	
	public boolean isSnapshotReady() {
		return isSnapshotReady;
	}





	private class SyncAllAjaxController extends NicelyResynchronizingAjaxController {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
			return true;
		}
	}
	
	
	private String getSnapshot(String url) throws FailingHttpStatusCodeException, IOException {


		isSnapshotReady=false;
		repeatedTimes=0;
		sizeSnapshot=0;
		
		URL urlToFetch = new URL(url);

		System.out.println("Fetching: " + urlToFetch);

		final WebRequest webRequest = new WebRequest(urlToFetch);

		// Headless browser HtmlUnit to obtain an HTML snapshot.
		webClient = new WebClient(BrowserVersion.FIREFOX_38);
		webClient.getCache().clear();

		// Set Options fot headless browser
		webClient.setJavaScriptTimeout(50000);
		webClient.getOptions().setTimeout(56000);
		webClient.getOptions().setRedirectEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);

		webClient.setAjaxController(new SyncAllAjaxController());
		webClient.setCssErrorHandler(new SilentCssErrorHandler());

		final HtmlPage page = webClient.getPage(webRequest);

		

		long previousSize=0;
		Long dateStart=System.currentTimeMillis()/1000;
		Long dateUpdate=System.currentTimeMillis()/1000;
		Long dateNow=System.currentTimeMillis()/1000;
		
		Boolean stillWait=true;
		String exitReason="";
		
		do {
			
			// Pump JS
			try {
				repeatedTimes++;
				previousSize=sizeSnapshot;
				
				webClient.getJavaScriptEngine().pumpEventLoop(timePumpJavascriptMilliSeconds);

				webClient.waitForBackgroundJavaScript(timeJavascriptTimeout);	
			} catch (Exception e) {
				// TODO: handle exception
			} 
			
			
			// Get Page Size
			try {
				sizeSnapshot=page.asXml().length();
			} catch (Exception e) {
				sizeSnapshot=0;
			}
			
			// Check if Page still is in change
			if(sizeSnapshot != previousSize) {
				
				dateUpdate=System.currentTimeMillis()/1000;
			}
			dateNow=System.currentTimeMillis()/1000;
			
			// See if the Snapshot is Ready and no change is made to the page anymore
			exitReason="";
			if(dateUpdate + params.getWaitChange() > dateNow) stillWait=true;
			else {stillWait=false;isSnapshotReady=true;exitReason+="Complete: No change for: " + params.getWaitChange() + "s";}
			if(sizeSnapshot==0) stillWait=true;
			if(dateStart + params.getDeadline() < dateNow) {stillWait=false;exitReason+="ERROR: Hard Deadline!";}
			if(repeatedTimes > 3000) {stillWait=false;exitReason+="ERROR: Repeated to often!";}

		} while (stillWait);
		
		
		elapsedTime=dateNow - dateStart;
		
		System.out.println("Size / Repeat / Time / Reason: " + sizeSnapshot + "Bytes / " + repeatedTimes + "x / " + elapsedTime + "s / " + exitReason);
		
		
		final String staticSnapshotHtml = page.asXml();

		webClient.closeAllWindows();

		return staticSnapshotHtml;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public long getRepeatedTimes() {
		return repeatedTimes;
	}

	public long getSizeSnapshot() {
		return sizeSnapshot;
	}

	
	
	
	

}
