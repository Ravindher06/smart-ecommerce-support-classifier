#!/usr/bin/env sh
set -eu
command -v java >/dev/null 2>&1 || { echo "Install JDK 17+ first."; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo "Install Maven 3.8+ first."; exit 1; }
if ! ollama show llama3.2 >/dev/null 2>&1; then
  ollama pull llama3.2
fi
mvn clean spring-boot:run
