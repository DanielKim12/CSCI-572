import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import java.util.ArrayList;
//import java.util.HashSet;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MyCrawler extends WebCrawler {
    // Patterns for filtering URLs
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(html?|pdf|png|jpe?g|gif|svg|docx?))$"); // Acceptable file types
	private final static Pattern EXCLUDED_TYPES = Pattern.compile(".*(\\.(css|js|json))$"); // Excluded file types

    // Lists to store fetch, visit, and URL data
    private static List<String[]> fetchList = new ArrayList<>();
    private static List<String[]> visitList = new ArrayList<>();
    private static List<String> urlsList = new ArrayList<>();
    // Set to store seen URLs
    // private static HashSet<String> seen = new HashSet<>();
    
    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        // Add fetch data to the fetch list
        fetchList.add(new String[]{webUrl.getURL(), String.valueOf(statusCode)});
        if (statusCode == 404 || statusCode == 403) {
            // Handle errors here, maybe log them or skip retries
            System.out.println("Error on URL: " + webUrl.getURL() + " - Status Code: " + statusCode);
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase().replaceAll(",", "_").replaceFirst("^(https?://)?(www.)?", "");
        // boolean isWithinSite = href.startsWith("https://www.nytimes.com/") || 
        //                        href.startsWith("http://www.nytimes.com/");
        boolean isWithinSite = href.startsWith("nytimes.com");
        // Add URL and status (OK or N_OK) to the map
        if (isWithinSite) {
            urlsList.add(href + ",OK");  // Directly add concatenated string
        } else {
            urlsList.add(href + ",N_OK");  // Directly add concatenated string
        }
        // boolean isOutsideSite = !seen.contains(href);
        // && isOutsideSite
        return FILTERS.matcher(href).matches() && !EXCLUDED_TYPES.matcher(href).matches() && isWithinSite;
    }

    @Override
    public void visit(Page page) {
    // Fix overflow by replacing commas in the URL with an underscore
    String url = page.getWebURL().getURL().toLowerCase().replaceAll(",", "_").replaceFirst("^(https?://)?(www.)?", "");
    String contentType = page.getContentType().split(";")[0].trim();
    int pageSize = page.getContentData().length;
    int outgoingLinks = 0;
    boolean isValid = contentType.contains("pdf") || contentType.contains("image") 
            || contentType.contains("doc") || contentType.contains("html");

    if (!isValid)
        return;
    
    if (page.getParseData() instanceof HtmlParseData) {
        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        Set<WebURL> links = htmlParseData.getOutgoingUrls();
        outgoingLinks += links.size();
    }
        visitList.add(new String[]{url, String.valueOf(pageSize), String.valueOf(outgoingLinks), contentType});
    }   

    public static void exportDataToCSV() throws IOException {
        // Export fetch.csv
        FileWriter fetchCSV = new FileWriter("fetch_nytimes.csv");
        fetchCSV.append("URL,Status Code\n");
        for (String[] fetchData : fetchList) {
            fetchCSV.append(String.join(",", fetchData)).append("\n");
        }
        fetchCSV.flush();
        fetchCSV.close();

        // Export visit.csv
        FileWriter visitCSV = new FileWriter("visit_nytimes.csv");
        visitCSV.append("URL,Size(Bytes),# of OutLinks,Content Type\n");
        for (String[] visitData : visitList) {
            visitCSV.append(String.join(",", visitData)).append("\n");
        }
        visitCSV.flush();
        visitCSV.close();

     // Export urls.csv using HashMap
        FileWriter urlsCSV = new FileWriter("urls_nytimes.csv");
        urlsCSV.append("URL,Status\n");
        for (String urlData : urlsList) {
            urlsCSV.append(urlData).append("\n");  // Directly write the concatenated string
        }
        urlsCSV.flush();
        urlsCSV.close();
    }
}
