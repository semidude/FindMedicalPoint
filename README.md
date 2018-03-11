# FindMedicalPoint
## Team
 - Piotr Chachuła
 - Olaf Dygas
 - Radosław Panuszewski
 
 ## About the project
 The purpose of our solution is to provide an oppurtunity to find the most suitable medical point based on users' location (or any other given) and medical specialization that fulfill their needs.
 
 Our REST API could also be utilized in variety of other solutions. 
 Example usage:
 ```
 /findClosest?specialization=Okulista&lat=52.241629599999996&lon=20.940932999999998
 /findClosestByAddress?specialization=Ortopeda&address=Warszawa;Ksiecia+Janusza;39
 ```
 Supported countries:
 - Poland
 
 ## Implementation
 ![alt text](https://imageshack.com/a/img923/5241/0mlV8p.png)
 
 ## Compiling & Launching
 To compile and launch our application you need to:
 - Clone the repository to your local disc
 - Open the project in your favourite IDE
 - Configure Tomcat Server in your IDE
 - Set up a databese using MySQL with following parameters (it is highly recommended to change them accordingly; those can be found in src/main/resources/application.properties):
      - Database name: db_example
      - Username: springuser
      - Password: ThePassword 
 - Run the application on server
 - Run the parser (which will store parsed data in a temporary database) via /medicalPoints/parse
 - Get coordinates for each medical point stored in temporary database via /medicalPoints/sector
 - After it finishes working, in order to fill up the main database use /medicalPoints/pars
 - Go to the user or admin pages to check if everything works
