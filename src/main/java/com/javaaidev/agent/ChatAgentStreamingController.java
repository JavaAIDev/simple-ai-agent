package com.javaaidev.agent;

import static com.javaaidev.agent.Constants.SYSTEM_TEXT;

import com.javaaidev.chatagent.model.ChatAgentRequest;
import com.javaaidev.chatagent.model.ChatAgentResponse;
import com.javaaidev.chatagent.springai.ModelAdapter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatAgentStreamingController {

  private final ChatClient chatClient;

  public ChatAgentStreamingController(ChatClient.Builder builder) {
    chatClient = builder.build();
  }

  @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<ChatAgentResponse>> chatStreaming(
      @RequestBody ChatAgentRequest request) {
    return ModelAdapter.toStreamingResponse(
        chatClient.prompt()
            .system(SYSTEM_TEXT)
            .messages(ModelAdapter.fromRequest(request).toArray(new Message[0]))
            .stream()
            .chatResponse());
  }
}
