package com.questionarioConex.avaliacao.service;

import com.questionarioConex.avaliacao.dto.ResultadoAvaliacaoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordNotificationService {

    @Value("${discord.webhook.url:}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void notificarResultado(ResultadoAvaliacaoDTO resultado) {
        if (webhookUrl == null || webhookUrl.isBlank()) return;

        Map<String, Object> payload = new HashMap<>();
        payload.put("content", "📢 **Novo Teste Concluído na Conexão Provedor de Internet**");

        // Formata embed do Discord
        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "📋 Avaliação de " + resultado.getCandidato());
        embed.put("description", String.format("**Pontuação:** %d / %d (%.1f%%)\n**Classificação:** %s",
                resultado.getPontosObtidos(), resultado.getPontosPossiveis(),
                resultado.getPorcentagem(), resultado.getNivelClassificacao()));
        embed.put("color", resultado.getPorcentagem() >= 80 ? 65280 : 16711680);

        payload.put("embeds", new Object[]{embed});

        try {
            restTemplate.postForEntity(webhookUrl, payload, String.class);
        } catch (Exception ignored) {
            // Log de erro
        }
    }
}