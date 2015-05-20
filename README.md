#Snapshot Crawler


[Download executable JAR](https://github.com/cudan/SnapshotCrawler/blob/master/bin/SnapshotCrawler.jar?raw=true)


###Snapshot Crawler can be used to make a HTML Snapshot of an AJAX Web Site for Example if build with GWT.
This project uses [HTMLUnit](http://htmlunit.sourceforge.net/) to generate a HTML Snapshot of AJAX Websites that are asynchronously loaded using JavaScript.<br/>
Using Snapshot Crawler help you to precalculate the response for the Google robots accessing your Website trought "?_escaped_fragment_=". See the documentation [here](https://developers.google.com/webmasters/ajax-crawling/docs/specification).<br/>
You can simply pass an [XML Sitemap](https://support.google.com/webmasters/answer/183668?hl=en) as an argument and all URLs insight the "&lt;loc&gt;" Tag are retreived and snapshotted.<br/>
It also automatically calls your Webserver with a POST call containing the calculated HTML snapshot.


##Usage Examples

<b>Example 1:</b> Creates a HTML snapshot of a single page

```
java -jar snapshotCrawler.jar --url url-to-ajax-page.com --outpath /home/user/path-to-save-snapshots/ --log out.html --concurrent 10
```

<b>Example 2:</b> Creates a HTML snapshot of all links found insight the XML sitemap. The format used is the <a href="https://support.google.com/webmasters/answer/183668?hl=en">Google Webmaster Tools sitemap</a>.

```
java -jar snapshotCrawler.jar --sitemap url-to-xml-sitemap.com --outpath /home/user/path-to-save-snapshots/ --log out.html --concurrent 10
```

<b>Example 3:</b> Creates a HTML snapshot and posts the Result to your web server:

```
java -jar snapshotCrawler.jar --sitemap url-to-xml-sitemap.com --posturl your-server-servlet.com --concurrent 10
```



##Options

Usage: java -jar snapshotCrawler.jar [OPTION]... [SITEMAP-LINK]... [POST-LINK]


| Option               				| Description  |
| ----------------------------------------------|--------------|
| --help               				| Display this help  |
| --sitemap [URL]      				| http URL to an [XML Sitemap](https://support.google.com/webmasters/answer/183668?hl=en) |
| --addurloption [String]  			| Appends to every URL called from the sitemap. Useful to pass special Options to the website.|
| --url [URL]                			| http URL to make a single HTML shapshot  |
| --outpath [path]           			| Path, where the snapshots are saved locally |
| --posturl [URL]            			| Followed by the path where the HTML Snapshot is POSTED<br>$_POST[link] contains the retreived Website<br>$_POST[html] the HTML Snapshot itself--outdir |
| --log [name]                			| HTML Log File, where log about work is saved  |
| --compress            			| Removes White Spaces in HTML Snapshot  |
| --concurrent [number of instances]         	| Number of Threads running contemporarily calculating the HTML Snapshots. default 1 |
| --waitchange [seconds]        		| Time to wait where JS execution is not changing the snapshot anymore. default 30s.  |
| --deadline [seconds]				| Deadline for Snapshot Thread in seconds. Default 120s. |
