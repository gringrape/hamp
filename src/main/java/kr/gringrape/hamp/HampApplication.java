package kr.gringrape.hamp;

//@SpringBootApplication
//public class HampApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(HampApplication.class, args);
//	}
//
//}

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HampApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HampApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HampApplication.class, args);
    }

}
