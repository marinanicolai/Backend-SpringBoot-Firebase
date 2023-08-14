package com.healthApp.springboot;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableScheduling
public class SpringbootApplication {

	public static void main(String[] args) throws IOException {

		ClassLoader classLoader = SpringbootApplication.class.getClassLoader();
		InputStream serviceAccountStream = classLoader.getResourceAsStream("serviceAccountKey.json");
		System.out.println(serviceAccountStream);
		System.out.println("Hello World");
		LocalDate currentDate = LocalDate.now();
		System.out.println("Current Date: " + currentDate);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
				.setDatabaseUrl("https://healthapp-7f47c.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
