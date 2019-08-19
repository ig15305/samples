package com.example.orhp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.orhp.model.Meetup;
import com.example.orhp.model.MeetupRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class OneMoreSampleApplicationTests {

	@Autowired
	private MeetupRepository meetupRepository;

	@Test
	public void contextLoads() {
	}

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testGet() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/meetups"),
				HttpMethod.GET, entity, String.class);

		assertTrue(response.getBody().length() > 0);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	@Test
	public void testfindAll() {

		Meetup m1 = new Meetup("Java GRP", " SFO", "Nov 1st");
		Meetup m2 = new Meetup("Big data", " San Jose", "Dec 1st");
		meetupRepository.save(m1);
		meetupRepository.save(m2);
		List<Meetup> list = meetupRepository.findAll();
		list.add(m1);
		list.add(m2);

		assertEquals(4, list.size());	
	}

	@Test
	public void whenFindAllById() {
		//given
		Meetup m1 = new Meetup("Java GRP", " SFO", "Nov 21st");
		meetupRepository.save(m1);
		//when
		Meetup test = meetupRepository.findByName(m1.getName());
		//then
		assertEquals(test.getName(), m1.getName());
	}
	
	@Test
	public void testPost() {

		Meetup m1 = new Meetup("Big Data", " SFO", "Nov 21st");
		HttpEntity<Meetup> entity = new HttpEntity<Meetup>(m1, headers);

		ResponseEntity<Meetup> response = restTemplate.exchange(
				createURLWithPort("/api/meetup"),
				HttpMethod.POST, entity, Meetup.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("/api/meetup"));
	}
}
