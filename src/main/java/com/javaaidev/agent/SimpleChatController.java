package com.javaaidev.agent;

import static com.javaaidev.agent.Constants.SYSTEM_TEXT;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SimpleChatController {

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
