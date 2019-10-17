package kr.gringrape.hamp;

import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.domain.MeetingRepository;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HampApplication {

	public static void main(String[] args) {

		SpringApplication.run(HampApplication.class, args);

	}
}
