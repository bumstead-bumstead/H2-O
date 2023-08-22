package com.h2o.h2oServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class H2oServerApplication {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(H2oServerApplication.class, args);
	}

	@GetMapping("/ping")
	public ResponseEntity ping() {
		return ResponseEntity.ok("pong");
	}

	@GetMapping("/redis-ping")
	public ResponseEntity redisPing() {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set("testKey", "testValue");

		return new ResponseEntity<>(HttpStatus.OK);
	}
}

