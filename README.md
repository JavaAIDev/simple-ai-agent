# Simple AI Agent

[![Maven build](https://github.com/JavaAIDev/simple-ai-agent/actions/workflows/maven.yml/badge.svg)](https://github.com/JavaAIDev/simple-ai-agent/actions/workflows/maven.yml)

A simple AI agent built using Spring AI.

Native executable of this agent is built using GraalVM.

To run this agent, you need to have a OpenAI API key. This API key is read from environment variable
`OPENAI_API_KEY`.

Build the native agent using the following command.

```shell
mvn -Pnative -DskipTests package
```

Start the application and access the chat UI at `http://localhost:8080/webjars/chat-agent-ui/index.html`.

To test reasoning support of a model, the `reasoning` profile should be enabled.
