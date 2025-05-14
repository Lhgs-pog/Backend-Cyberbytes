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

    private final IaConfig config = new IaConfig(); //Objeto para pegar o endereço com a chave
    private final ObjectMapper mapper = new ObjectMapper(); //Objeto para converter classes em json e json em classes

    /*
        Faz a requisição http para a api da ia e retorna a resposta em string
     */
    public String fazerRequisicao(String prompt) throws URISyntaxException, IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            IaRequest iaRequest = criarRequest(prompt);

            //Criação e configuração da requisiçãao post
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(config.getIaUrl())) //Endereço onde a requisição vai chegar
                    .header("Content-Type", "application/json") // Configuração do header, no caso aqui o tipo de conteúdo
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(iaRequest))) // Definição do tipo de requisição e conteúdo do body
                    .build();

            //OBS: Essa requisição não e assincrona
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //O client envia nossa requição e aguarda a resposta

            String json = response.toString(); //Converte o json em string

            IaResponse iaResponse = mapper.readValue(json, IaResponse.class); // Converte a string no objeto response
            return iaResponse.getCandidates().get(0).getContent().getParts().get(0).getText(); //Pecorre todos os elementos do objeto até chegar no texto

        } catch (Exception e){
            System.out.println("Ocorreu um erro ao tentar fazer a requisição. Exeption: "+ e.getMessage());
            return "Ocorreu um erro ao tentar fazer a requisição. Exeption: "+ e.getMessage();
        }
    }

    public IaRequest criarRequest(String prompt){

        IaRequest iaRequest = new IaRequest();
        IaRequest.Content content = new IaRequest.Content();
        content.setParts(List.of(new IaRequest.Part(prompt))); //coloca o prompt na lista de parts dentro do content
        iaRequest.setContents(List.of(content)); // Adiciona o content ao nosso request

        return iaRequest;
    }
}
