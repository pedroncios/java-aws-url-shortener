package com.pedroncios.createUrlShortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

   private final ObjectMapper objectMapper = new ObjectMapper();
   private final S3Client s3Client = S3Client.builder().build();

   @Override
   public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
      // Extrai o body da requisição
      String body = input.get("body").toString();

      Map<String, String> bodyMap;
      try {
         bodyMap = objectMapper.readValue(body, Map.class);
      } catch (Exception exception) {
         throw new RuntimeException("Error parsing JSON body: " + exception.getMessage(), exception);
      }

      // Extrai a url original e o timestamp do body
      String originalUrl = bodyMap.get("originalUrl");
      String expirationTime = bodyMap.get("expirationTime");
      long expirationTimeInSeconds = Long.parseLong(expirationTime) * 3600;

      // Gera o uuid da url de forma tosca (somente para exemplo prático)
      String shortUrlCode = UUID.randomUUID().toString().substring(0, 8);

      UrlData urlData = new UrlData(originalUrl, expirationTimeInSeconds);

      try {
         //
         String urlDataJson = objectMapper.writeValueAsString(urlData);

         PutObjectRequest request = PutObjectRequest.builder()
                 .bucket("java-aws-url-shortener-storage") // Nome do bucket
                 .key( shortUrlCode + ".json") // Nome do arquivo
                 .build();

         // Envia o objeto para o bucket
         s3Client.putObject(request, RequestBody.fromString(urlDataJson));
      } catch (Exception exception) {
         throw new RuntimeException("Error saving URL data to S3: " + exception.getMessage(), exception);
      }

      Map<String, String> response = new HashMap<>();
      response.put("code", shortUrlCode);

      return response;
   }
}