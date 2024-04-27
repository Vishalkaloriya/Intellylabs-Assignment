# Running the UI and Backend Code

This guide provides step-by-step instructions to run the UI (frontend) and backend code for the interactive application.

## Prerequisites

Before running the project, make sure you have the following installed on your system:

- Oracle OpenJDK 17.0.10
- Intellij IDEA (or any preferred IDE)
- Maven 4.0.0
- MySQL database server

# Backend Setup

1. **Clone the Repository:**
   ```bash
   git clone <repository-url>


## Navigate to the Backend Directory:
1. **Clone the Repository:**
   ```bash
   cd backend

## Open the Backend Project in IntelliJ IDEA:

- Launch IntelliJ IDEA.
- Select "Open" from the main menu.
- Navigate to the backend directory and select it.

## Configure the Database:
- Open src/main/resources/application.properties.
- Configure the database connection properties, including URL, username, and password.
- Run the Spring Boot Application:
- Run the main class (Application.java) to start the Spring Boot application.

# Frontend Setup:-

## Navigate to the Frontend Directory:
1. **Clone the Repository:**
   ```bash
   cd frontend

## Install Dependencies:
- npm install
## Start the React Development Server:
- npm start
## Access the Application:
- Open your web browser and navigate to http://localhost:3000.

# Usage
## Registration
- Open the application in your browser.
- Navigate to the registration page.
- Fill in the required details in the registration form and submit.

## Login
- Navigate to the Login page.
- Enter the registered email and password and submit.

# Testing
## Backend Unit Tests
- Open the backend project in IntelliJ IDEA.
- Navigate to the test directory (src/test/java).
- Run the unit test cases using your preferred test runner.

## Frontend UI Tests
- Navigate to the frontend directory.
- Run the UI test cases using your preferred testing framework.

# Additional Notes
- Ensure that MySQL database server is running before starting the backend application.
- You may need to adjust database connection properties in the backend 'application.properties'  file according to your MySQL setup.
- Feel free to customize and modify the code as per your requirements.

## Screenshots

![Screenshot 1](https://res.cloudinary.com/dmekxgazq/image/upload/v1714119412/Screenshot_2024-04-26_130343_gyjh5i.png)
*Registration*

![Screenshot 2](https://res.cloudinary.com/dmekxgazq/image/upload/v1714119412/Screenshot_2024-04-26_130256_rylbwr.png)
*Login*

