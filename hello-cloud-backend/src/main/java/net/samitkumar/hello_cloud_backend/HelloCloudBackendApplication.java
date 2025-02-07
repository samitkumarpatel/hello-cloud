package net.samitkumar.hello_cloud_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

@SpringBootApplication
public class HelloCloudBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloCloudBackendApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions
				.route()
				.path("/internal-api", builder -> builder
						.GET("", request -> ServerResponse.ok().bodyValue(Map.of("message", "Hello from internal API")))
						.POST("", request -> request.bodyToMono(Map.class).flatMap(ServerResponse.ok()::bodyValue))
						.PUT("/{id}", request -> request
								.bodyToMono(Map.class)
								.mapNotNull(m -> {
									m.put("id", request.pathVariable("id"));
									return m;
								})
								.flatMap(ServerResponse.ok()::bodyValue))
						.PATCH("/{id}",  request -> request
								.bodyToMono(Map.class)
								.mapNotNull(m -> {
									m.put("id", request.pathVariable("id"));
									return m;
								})
								.flatMap(ServerResponse.ok()::bodyValue))
						.DELETE("/{id}", request -> ServerResponse.ok().bodyValue(Map.of("id", request.pathVariable("id"), "deleted", true)))
				)
				.build();
	}

	@Bean
	CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}

}
