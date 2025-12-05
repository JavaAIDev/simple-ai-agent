package com.javaaidev.agent;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

  private static final Duration API_TIMEOUT = Duration.ofMinutes(3);

  @Bean
  public RestClient.Builder restClientBuilder(HttpClient httpClient) {
    JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
    requestFactory.setReadTimeout(API_TIMEOUT);
    return RestClient.builder().requestFactory(requestFactory);
  }

  @Bean
  public WebClient.Builder webClientBuilder(HttpClient httpClient) {
    var connector = new JdkClientHttpConnector(httpClient);
    connector.setReadTimeout(API_TIMEOUT);
    return WebClient.builder().clientConnector(connector);
  }

  @Bean
  public HttpClient httpClient() {
    var executor = new SimpleAsyncTaskExecutor();
    executor.setVirtualThreads(true);
    return HttpClient.newBuilder()
        .executor(executor)
        .connectTimeout(API_TIMEOUT)
        .build();
  }

  @Bean
  @Primary
  @Profile("reasoning")
  public OpenAiChatProperties openAiChatProperties() {
    OpenAiChatProperties properties = new OpenAiChatProperties();
    properties.getOptions().setTemperature(null);
    return properties;
  }
}
