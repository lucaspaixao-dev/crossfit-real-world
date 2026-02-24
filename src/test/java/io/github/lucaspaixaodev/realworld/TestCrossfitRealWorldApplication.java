package io.github.lucaspaixaodev.realworld;

import org.springframework.boot.SpringApplication;

public class TestCrossfitRealWorldApplication {

	public static void main(String[] args) {
		SpringApplication.from(CrossfitRealWorldApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
