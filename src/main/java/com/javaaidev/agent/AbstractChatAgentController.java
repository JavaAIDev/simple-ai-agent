package com.javaaidev.agent;

import com.javaaidev.chatagent.model.ChatRequest;
import com.javaaidev.chatagent.model.TextContentPart;
import com.javaaidev.chatagent.model.ThreadAssistantMessage;
import com.javaaidev.chatagent.model.ThreadUserMessage;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

public abstract class AbstractChatAgentController {

  public static final String SYSTEM_TEXT = """
      You are a chef who is proficient in various cuisines. Please answer users' questions about cooking.
      For other unrelated inputs, simply tell the user that you don't know.
      """;


  protected List<Message> chatRequestToMessages(ChatRequest request) {
    return request.messages().stream().flatMap(message -> {
      if (message instanceof ThreadUserMessage userMessage) {
        return userMessage.content().stream().map(part -> {
          if (part instanceof TextContentPart(String text)) {
            return new UserMessage(text);
          }
          return null;
        });
      } else if (message instanceof ThreadAssistantMessage assistantMessage) {
        return assistantMessage.content().stream().map(part -> {
          if (part instanceof TextContentPart(String text)) {
            return new AssistantMessage(text);
          }
          return null;
        });
      }
      return Stream.<Message>of();
    }).toList();
  }
}
