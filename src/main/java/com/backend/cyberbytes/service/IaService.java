package com.backend.cyberbytes.service;

import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class IaService {

    HttpClient client = HttpClient.newHttpClient();



}
