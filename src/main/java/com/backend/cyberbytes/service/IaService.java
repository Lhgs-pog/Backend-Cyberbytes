package com.backend.cyberbytes.service;

import Config.IaConfig;
import com.backend.cyberbytes.dto.IaRequest;
import com.backend.cyberbytes.dto.IaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class IaService {

    private final IaConfig config = new IaConfig();
    private final ObjectMapper mapper = new ObjectMapper();


    public String fazerRequisicao(String prompt) throws URISyntaxException, IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            IaRequest iaRequest = criarRequest(prompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(config.getIaUrl()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(iaRequest)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.toString();

            IaResponse iaResponse = mapper.readValue(json, IaResponse.class);
            return iaResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

        } catch (Exception e){
            System.out.println("Ocorreu um erro ao tentar fazer a requisição. Exeption: "+ e.getMessage());
            return "Ocorreu um erro ao tentar fazer a requisição. Exeption: "+ e.getMessage();
        }
    }

    public IaRequest criarRequest(String prompt){
        IaRequest iaRequest = new IaRequest();
        IaRequest.Content content = new IaRequest.Content();
        content.setParts(List.of(new IaRequest.Part(prompt)));
        iaRequest.setContents(List.of(content));

        return iaRequest;
    }
}
