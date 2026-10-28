# Smart E-Commerce Support Ticket Classifier

An AI-powered customer support ticket classification system built with **Java, Spring Boot, LangChain4j, and Ollama**. The application analyzes customer messages, identifies the type of issue, determines its priority and sentiment, and generates a suggested response for the support team.

The project runs an LLM locally through Ollama, so it does not require a cloud AI API key.

## 🚀 Features

- **Automatic Ticket Classification:** Categorizes customer messages into order delays, refunds, technical issues, spam, or other requests.
- **Priority Detection:** Assigns LOW, MEDIUM, or HIGH priority.
- **Sentiment Analysis:** Identifies whether the customer's tone is HAPPY, NEUTRAL, or ANGRY.
- **AI-Generated Responses:** Drafts polite and empathetic replies for support agents.
- **REST API:** Exposes endpoints for ticket analysis and application health checks.
- **Local AI Processing:** Uses Ollama to run a language model on your machine.
- **Input Validation:** Validates email addresses and ticket messages.
- **Error Handling:** Provides structured error responses for invalid inputs and AI service failures.
- **Maven Build:** Uses a standard Maven project structure for development and execution.

## 🛠️ Technology Stack

| Technology              | Purpose                             |
| ----------------------- | ----------------------------------- |
| Java 17+                | Core programming language           |
| Spring Boot 3.3.4       | REST API and application framework  |
| Maven                   | Dependency management and build     |
| LangChain4j 0.33.0      | Integration with the language model |
| Ollama                  | Local LLM runtime                   |
| Llama 3.2               | Default language model              |
| Jackson                 | Parsing AI-generated JSON           |
| Jakarta Bean Validation | Request validation                  |
| Postman / cURL          | API testing                         |

## 🏗️ Project Architecture

```text
support-classifier-ollama/
├── src/
│   └── main/
│       ├── java/com/example/supportclassifier/
│       │   ├── SupportClassifierApplication.java
│       │   ├── config/
│       │   │   └── AIConfig.java
│       │   ├── controller/
│       │   │   ├── TicketController.java
│       │   │   └── ApiExceptionHandler.java
│       │   ├── dto/
│       │   │   ├── TicketRequest.java
│       │   │   └── TicketAnalysis.java
│       │   └── service/
│       │       ├── TicketAnalysisService.java
│       │       ├── OllamaUnavailableException.java
│       │       └── InvalidModelResponseException.java
│       └── resources/
│           └── application.properties
├── pom.xml
├── run.bat
├── run.sh
└── README.md
```

### How it works

1. A customer support message is submitted through the REST API.
2. Spring Boot validates the incoming request.
3. The service sends a structured prompt to the local Ollama model through LangChain4j.
4. The model analyzes the message and generates a JSON response.
5. Jackson parses the model output, and the application validates the classification values.
6. The API returns the category, priority, sentiment, and suggested reply as JSON.

## ⚙️ Prerequisites

Install the following software before running the application:

