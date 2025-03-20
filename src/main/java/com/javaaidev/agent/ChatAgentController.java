package com.javaaidev.agent;

import com.javaaidev.chatagent.model.ChatRequest;
import com.javaaidev.chatagent.model.ChatResponse;
import com.javaaidev.chatagent.model.TextContentPart;
import com.javaaidev.chatagent.model.ThreadAssistantMessage;
import com.javaaidev.chatagent.model.ThreadUserMessage;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatAgentController {


  private static final String SYSTEM_TEXT = "You are a chef who is proficient in various cuisines. Please answer users' questions about cooking.";
  private final ChatClient chatClient;

  public ChatAgentController(ChatClient.Builder builder) {
    chatClient = builder.build();
  }

  @PostMapping
  public ChatResponse chat(@RequestBody ChatRequest request) {
    var messages = request.messages().stream().flatMap(message -> {
      if (message instanceof ThreadUserMessage userMessage) {
        return userMessage.content().stream().map(part -> {
          if (part instanceof TextContentPart textContentPart) {
            return new UserMessage(textContentPart.text());
          }
          return null;
        });
      } else if (message instanceof ThreadAssistantMessage assistantMessage) {
        return assistantMessage.content().stream().map(part -> {
          if (part instanceof TextContentPart textContentPart) {
            return new AssistantMessage(textContentPart.text());
          }
          return null;
        });
      }
      return Stream.of();
    }).toList();
    var output = chatClient.prompt().system(SYSTEM_TEXT)
        .messages(messages.toArray(new Message[0]))
        .call()
        .content();
    return new ChatResponse(List.of(new TextContentPart(output)));
  }
}
