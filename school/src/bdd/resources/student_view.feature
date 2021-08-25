Feature: Student Application Frame
	Specifications of the behavior of the Student Application Frame
	
	Scenario: The initial state of the view
		Given The database contains a student with the following values
			| 1 | first student  |
			| 2 | second student |
		When The StudentView is shown
		Then The list contains elements with the following values
			| 1 | first student  |
			| 2 | second student |
		   
