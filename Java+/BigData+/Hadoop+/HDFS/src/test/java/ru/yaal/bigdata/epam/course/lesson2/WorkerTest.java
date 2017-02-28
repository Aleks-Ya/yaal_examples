package ru.yaal.bigdata.epam.course.lesson2;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;

import org.junit.Ignore;
import org.junit.Test;

public class WorkerTest {

	@Test
	@Ignore
	public void test() throws Exception {
		final String data = "a74dc89907ff5ed9a843dc65122164d4	20130314150002729	dd6418a0bbe02d76144846ae92d476a6	Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.2; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/),gzip(gfe),gzip(gfe)	114.99.72.*	106	114	2	trqRTvuyMTKR1m58uG	4cc4a87208d20c91f1e10db0388f6ac		2856557484	250	250	2	0	5	3a0cf3767556609a1f4329c9f52e387e	300"
				+ "\n"
				+ "7abee2ef20fdf4ba26e0681211a4ddfa	20130314150002738	bd9bff7e33f64543cc25d9e54d65c217	Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0,gzip(gfe),gzip(gfe)	114.253.233.*	1	1	2	trqRTu50GQk7gspy5SqW	70c0b6467fb12d5e55c14aa0bd89ebd2		2062661011	336	280	1	0	5	ca165a0bf41928c7ec45df6c2745cf6e	300"
				+ "\n"
				+ "3b0857a041c8bb8e386fa6d08d9e4136	20130314150002729	37a6259cc0c1dae299a7866489dff0bd	Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; Trident/5.0),gzip(gfe),gzip(gfe)	58.16.89.*	298	299	2	trqRTuMyBqxYFYqWJJurN1TIm9mn	cf85736279dcbff5702479752641e2b		1836185084	300	250	2	0	5	d683498a1e531a0f3621d2f7b0a9dcf7	300";
		ByteArrayInputStream is = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		Charset charset = Charset.forName("UTF-8");
		Worker worker = new Worker(is, charset);
		final Result result = worker.call();
		final Map<String, Integer> map = result.getIdCountMap();

		assertThat(map, aMapWithSize(2));
		assertThat(map, hasEntry("20130314150002729", 2));
		assertThat(map, hasEntry("20130314150002738", 1));
	}

}
