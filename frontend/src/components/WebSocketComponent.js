import React, { useEffect, useState } from "react";

function WebSocketComponent() {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        const socket = new WebSocket("ws://localhost:8080/ws/games");

        socket.onopen = () => {
            console.log("WebSocket connection established.");
        };

        socket.onmessage = (event) => {
            const newMessage = event.data;
            setMessages((prevMessages) => [...prevMessages, newMessage]);
        };

        socket.onclose = () => {
            console.log("WebSocket connection closed.");
        };

        socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        return () => {
            socket.close(); // Cleanup on component unmount
        };
    }, []);

    return (
        <div>
            <h2>Real-Time Game Updates</h2>
            <ul>
                {messages.map((msg, index) => (
                    <li key={index}>{msg}</li>
                ))}
            </ul>
        </div>
    );
}

export default WebSocketComponent;
