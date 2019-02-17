import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Set;
import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
class MyTest {
	
	private ArrayList<Page> pages = new ArrayList<Page>();
	private Page page = new Page(null);
	private MyCrawler myCrawler = new MyCrawler();
	private HtmlParseData htmlParseData = new HtmlParseData();
	private TextParseData testParseData = new TextParseData();
	private CrawlController controller;
	private CrawlConfig config;
	
	@BeforeEach
	public void setUp() {
		String crawlStorageFolder = "data/";
		config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder); 
		config.setIncludeBinaryContentInCrawling(false);
		config.setMaxDepthOfCrawling(-1);
		config.setMaxOutgoingLinksToFollow(5000);
		PageFetcher pageFetcher = new PageFetcher(config);   
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			myCrawler.init(0, controller);
			WebURL curURL = new WebURL();
			curURL.setURL("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Machine_learning.html");
			myCrawler.processPage(curURL);
			pages = myCrawler.getPages();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	// 1. 

	@Test
	public void lowerCaseTestForContent() throws Exception{
		
		String testString = "Aaaa";
		
		testParseData.setTextContent(testString);
	
		assertEquals(testString, testParseData.getTextContent());
	
	}
	
	@Test
	public void testForContentWhenTheTextPageHaveNumber() throws Exception {
		
		double num = 0.01;
		
		String testString = Double.toString(num);
		
		testParseData.setTextContent(testString);
	
		assertEquals(testString, testParseData.getTextContent());
		

	}
	
	
	@Test
	public void lowerCaseTestForHtml() throws Exception{
		
		String testString = "Aaa";
		
		htmlParseData.setHtml(testString);
		
		assertEquals(testString, htmlParseData.getHtml());
		
	}
	
	@Test
	public void lowerCaseTestForTest() throws Exception{
		
		String testString = "Aaa";
	
		htmlParseData.setText(testString);
		
		assertEquals(testString, htmlParseData.getText());
		
	}
	
	// 2.
	@Test
	public void shouldViewTest() throws Exception{
		
		page = pages.get(0);
		
		WebURL url = new WebURL();
		
		url.setURL("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2private/IDA.html");
		
		assertFalse(myCrawler.shouldVisit(page, url));

	}
	
	@Test
	public void NumOfOutLinkTest() throws Exception{
		
		page = pages.get(0);
		
		htmlParseData = (HtmlParseData) page.getParseData();
		 
		Set<WebURL> links = htmlParseData.getOutgoingUrls();
		 
		assertEquals(6, links.size());
		
	}
	
	@Test 
	public void MaxOutgoingLinksToFollow() throws Exception{
		
		assertEquals(5000, config.getMaxOutgoingLinksToFollow());
		
	}
	
	@Test
	public void testForOriginalShouldVisit() throws Exception{
		
		WebURL url = new WebURL();
		
		url.setURL("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2private/IDA.html");
		
		myCrawler.shouldVisit(page, url);
		
		WebCrawler webCrawler = new WebCrawler();
		
		webCrawler.init(0, controller);
		
		webCrawler.shouldVisit(page, url);
		
		assertSame(myCrawler.shouldVisit(page, url), webCrawler.shouldVisit(page, url));
		
	}
	
}
