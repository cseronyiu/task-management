# task-management

Steps to follow:

1. change database configurations 
2. run project

APIs:

* http://localhost:8081/auth/signin (POST)

{
	"username":"admin",
	"password":"password"
}


* http://localhost:8080/api/createProject(POST)

header: Authorization : Bearer {{access_token}} 

body: 
		{
			 "name":"Test Project"
		}

* http://localhost:8080/api/getAllProject (GET)

header: Authorization : Bearer {{access_token}} 



