package com.javaaidev.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatAgentController {

  public record ChatRequest(String input) {

  }

  public record ChatResponse(String output) {

  }

  private static final String SYSTEM_TEXT = "You are a chef who is proficient in various cuisines. Please answer users' questions about cooking.";
  private final ChatClient chatClient;

  public ChatAgentController(ChatClient.Builder builder) {
    chatClient = builder.build();
  }

  @PostMapping
  public ChatResponse chat(@RequestBody ChatRequest request) {
    var output = chatClient.prompt().system(SYSTEM_TEXT)
        .user(request.input())
        .call()
        .content();
    return new ChatResponse(output);
  }
}
