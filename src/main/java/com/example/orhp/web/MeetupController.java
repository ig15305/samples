package com.example.orhp.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orhp.model.Meetup;
import com.example.orhp.model.MeetupRepository;
import com.example.orhp.model.User;
import com.example.orhp.model.UserRepository;

@RestController
@RequestMapping("/api")
class MeetupController {

	private final Logger log = LoggerFactory.getLogger(MeetupController.class);
	private MeetupRepository meetupRepository;
	private UserRepository userRepository;


	public MeetupController(MeetupRepository meetupRepository, UserRepository userRepository) {
		this.meetupRepository = meetupRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/meetups")
	List<Meetup> meetups() {
		return meetupRepository.findAll();
	}

	@GetMapping("/users")
	List<User> users() {
		return userRepository.findAll();
	}

	@GetMapping("/meetup/{id}")
	ResponseEntity<?> getMeetup(@PathVariable Long id) {
		Optional<Meetup> meetup = meetupRepository.findById(id);
		return meetup.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/meetup/{id}/users")
	ResponseEntity<List<User>> getUsers(@PathVariable Long id) {
		Optional<Meetup> meetup = meetupRepository.findById(id);
		return meetup.map(response -> ResponseEntity.ok().body(response.getAttendees()))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/meetup")
	ResponseEntity<Meetup> createMeetup(@Valid @RequestBody Meetup meetup) throws URISyntaxException {
		log.info("Request to create meetup: {}", meetup);
		Meetup result = meetupRepository.save(meetup);
		return ResponseEntity.created(new URI("/api/meetup/" + result.getId()))
				.body(result);
	}

	@PutMapping("/meetup/{id}")
	ResponseEntity<Meetup> updateMeetup(@Valid @RequestBody Meetup meetup) {
		log.info("Request to update meetup: {}", meetup);
		@Valid Meetup result = meetupRepository.save(meetup);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/meetup/{id}")
	public ResponseEntity<?> deleteMeetup(@PathVariable Long id) {
		log.info("Request to delete meetup: {}", id);
		meetupRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
