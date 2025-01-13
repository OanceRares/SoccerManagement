package com.footballteams.footballteamorganizer.Controllers;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    // Map to track which sessions are subscribed to which games
    private final Map<Integer, Set<WebSocketSession>> gameSubscriptions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Add session to a generic pool (optional, for debugging purposes)
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Parse the incoming message to determine the game ID
        Map<String, String> payload = parseMessage(message.getPayload());
        if ("JOIN_GAME".equals(payload.get("action"))) {
            int gameId = Integer.parseInt(payload.get("gameId"));
            subscribeToGame(gameId, session);
            broadcastToGame(gameId, "User joined game " + gameId);
        }
    }

    private Map<String, String> parseMessage(String payload) {
        // Parse JSON payload (use your preferred library)
        return new HashMap<>(); // Replace with actual parsing
    }

    private void subscribeToGame(int gameId, WebSocketSession session) {
        gameSubscriptions.computeIfAbsent(gameId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    private void broadcastToGame(int gameId, String message) throws IOException {
        Set<WebSocketSession> sessions = gameSubscriptions.getOrDefault(gameId, Collections.emptySet());
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        gameSubscriptions.values().forEach(sessions -> sessions.remove(session));
    }
}
