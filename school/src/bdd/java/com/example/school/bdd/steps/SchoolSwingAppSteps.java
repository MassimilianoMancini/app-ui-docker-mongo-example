package com.example.school.bdd.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SchoolSwingAppSteps {
	
	private static final String STUDENT_COLLECTION_NAME = "student";
	private static final String SCHOOL_DB_NAME = "school";
	
	private static int mongoPort = Integer.parseInt(System.getProperty("mongo.port", "27017"));
	
	private MongoClient mongoClient;
	
	private FrameFixture window;
	
	@Before
	public void setUp() {
		mongoClient = new MongoClient(new ServerAddress("localhost", mongoPort));
		mongoClient.getDatabase(SCHOOL_DB_NAME).drop();
	}
	
	@After
	public void tearDown() {
		mongoClient.close();
		// the window might be null if the step for showing the view fails or it's not executed
		if (window != null) {
			window.cleanUp();
		}
	}
	
	
	@Given("The database contains a student with id {string} and name {string}")
	public void the_database_contains_a_student_with_id_and_name(String id, String name) {
		mongoClient
			.getDatabase(SCHOOL_DB_NAME)
			.getCollection(STUDENT_COLLECTION_NAME)
			.insertOne(
				new Document()
					.append("id", id)
					.append("name", name));

	}
	
	
	@When("The StudentView is shown")
	public void the_student_view_is_shown() {
		application("com.example.school.app.swing.SchoolSwingApp")
		.withArgs("--mongo-port=" + mongoPort, "--db-name=" + SCHOOL_DB_NAME, "--db-collection=" + STUDENT_COLLECTION_NAME).start();
		// get a reference of its JFrame
		// get reference of its JFrame
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Student View".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(BasicRobot.robotWithCurrentAwtHierarchy());
	}
	
	@Then("The list contains an element with id {string} and name {string}")
	public void the_list_contains_an_element_with_id_and_name(String id, String name) {
	    assertThat(window.list().contents()).anySatisfy(e -> assertThat(e).contains(id, name));
	}

}
