#Snapshot Crawler
##Snapshot Crawler can be used to make a HTML Snapshot of an AJAX Web Site for Example if build with GWT.

Usage: java -jar localCrawler.jar [OPTION]... [SITEMAP-LINK]... [POST-LINK]

OPTIONS:

| Option     | Description  |
| -----------|--------------|
| --help               | Display this help  |
| --sitemap            | Followed by an XML Sitemap  |
| --postlink           | Followed by the path where the HTML Snapshot is POSTED  |
                       | $_POST[link] contains the retreived Website
                       $_POST[html] the HTML Snapshot itself--outdir
                       Followed by the folder, where the snapshots are saved locally |
| --log                | File, where log about work is saved  |
| --compress           | Removes White Spaces in HTML Snapshot  |
| --concurrent         | Number of Threads running contemporarily calculating the HTML Snapshots  |
| --waitchange         | Time to wait where JS execution is not changing the snapshot anymore. default 30s.  |
| --harddeadline       | Deadline for Snapshot Thread in seconds. Default 120s.Retreiving Sitemap:   |