- [Java JDK 17 or later](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Ollama](https://ollama.com/download)
- [Git](https://git-scm.com/) (optional, for cloning the repository)
- [Postman](https://www.postman.com/) (optional, for testing the API)

Verify Java and Maven:

```bash
java -version
mvn -version
```

## 📥 Installation and Setup

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/support-classifier-ollama.git
cd support-classifier-ollama
```

Replace `YOUR_USERNAME` with your GitHub username.

### 2. Download the Ollama model

Make sure Ollama is installed, then run:

```bash
ollama pull llama3.2
```

Verify that the model is available:

```bash
ollama list
```

### 3. Start Ollama

Ollama normally runs as a background service after installation. If it is not running, start it in a separate terminal:

```bash
ollama serve
```

The default Ollama API address is:

```text
http://localhost:11434
```

### 4. Build and run the Spring Boot application

Run these commands from the project root:

```bash
mvn clean package
mvn spring-boot:run
```

Alternatively, execute the packaged JAR:

```bash
java -jar target/support-classifier.jar
```

On Windows, you can also use:

```bat
run.bat
```

On Linux or macOS:

```bash
chmod +x run.sh
./run.sh
```

Once started, the application runs at:

```text
http://localhost:8080
```

**Note:** The first Maven build requires internet access to download project dependencies. Ollama must also be running, with the configured model available, for AI-powered analysis to work.

## 🔌 API Documentation

### 1. Health Check

Checks whether the Spring Boot application is running.

- **Method:** `GET`
- **Endpoint:** `/api/v1/tickets/health`
- **Full URL:** `http://localhost:8080/api/v1/tickets/health`

Example response:

```json
{
  "status": "UP",
  "application": "support-classifier",
  "modelProvider": "Ollama",
  "model": "llama3.2"
}
```

This endpoint checks application availability. It does not independently verify that Ollama is reachable.

### 2. Analyze a Support Ticket

Analyzes a customer's message and generates a suggested response.

- **Method:** `POST`
- **Endpoint:** `/api/v1/tickets/analyze`
- **Full URL:** `http://localhost:8080/api/v1/tickets/analyze`
- **Content-Type:** `application/json`

**Request body:**

```json
{
  "customerEmail": "customer@example.com",
  "messageBody": "My order was supposed to arrive three days ago, but the tracking page has not updated. Please help me resolve this issue."
}
```

**Example response:**

```json
{
  "category": "ORDER_DELAY",
  "priority": "HIGH",
  "sentiment": "ANGRY",
  "draftResponse": "Hello, I am sorry your order has been delayed. We understand how frustrating this can be. Our support team can review the tracking details and help you with the next steps."
}
```

The response is illustrative. Actual classifications and generated text depend on the model's output.

### Test with cURL

```bash
curl -X POST http://localhost:8080/api/v1/tickets/analyze \
  -H "Content-Type: application/json" \
  -d "{\"customerEmail\":\"customer@example.com\",\"messageBody\":\"My order is delayed by three days. Please help.\"}"
```

### Test with Postman

1. Start the Spring Boot application and Ollama.
2. Open Postman and create a new HTTP request.
3. Select `POST`.
4. Enter `http://localhost:8080/api/v1/tickets/analyze`.
5. Select **Body → raw → JSON**.
6. Paste the sample request JSON.
7. Click **Send** and inspect the response.

## 📊 Classification Categories

| Field     | Possible values                                             |
| --------- | ----------------------------------------------------------- |
| Category  | `ORDER_DELAY`, `REFUND`, `TECHNICAL_ISSUE`, `SPAM`, `OTHER` |
| Priority  | `LOW`, `MEDIUM`, `HIGH`                                     |
| Sentiment | `ANGRY`, `NEUTRAL`, `HAPPY`                                 |

These are the expected output values enforced by the application. The priority and sentiment are model-generated assessments, not guaranteed ground-truth labels.

## 🔧 Configuration

Configuration is available in `src/main/resources/application.properties`.

```properties
spring.application.name=support-classifier
server.port=8080

ollama.base-url=${OLLAMA_BASE_URL:http://localhost:11434}
ollama.model-name=${OLLAMA_MODEL_NAME:llama3.2}
ollama.timeout-seconds=${OLLAMA_TIMEOUT_SECONDS:120}
```

Environment variables allow you to override the default Ollama URL, model name, and timeout.

For example, in Windows PowerShell:

```powershell
$env:OLLAMA_MODEL_NAME="llama3.2"
$env:OLLAMA_BASE_URL="http://localhost:11434"
$env:OLLAMA_TIMEOUT_SECONDS="120"
mvn spring-boot:run
```

The configured model must already be available in Ollama.

## 🛡️ Error Handling

The application includes request validation and exception handling for common failures.

| HTTP status                 | Meaning                                                  |
| --------------------------- | -------------------------------------------------------- |
| `200 OK`                    | Ticket analysis completed successfully                   |
| `400 Bad Request`           | Invalid request, missing fields, or invalid email        |
| `502 Bad Gateway`           | Ollama unavailable or AI response could not be processed |
| `500 Internal Server Error` | Unexpected server-side failure                           |

If the API returns an error, inspect the Spring Boot console logs and confirm that Ollama is running and the model has been downloaded.

## 🔍 Troubleshooting

**Problem: Connection refused by Ollama**

Check whether Ollama is available:

```bash
curl http://localhost:11434/api/tags
```

Start Ollama if necessary:

```bash
ollama serve
```

**Problem: Model not found**

Download the configured model:

```bash
ollama pull llama3.2
```

**Problem: Maven build fails**

Verify the Java and Maven versions, check your internet connection, and retry:

```bash
mvn clean package
```

**Problem: API returns HTTP 502**

Check the Ollama URL, verify that the model is installed, and inspect the application logs. If the local model returns malformed JSON, retry the request or adjust the model configuration and prompt.

**Problem: API returns HTTP 400**

Confirm that the JSON is valid and that both `customerEmail` and `messageBody` are supplied. Use a valid email address and a non-empty message.

## 🚧 Current Scope and Future Improvements

The current implementation focuses on AI-based ticket analysis through a REST API. It does not include a database, authentication, a web dashboard, or automatic email delivery.

Potential enhancements include:

- Add PostgreSQL to persist support tickets and analysis results.
- Build a frontend dashboard for support agents.
- Add ticket history, search, and filtering.
- Implement authentication and role-based access.
- Add automated tests with JUnit and Mockito.
- Evaluate classification accuracy using a labeled dataset.
- Add monitoring, structured logging, and production deployment configuration.

## 🎯 Learning Outcomes

This project demonstrates practical experience with:

- Java and Spring Boot application development.
- REST API design and request validation.
- Maven dependency and build management.
- Local large language model integration using Ollama.
- LangChain4j-based AI application development.
- JSON parsing and exception handling.
- API testing with Postman and cURL.

## 🤝 Contributing

Contributions, bug reports, and suggestions are welcome.

1. Fork the repository.
2. Create a feature branch.
3. Make your changes and test them.
4. Submit a pull request describing your improvements.

##

---

**Built with Java, Spring Boot, LangChain4j, and Ollama.**
