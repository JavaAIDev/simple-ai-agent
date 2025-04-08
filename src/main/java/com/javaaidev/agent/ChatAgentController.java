package com.javaaidev.agent;

import com.javaaidev.chatagent.model.ChatAgentRequest;
import com.javaaidev.chatagent.model.ChatAgentResponse;
import com.javaaidev.chatagent.springai.ModelAdapter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat_non_streaming")
public class ChatAgentController extends AbstractChatAgentController {

  private final ChatClient chatClient;

  public ChatAgentController(ChatClient.Builder builder) {
    chatClient = builder.build();
  }

  @PostMapping
  public Flux<ServerSentEvent<ChatAgentResponse>> chat(@RequestBody ChatAgentRequest request) {
    if (request == null) {
      return Flux.empty();
    }
    var messages = ModelAdapter.fromRequest(request);
    var chatResponse = chatClient.prompt().system(SYSTEM_TEXT)
        .messages(messages.toArray(new Message[0]))
        .call()
        .chatResponse();
    if (chatResponse == null) {
      return Flux.empty();
    }
    return ModelAdapter.toStreamingResponse(Flux.just(chatResponse));
  }
}
