# CourseRegistrationSystem-2.0
A Course Registration System in Java developed as a Client-Server application with a Graphical User Interface for users to interact with:
                       
- Client & Server packages adopt the Model, View, Controller architecture where the Server Model communicates with a MySQL database.
                       
- To dynamically accept clients and to improve performance, the application utilizes multithreading with a threadpool. 
Users are split into two categories: admins & students. Admins can perform CRUD operations on courses and users in order to add them to the database. Student users can register for a course and remove their registration.  

## How to Run:
    1. open a new terminal->navigate to src. compile: javac .\Server\ServerController\ServerCommunication.java then run: java Server.ServerController.ServerCommunication 
    2. open another terminal->navigate to src. complie:javac .\Client\ClientController\ClientCommunication.java then run: java Client.ClientController.ClientCommunication
    3. run as many client as you wish...
    *******************************NOTE: Server must be initialized before running client**********************************************************************************


## User Credentials
To log in as an admin->  username: addy
                          userID: 23
                          password: pop
                       
To log in as a student-> username: stu
                         userID: 8
                         password: student
                        



