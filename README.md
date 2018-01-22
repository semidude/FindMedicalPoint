# FindMedicalPoint
## Team:
 - Piotr Chachuła
 - Olaf Dygas
 - Radosław Panuszewski
 
 ## Purpose:
 The purpose of our solution is to provide the oppurtunity to find the most suitable medical point based on users' location or any other given, and specialization that fulfill their needs.
 Open architecture of our application also could serve as an API for NFZ's servers.
 
 ## Implementation:
 ![alt text](https://imageshack.com/a/img923/5241/0mlV8p.png)
 
 ## Compiling & Launching
 To compile and launch our application you need to:
 - Clone the repository to your local disc
 - Open the project in your favourite IDE
 - Configure Tomcat Server in your IDE
 - Set up a databese using MySQL with following parameters:
      - Database name: db_example
      - Username: springuser
      - Password: ThePassword 
 - Run the application on server
 - In order to run the parser you need to go to the address /medicalPoints/parse
 - In order to localize medical points in temporary database go to /medicalPoints/sector
 - After it finishes working, in order to fill the main databese go to /medicalPoints/pars
 
