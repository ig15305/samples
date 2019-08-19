package com.example.orhp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.orhp.model.Meetup;
import com.example.orhp.model.MeetupRepository;
import com.example.orhp.model.User;
import com.example.orhp.model.UserRepository;

@Component
class Initializer implements ApplicationRunner {
	//class Initializer implements CommandLineRunner {


	private final MeetupRepository repository;
	//   private final UserRepository userRepo;

	public Initializer(MeetupRepository repository) {
		this.repository = repository;
		//     this.userRepo = userRepo;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {   
		List<User> list = new ArrayList<>();
		List<Meetup> mlist = new ArrayList<>();
		User u1 = new User("101", "John");
		User u2 = new User("102", "Mark");
		User u3 = new User("103", "Emily");
		User u4 = new User("104", "Brian");

		list.add(u1);
		list.add(u2);
		Meetup m1 = new Meetup("Java Dev", "Dublin", "Aug 20th");
		Meetup m2 = new Meetup("Big Data", "San Jose", "Sept 15th");
		mlist.add(m1);
		mlist.add(m2);
		m1.setAttendees(list);
		repository.save(m1);
		repository.save(m2);

		//repository.findAll().forEach(System.out::println);
	}


}