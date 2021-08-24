Feature: Student Application Frame
	Specifications of the behavior of the Student Application Frame
	
	Scenario: The initial state of the view
		Given The database contains a student with id "1" and name "first student"
		And The database contains a student with id "2" and name "second student"
		When The StudentView is shown
		Then The list contains an element with id "1" and name "first student"
		And The list contains an element with id "2" and name "second student"
