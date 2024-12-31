# Simple AI Agent

A simple AI agent built using Spring AI.

Native executable of this agent is built using GraalVM.

To run this agent, you need to have a OpenAI API key. This API key is read from environment variable
`OPENAI_API_KEY`.

Build the native agent using the following command.

```shell
mvn -Pnative -DskipTests package
```

