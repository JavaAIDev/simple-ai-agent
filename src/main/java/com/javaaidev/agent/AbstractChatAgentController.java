package com.javaaidev.agent;

import com.javaaidev.chatagent.model.ChatAgentRequest;
import com.javaaidev.chatagent.springai.ModelAdapter;
import java.util.List;
import org.springframework.ai.chat.messages.Message;

public abstract class AbstractChatAgentController {

  public static final String SYSTEM_TEXT = """
      You are a chef who is proficient in various cuisines. Please answer users' questions about cooking.
      For other unrelated inputs, simply tell the user that you don't know.
      """;


  protected List<Message> chatRequestToMessages(ChatAgentRequest request) {
    return ModelAdapter.fromRequest(request);
  }
}
