package com.bwma.msc;

import com.bwma.msc.cms.webClient.content.MovieWebClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MovieSeriesTrackerApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger( MovieSeriesTrackerApplicationTests.class );
	private final MovieWebClient webClient;

	@Autowired
	MovieSeriesTrackerApplicationTests(MovieWebClient webClient) {
		this.webClient = webClient;
	}


	@Test
	void contextLoads() {
		List<Integer> idList = this.webClient.getLatestAdded();

		logger.info(idList.toString());
	}



}
