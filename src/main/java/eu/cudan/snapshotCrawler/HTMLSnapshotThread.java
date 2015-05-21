package eu.cudan.snapshotCrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

interface Callback {
	void callback(); // would be in any signature
}

public class HTMLSnapshotThread implements Runnable {

	private Callback c;
	private ParameterSet params;
	private String url;
	private String htmlSnapshot = "";

	public HTMLSnapshotThread(Callback c, ParameterSet params, String url) {
		this.params = params;
		this.url = url;
		this.c = c;
		
	}

	@Override
	public void run() {
		

		// Create a HTML Snapshot of a desired URL
		HtmlSnapshotter snapshotter = null;
for(int i=0;i<10;i++){
		System.out.println("Try.." + i);
		snapshotter = new HtmlSnapshotter(params, url);
		htmlSnapshot=snapshotter.getHtmlSnapshot();
		
		if(snapshotter.isSnapshotReady())
			break;
		
		if(i>=9){System.err.println("ERROR: Could not generate a html Snapshot of this element: " + url); this.c.callback(); return;}
}
		
		// Compress HTML if desired: remove white spaces etc.
		if (params.isCompress()) {
			HtmlCompressor compressor = new HtmlCompressor();
			compressor.setPreserveLineBreaks(true);
			htmlSnapshot = compressor.compress(htmlSnapshot);
		}

		// Save to File if desired
		saveToFile();

		// Add an Entry to Log File
		addToLogFile(snapshotter);

		// Post to a Server if desired
		PostCommitter poster = new PostCommitter(params, snapshotter.getUrl(),
				htmlSnapshot);

		this.c.callback();
	}

	private void saveToFile() {
		if(params.getOutPath().isEmpty()) return;
		
			try (PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(params.getOutPath()+"/"+URLEncoder.encode(url, "UTF-8") + "___SNAP.html", false)))) {
				out.println(htmlSnapshot);
			} catch (IOException e) {
				System.err.println("ERROR: Could not write to File");
			}

	}

	private void addToLogFile(HtmlSnapshotter snapshotter) {
		if(params.getLog().isEmpty()) return;
		
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(params.getLog(), true)))) {
			out.println(snapshotter.getSizeSnapshot() + "Bytes, "
					+ snapshotter.getElapsedTime() + "s, "
					+ snapshotter.getRepeatedTimes() + "x, "
					+ snapshotter.getUrl()+ "<br />") ;
		} catch (IOException e) {
			System.err.println("ERROR: Could not write to Log File");
		}

	}

}
