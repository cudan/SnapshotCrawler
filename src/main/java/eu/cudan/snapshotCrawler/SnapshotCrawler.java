package eu.cudan.snapshotCrawler;

public class SnapshotCrawler {

	private static ParameterSet param=new ParameterSet();
	static int numberOfThreads = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		analyzeArgs(args);		// Passing the Arguments to SnapshotCrawler
		snapshotURL();			// Make a Snapshot if a single URL is passed
		snapshotSitemap();		// Make the Snapshots of all URL insight <loc> tag insight the XML sitemaps

	}

	
	/**
	 * Taking a HTML Snapshot of an XML Sitemap. The passed XML should be build in this format:
	 * https://support.google.com/webmasters/answer/183668?hl=en
	 * 
	 * @return true, if the Snapshots where created successfully.
	 */
	private static boolean snapshotSitemap() {
		if (!param.getSitemap().isEmpty()) {
			System.out.println("Download Sitemap: " + param.getSitemap());

			SitemapDownloader sitemapDownloader = new SitemapDownloader(param);
			if (!sitemapDownloader.isRetreiveSuccess) {
				return false;		// Problem downloading Sitemap
			}
			// Sitemap Retreived successfully
			

			for (int i = 0; i < sitemapDownloader.getLinkList().size(); i++) {

				numberOfThreads++;
				System.out.println("Starting Thread Nr.:" + numberOfThreads);
				System.out.println("Making Snapshot of Sitemap: "
						+ sitemapDownloader.getLinkList().get(i));
				
				// Callback when Threads finish running
				Callback c=new Callback() {
					
					@Override
					public void callback() {
						numberOfThreads--;
					}
				};
				
				// Start a Thread to calculate a HTML Snapshot
				Runnable r = new HTMLSnapshotThread(c,param, sitemapDownloader
						.getLinkList().get(i));
				new Thread(r).start();

				// Checking if the maximum number of Threads is reached, then wait for them to finish
				while (numberOfThreads >= param.getConcurrent()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}
		return true;
	}


	/**
	 * @return true, if the Snapshot of the passed URL was successful
	 */
	private static boolean snapshotURL() {
		if (!param.getUrl().isEmpty()) {
			System.out.println("Making Snapshot of URL: " + param.getUrl());
			
			// Called when the HTML Snapshot is ready
			Callback c=new Callback() {
				
				@Override
				public void callback() {
					numberOfThreads--;
				}
			};
			
			// Start a Thread to calculate a HTML Snapshot
			Runnable r = new HTMLSnapshotThread(c,param, param.getUrl());
			new Thread(r).start();
			
			
		}
		return true;

	}


	/**
	 * Passing the Command Line Arguments to the Snapshot Crawler 
	 * 
	 * @param args: commandline arguments passed to the SnapshotCrawler
	 */
	private static void analyzeArgs(String[] args) {

		if (args.length != 0) {
			try {
				for (int i = 0; i < args.length; i++) {
					switch (args[i]) {
					
					// print help to command line
					case "--help":
						printHelp();
						return;

					// Create HTML Snapshots of all urls insight the XML Sitemap
					case "--sitemap":
						param.setSitemap(args[++i]);
						break;

					// Add parameters to every URL to inform your Website about the crawler. Example: ?snapshotCrawler=true
					case "--addurloption":
						param.setAddurloption(args[++i]);
						break;
						
					// Create HTML Snapshot of a single URL
					case "--url":
						param.setUrl(args[++i]);
						break;
						
					// Path, where the HTML Snapshots sould be saved locally.
					case "--outpath":
						param.setOutPath(args[++i]);
						break;
						
					// URL of your Web Server, where the HTML Snapshot is passed by POST method
					case "--posturl":
						param.setPostUrl(args[++i]);
						break;

					// HTML File where the log of the execution is saved
					case "--log":
						param.setLog(args[++i]);
						break;
						
					// Removes empthy spaces in the calculated HTML Snapshot to reduce size 
					case "--compress":
						param.setCompress(true);
						break;
						
					// Number of Threads calculating HTML Snapshots contemporarily
					case "--concurrent":
						param.setConcurrent(Integer.valueOf(args[++i]));
						break;
						
					// Number of seconds the Snapshotter is waiting for no change until it considers the HTML Snapshot as ready. Default 30s.
					case "--waitchange":
						param.setWaitChange(Integer.valueOf(args[++i]));
						break;
						
					// Number of seconds until the Thread shutted down. Default 120s
					case "--deadline":
						param.setDeadline(Integer.valueOf(args[++i]));
						break;

					// For Invalid parameters
					default:
						System.err.println("ERROR: Invalid Arguments");
						printHelp();
						return;
					}
				}
			} catch (NumberFormatException e) {
				System.err.println("ERROR: Arguments wrong");
				printHelp();
			}
		} else {
			System.err.println("ERROR: Arguments needed");
			printHelp();
		}

	}

	/**
	 *  Prints help to the Command Line
	 */
	private static void printHelp() {
		System.out
				.println("Please visit https://github.com/lukasobl/SnapshotCrawler/ to see how to use this tool.");
	}

}
