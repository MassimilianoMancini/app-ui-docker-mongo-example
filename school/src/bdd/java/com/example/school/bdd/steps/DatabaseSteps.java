package com.example.school.bdd.steps;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class DatabaseSteps {

	private static final String STUDENT_COLLECTION_NAME = "student";
	private static final String SCHOOL_DB_NAME = "school";

	private static int mongoPort = Integer.parseInt(System.getProperty("mongo.port", "27017"));

	private MongoClient mongoClient;

	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress("localhost", mongoPort));
		mongoClient.getDatabase(SCHOOL_DB_NAME).drop();
	}

	@After
	public void tearDown() {
		mongoClient.close();
	}

	@Given("The database contains the students with the following values")
	public void the_database_contains_a_student_with_the_following_values(List<List<String>> values) {
		values.forEach(v -> mongoClient.getDatabase(SCHOOL_DB_NAME).getCollection(STUDENT_COLLECTION_NAME)
				.insertOne(new Document().append("id", v.get(0)).append("name", v.get(1))));
	}

}
