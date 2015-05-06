package eu.cudan.snapshotCrawler;

public class SnapshotCrawler {

	private static ParameterSet param=new ParameterSet();
	static int numberOfThreads = 0;

	public static void main(String[] args) {

		analyzeArgs(args);
		snapshotURL();
		if (!snapshotSitemap()) {
			printHelp();
			return;
		}

	}

	private static boolean snapshotSitemap() {
		if (!param.getSitemap().isEmpty()) {
			System.out.println("Download Sitemap: " + param.getSitemap());

			SitemapDownloader sitemapDownloader = new SitemapDownloader(param);
			if (!sitemapDownloader.isRetreiveSuccess) {
				return false;
			}

			// Sitemap Retreived successfully

			for (int i = 0; i < sitemapDownloader.getLinkList().size(); i++) {

				numberOfThreads++;
				System.out.println("Starting Thread Nr.:" + numberOfThreads);
				System.out.println("Making Snapshot of Sitemap: "
						+ sitemapDownloader.getLinkList().get(i));
				
				Callback c=new Callback() {
					
					@Override
					public void callback() {
						numberOfThreads--;
					}
				};
				
				Runnable r = new HTMLSnapshotThread(c,param, sitemapDownloader
						.getLinkList().get(i));
				new Thread(r).start();

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

	private static void snapshotURL() {
		if (!param.getUrl().isEmpty()) {
			System.out.println("Making Snapshot of URL: " + param.getUrl());
			
			Callback c=new Callback() {
				
				@Override
				public void callback() {
					numberOfThreads--;
				}
			};
			
			Runnable r = new HTMLSnapshotThread(c,param, param.getUrl());
			new Thread(r).start();
			
			
		}

	}


	private static void analyzeArgs(String[] args) {

		if (args.length != 0) {
			try {
				for (int i = 0; i < args.length; i++) {
					switch (args[i]) {
					case "--help":
						printHelp();
						return;

					case "--sitemap":
						param.setSitemap(args[++i]);
						break;

					case "--addurloption":
						param.setAddurloption(args[++i]);
						break;
						
					case "--url":
						param.setUrl(args[++i]);
						break;
					case "--outpath":
						param.setOutPath(args[++i]);
						break;
					case "--posturl":
						param.setPostUrl(args[++i]);
						break;

					case "--log":
						param.setLog(args[++i]);
						break;
					case "--compress":
						param.setCompress(true);
						break;
					case "--concurrent":
						param.setConcurrent(Integer.valueOf(args[++i]));
						break;
					case "--waitchange":
						param.setWaitChange(Integer.valueOf(args[++i]));
						break;
					case "--deadline":
						param.setDeadline(Integer.valueOf(args[++i]));
						break;

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

	private static void printHelp() {
		System.out
				.println("Please visit https://github.com/lukasobl/SnapshotCrawler/ to see how to use this tool.");
	}

}
