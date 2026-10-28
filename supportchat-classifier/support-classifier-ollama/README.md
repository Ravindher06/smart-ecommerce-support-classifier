# Smart E-Commerce Support Ticket Classifier

A practical entry-level Java project using Spring Boot, LangChain4j and a **local Ollama `llama3.2` model**. It classifies customer support messages and drafts a suggested response. No cloud AI API key or database is required.

## Requirements

- JDK 17 or newer
- Maven 3.8+
- Ollama installed locally
- Model downloaded with `ollama pull llama3.2`

## Start Ollama

Open a terminal and run:

```bash
ollama pull llama3.2
ollama serve
```

If Ollama is already running as a desktop service, you usually do not need to run `ollama serve` again. Verify the model with:

```bash
ollama list
curl http://localhost:11434/api/tags
```

## Run the application

Windows: double-click `run.bat`, or run `mvn clean spring-boot:run`.

Linux/macOS:

```bash
chmod +x run.sh
./run.sh
```

The first Maven build needs internet access to download dependencies. The app starts on `http://localhost:8080`.

Configuration can be overridden with environment variables:

- `OLLAMA_BASE_URL` (default `http://localhost:11434`)
- `OLLAMA_MODEL_NAME` (default `llama3.2`)
- `OLLAMA_TIMEOUT_SECONDS` (default `120`)

## API

### Health check

`GET http://localhost:8080/api/v1/tickets/health`

Example response:

```json
{
  "status": "UP",
  "application": "support-classifier",
  "modelProvider": "Ollama",
  "model": "llama3.2"
}
```

This confirms the Spring Boot process is running; it does not guarantee Ollama is reachable.

### Analyze a ticket

`POST http://localhost:8080/api/v1/tickets/analyze`

Header: `Content-Type: application/json`

Body:

```json
{
  "customerEmail": "customer@example.com",
  "messageBody": "My order #A123 was supposed to arrive three days ago. The tracking page has not changed and I need it urgently."
}
```

Example response (the wording and classification depend on the model):

```json
{
  "category": "ORDER_DELAY",
  "priority": "HIGH",
  "sentiment": "ANGRY",
  "draftResponse": "Hello, I'm sorry your order has not arrived as expected. We understand how frustrating this is. Please share your order number if it is not already included, and our support team will check the latest tracking details and update you."
}
```

Test with curl:

```bash
curl -X POST http://localhost:8080/api/v1/tickets/analyze \
  -H "Content-Type: application/json" \
  -d "{\"customerEmail\":\"customer@example.com\",\"messageBody\":\"My order is three days late. Please help.\"}"
```

## Error handling

- Invalid/missing email or message: HTTP 400 with a JSON error.
- Invalid JSON body: HTTP 400.
- Ollama unreachable, model missing, or generation fails: HTTP 502 with a helpful JSON error.
- Unexpected server exception: HTTP 500 with a generic message; details remain in server logs.
- If the model returns malformed JSON, the API returns HTTP 502 rather than crashing.

## Notes

- The draft response is a suggestion for a human support agent; it is not sent to the customer automatically.
- No Mistral dependency or API key is used.
- The project uses LangChain4j `0.33.0`; its Ollama module and core module use the same version.
- Build and runtime success depend on your machine having the prerequisites and being able to download Maven dependencies. This archive was assembled as source; run `mvn clean package` locally to compile it.
