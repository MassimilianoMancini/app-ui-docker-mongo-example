Feature: Student Application Frame
	Specifications of the behavior of the Student Application Frame
	
	Scenario: The initial state of the view
		Given The database contains the students with the following values
			| 1 | first student  |
			| 2 | second student |
		When The Student View is shown
		Then The list contains elements with the following values
			| 1 | first student  |
			| 2 | second student |
			
	Scenario: Add a new student
		Given The Student View is shown
		When The user enters the following values in the text fields
			| id | name          |
			|  1 | a new student |
		And The user clicks the "Add" button
		Then The list contains elements with the following values
			|  1 | a new student |
			
		Scenario: Add a new student with an existing id
			Given The database contains the students with the following values
				| 1 | first student  |
			And The Student View is shown
			When The user enters the following values in the text fields
				| id | name          |
				|  1 | a new student |
			And The user clicks the "Add" button
			Then An error is shown containing the following values
				| 1 | first student  |
				
		Scenario: Delete a student
			Given The database contains the students with the following values
				| 1 | first student  |
				| 2 | second student |
			And The Student View is shown
			When The user select the student with id "2"
			And The user clicks the "Delete Selected" button
			Then The list contains elements with the following values
				| 1 | first student  |
			
			
		   
