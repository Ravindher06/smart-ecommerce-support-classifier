@echo off
setlocal
echo === Smart E-Commerce Support Ticket Classifier ===
where java >nul 2>nul
if errorlevel 1 (
  echo ERROR: Java was not found. Install JDK 17 or newer and add it to PATH.
  pause
  exit /b 1
)
where mvn >nul 2>nul
if errorlevel 1 (
  echo ERROR: Maven was not found. Install Maven 3.8+ and add it to PATH.
  echo If you use an IDE, import this folder as an existing Maven project.
  pause
  exit /b 1
)
echo Checking local Ollama model...
ollama show llama3.2 >nul 2>nul
if errorlevel 1 (
  echo Model llama3.2 was not found. Downloading it now...
  ollama pull llama3.2
  if errorlevel 1 (
    echo ERROR: Could not download llama3.2. Ensure Ollama is installed and running.
    pause
    exit /b 1
  )
)
echo Building and starting application...
call mvn clean spring-boot:run
pause
