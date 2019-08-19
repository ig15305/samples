package com.example.orhp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
	Meetup findByName(String name);
}
