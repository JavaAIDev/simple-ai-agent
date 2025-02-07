package io.github.javaaidev.agent;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

  @Bean
  public RestClient.Builder restClientBuilder(HttpClient httpClient) {
    JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
    requestFactory.setReadTimeout(Duration.ofMinutes(3));
    return RestClient.builder().requestFactory(requestFactory);
  }

  @Bean
  public WebClient.Builder webClientBuilder(HttpClient httpClient) {
    var connector = new JdkClientHttpConnector(httpClient);
    connector.setReadTimeout(Duration.ofMinutes(3));
    return WebClient.builder().clientConnector(connector);
  }

  @Bean
  public HttpClient httpClient() {
    var executor = new SimpleAsyncTaskExecutor();
    executor.setVirtualThreads(true);
    return HttpClient.newBuilder()
        .executor(executor)
        .connectTimeout(Duration.ofMinutes(3))
        .build();
  }
}
