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
	
	ArrayList<Page> pages = new ArrayList<Page>();
	Page page = new Page(null);
	MyCrawler myCrawler = new MyCrawler();
	HtmlParseData htmlParseData = new HtmlParseData();
	TextParseData testParseData = new TextParseData();
	CrawlConfig config;
	
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
			CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
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
		
		String testString = "Aaa";
		
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
	
//	@Test
//	public void testDataSet() throws Exception {
//		
//		WebURL curURL = new WebURL();
//		curURL.setURL("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/data.txt");
//		myCrawler.processPage(curURL);
//		pages = myCrawler.getPages();
//		page = pages.get(0);
//		System.err.println(page.getContentData());
//		TextParseData textParseData = (TextParseData) page.getParseData();
//		String text = textParseData.getTextContent();
//		String expected = "hot,0,5,2.3,6;" + 
//						  "cold,10,5,2,2;" + 
//				          "cold,1,5,2.4,88;" + 
//				          "cold,0,15,2,2;" + 
//				          "hot,2,5,2,42;" + 
//				          "hot,0,5,22,12;" + 
//				          "cold,3,2,2,2;";
//		assertSame(expected, text);
//		
//	}
	

}
