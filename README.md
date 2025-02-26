# Money Exchange Machine

## Description
This is a FullStack application that simulates a money exchange machine. The application allows users to insert money bills and receive change in coins. It supports different exchange rules, stores transaction history in MongoDB, and allows customization of the initial coin inventory. The application consists of a Spring Boot backend and a React frontend, both Dockerized.

## Features
- Exchange money bills ($1, $2, $5, $10, $20, $50, $100) for coins (1c, 5c, 10c, 25c)
- Default exchange based on the minimum number of coins (option to maximize coins)
- Maintain a coin inventory with a customizable initial stock
- Store all transactions in MongoDB
- REST API to interact with the backend
- Frontend interface built with React
- Dockerized setup for easy deployment

## Technologies Used
### Backend:
- Java 22
- Spring Boot 3
- Spring Data MongoDB
- JUnit & Mockito for testing
- Docker & Docker Compose

### Frontend:
- React
- Axios for API calls

## Setup Instructions

### Prerequisites
- Docker and Docker Compose installed
- Java 22 installed (for local backend execution without Docker)
- Node.js installed (for local frontend execution without Docker)

### Running the Application with Docker
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/money-exchange-machine.git
   cd money-exchange-machine
   ```
2. Start the application using Docker Compose:
   ```bash
   docker-compose up --build
   ```
3. The backend will be available at `http://localhost:8080`.
4. The frontend will be available at `http://localhost:3000`.

### Running the Backend Locally (Without Docker)
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Configure the `application.yml` file to set the initial coin inventory:
   ```yaml
   coin-inventory:
     initial-values:
       1: 100
       5: 100
       10: 100
       25: 100
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

### Running the Frontend Locally (Without Docker)
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the frontend application:
   ```bash
   npm start
   ```
4. Open `http://localhost:3000` in a browser.

## API Endpoints
### Exchange Money
**POST /exchange**
- Request:
  ```json
  {
    "bill": 10,
    "maxCoins": false
  }
  ```
- Response:
  ```json
  {
    "message": "Exchanged $10 successfully!",
    "statement": [
      "40 coins (25 cents) = $10"
    ]
  }
  ```

### Increase Coin Inventory
**POST /exchange/increase**
- Request:
  ```json
  {
    "1": 50,
    "5": 30
  }
  ```
- Response:
  ```json
  {
    "message": "Coins successfully added!"
  }
  ```

## Running Unit Tests
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Run the tests:
   ```bash
   mvn test
   ```

## Future Improvements ( 🤔 )
- Improve UI/UX with animations and styling
- Add user authentication and session management
- Support additional currencies
- Deploy to a cloud provider

## Contributors
- **Igor Queiroz** - All the development

## License
This project is licensed under the MIT License.


