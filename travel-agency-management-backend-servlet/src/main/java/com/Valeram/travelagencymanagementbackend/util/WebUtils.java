package com.Valeram.travelagencymanagementbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final String APPLICATION_JSON = "application/json";

  static {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  public static void sendJson(HttpServletResponse response, Object body, int code)
      throws IOException {
    response.setStatus(code);
    sendJson(response, body);
  }

  public static void sendJson(HttpServletResponse response, Object body) throws IOException {
    response.setContentType(APPLICATION_JSON);
    writeBody(response, body);
  }

  private static void writeBody(HttpServletResponse response, Object body)
      throws IOException {
    PrintWriter out = response.getWriter();
    String json = toJson(body);
    out.write(json);
    out.flush();
  }

  public static String toJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T readBody(HttpServletRequest request, Class<T> clazz) throws IOException {
    String body = readFromInputStream(request.getInputStream());

    return objectMapper.readValue(body, clazz);
  }

  private static String readFromInputStream(InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder result = new StringBuilder();

    String line = "";

    while ((line = reader.readLine()) != null) {
      result.append(line);
    }

    return result.toString();
  }


  public static <T> T parseJson(String json, Class<T> clazz) throws JsonProcessingException {
    return objectMapper.readValue(json, clazz);
  }

}
