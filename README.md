# task-management

#### Steps to follow:

1. change database configurations 
2. run project

### APIs:
###### * Please import Task Management.postman_collection.json to your postman for easy testing.
#### signin:
* http://localhost:8081/auth/signin (POST)

{
	"username":"admin",
	"password":"password"
}

#### Create project:
* http://localhost:8081/api/createProject(POST)

header: Authorization : Bearer {{access_token}} 

body: 
		{
			 "name":"Test Project"
		}
		
#### Get all projects:
* http://localhost:8081/api/getAllProject (GET)

header: Authorization : Bearer {{access_token}} 


#### Delete project:
* http://localhost:8081/api/deleteProject/25 (DELET)

header: Authorization : Bearer {{access_token}} 

#### Create task:
* http://localhost:8081/api/createTask(POST)

header: Authorization : Bearer {{access_token}} 

body: 
{
    "description": "Test description",
    "status": "open",
    "projectId": "26",
    "dueDate":"2021-02-10"
}

#### Edit task:
* http://localhost:8081/api/editTask(POST)

header: Authorization : Bearer {{access_token}} 

body:
{
    "id":31,
    "description": "Desc2e",
    "status": "open",
    "projectId": "30",
    "dueDate":"2021-02-02"
}

#### Get task:
* http://localhost:8081/api/getTaskList (GET)

header: Authorization : Bearer {{access_token}} 

* http://localhost:8081/api/getTask/27 (GET)

header: Authorization : Bearer {{access_token}} 

 ### Search tasks
 ####  Get all by project
 * http://localhost:8081/api/getAllByProject/26 (GET)
 
 header: Authorization : Bearer {{access_token}} 

* http://localhost:8081/api/getTaskByStatus?status=open (GET)

header: Authorization : Bearer {{access_token}} 

 ####  Get expired tasks (due date in the past)
 
* http://localhost:8081/api/getExpiredTasks (GET)

header: Authorization : Bearer {{access_token}} 

 ####  By status
* http://localhost:8081/api/getTaskByStatus?status=open (GET)

header: Authorization : Bearer {{access_token}} 

 #### Get all tasks by user
* http://localhost:8081/api/getTasksByUser?username=user (GET)

header: Authorization : Bearer {{access_token}} 

 #### Get all projects by user
* http://localhost:8081/api/getProjectsByUser?username=user (GET)

header: Authorization : Bearer {{access_token}} 
