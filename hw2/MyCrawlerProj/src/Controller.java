import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/Users/gukilkim_1/Desktop/CSCI_572/hw2/data"; // Directory to store crawl data
        int numberOfCrawlers = 15;  // Number of concurrent threads
        int maxPagesToFetch = 20000; // Limit of pages to fetch
        int maxDepthOfCrawling = 16; // Depth of crawl

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxPagesToFetch(maxPagesToFetch);
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);
        config.setPolitenessDelay(100); // Politeness delay between requests

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://www.nytimes.com/"); // Seed URL
        controller.start(MyCrawler.class, numberOfCrawlers);

        // After crawl completes, export the data to CSVs
        MyCrawler.exportDataToCSV();
        System.out.println("Crawling Finished");
    }
}

