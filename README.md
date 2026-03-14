# Spring Boot Assignment Tracker & Calendar Integration

This project is a web-based productivity tool designed to help students and professionals easily schedule tasks and assignments. It seamlessly connects a custom Java backend with the user's personal Google Calendar.

The application uses secure OAuth 2.0 authentication, allowing users to safely log in with their Google accounts and automatically insert deadlines into their agenda without leaving the app.

## Key Features

* **Secure Authentication (OAuth 2.0):** Passwordless and secure login flow using Spring Security, ensuring the application never has access to the user's actual Google password.
* **Automated Scheduling:** Connects directly to the Google Calendar API v3 to generate full-day or timed events based on the user's input.
* **RESTful Communication:** Processes HTTP requests and handles JSON data payloads between the Spring Boot backend and Google's cloud infrastructure.
* **Interactive UI:** A clean, responsive web interface built with HTML5 and Thymeleaf for smooth data entry.
* **Environment Security:** Built with best practices in mind, utilizing environment variables to protect sensitive API credentials and client secrets.

## Tech Stack

* **Language:** Java 21+
* **Framework:** Spring Boot 4.x
* **Security:** Spring Security & OAuth2 Client
* **External API:** Google API Client (Google Calendar API v3)
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Build Tool:** Maven

## Project Structure

* `models/Task.java`: The core data structure representing an assignment and its due date.
* `service/CalendarService.java`: The business logic layer handling the Google Calendar API requests and event construction.
* `controller/CalendarController.java`: The web routing controller that manages endpoints and extracts the OAuth access tokens.
* `resources/templates/index.html`: The dynamic frontend user interface.
* `application.properties`: Configuration file routing the hidden environment variables.

## Local Installation

If you want to run this application on your local machine:

### Prerequisites
You will need a Google Cloud Console account with the **Google Calendar API** enabled and your own OAuth 2.0 Client credentials.

1. Clone the repository:
```bash
git clone https://github.com/SantyMG03/TaskManager.git
cd task-calendar-manager
```

2. Set up your Environment Variables (Linux/macOS):
```bash
export GOOGLE_CLIENT_ID="your_client_id_here"
export GOOGLE_CLIENT_SECRET="your_client_secret_here"
```
(For Windows PowerShell use `$env:GOOGLE_CLIENT_ID="your_client_id_here"`)

3. Build and run the application using Maven:
```bash
./mvnw spring-boot:run
```

4. Open your browser and navigate to: `http://localhost:8080`
