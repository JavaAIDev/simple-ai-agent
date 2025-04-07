package com.javaaidev.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SimpleChatController {

  public static final String SYSTEM_TEXT = """
      You are a chef who is proficient in various cuisines. Please answer users' questions about cooking.
      For other unrelated inputs, simply tell the user that you don't know.
      """;

  private final ChatClient chatClient;

  public SimpleChatController(ChatClient.Builder builder) {
    chatClient = builder.build();
  }

  @PostMapping("/simple_chat")
  public ChatOutput chat(@RequestBody ChatInput chatInput) {
    return new ChatOutput(
        chatClient.prompt()
            .system(SYSTEM_TEXT)
            .user(chatInput.input())
            .call().content());
  }


  public record ChatInput(String input) {

  }

  public record ChatOutput(String output) {

  }
}
