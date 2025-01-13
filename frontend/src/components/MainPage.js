import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

function MainPage() {
    const [games, setGames] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [notifications, setNotifications] = useState([]);

    const navigate = useNavigate();
    const userEmail = localStorage.getItem("email");
    const stompClientRef = useRef(null); // Persist stompClient across renders

    const fetchGames = async () => {
        try {
            const token = localStorage.getItem("token");
            const response = await axios.get("http://localhost:8080/api/games/upcoming", {
                headers: { Authorization: `Bearer ${token}` },
            });

            const updatedGames = response.data.map((game) => ({
                ...game,
                currentParticipants: game.blackTeam.length + game.whiteTeam.length,
            }));

            setGames(updatedGames);
        } catch (err) {
            console.error("Error fetching games:", err);
            setError("Failed to fetch games. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchGames();
    }, []);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const socket = new SockJS("http://localhost:8080/ws");
        stompClientRef.current = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
        });

        stompClientRef.current.onConnect = () => {
            console.log("Connected to WebSocket!");

            stompClientRef.current.subscribe("/all/messages", (message) => {
                console.log("General WebSocket broadcast:", message.body);
                fetchGames();
            });
        };

        stompClientRef.current.onStompError = (error) => {
            console.error("WebSocket connection error:", error);
        };

        stompClientRef.current.activate();

        return () => {
            if (stompClientRef.current && stompClientRef.current.connected) {
                stompClientRef.current.deactivate();
                console.log("WebSocket disconnected!");
            }
        };
    }, [userEmail]);

    const handleJoinLeaveGame = async (gameId, action) => {
        try {
            const token = localStorage.getItem("token");
            const url =
                action === "join"
                    ? `http://localhost:8080/api/games/join/${gameId}`
                    : `http://localhost:8080/api/games/leave/${gameId}`;

            await axios.post(url, {}, { headers: { Authorization: `Bearer ${token}` } });

            if (stompClientRef.current && stompClientRef.current.connected) {
                stompClientRef.current.publish({
                    destination: "/app/application",
                    body: `${localStorage.getItem("email")} ${action === "join" ? "joined" : "left"} game ${gameId}`,
                });
            }

            await fetchGames();
        } catch (error) {
            console.error(`Error ${action === "join" ? "joining" : "leaving"} the game:`, error.response?.data || error.message);
        }
    };

    const handleLogout = () => {
        // Clear localStorage
        localStorage.removeItem("token");
        localStorage.removeItem("email");

        // Disconnect WebSocket
        if (stompClientRef.current && stompClientRef.current.connected) {
            stompClientRef.current.deactivate();
            console.log("WebSocket disconnected on logout!");
        }

        // Redirect to login page
        navigate("/login");
    };

    if (loading) {
        return (
            <div className="container mt-5">
                <h3>Loading upcoming games...</h3>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mt-5">
                <h3 className="text-danger">{error}</h3>
            </div>
        );
    }

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Upcoming Football Games</h2>
                <button className="btn btn-danger" onClick={handleLogout}>
                    Logout
                </button>
            </div>
            <div>
                {notifications.map((notification, index) => (
                    <div key={index} className="alert alert-info">
                        {notification.message}
                    </div>
                ))}
            </div>
            {games.length === 0 ? (
                <p>No upcoming games available.</p>
            ) : (
                <table className="table table-striped table-bordered">
                    <thead className="table-dark">
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Location</th>
                        <th>Black Team</th>
                        <th>White Team</th>
                        <th>Participants</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {games.map((game) => {
                        const isUserInGame =
                            game.blackTeam.some((user) => user.email === userEmail) ||
                            game.whiteTeam.some((user) => user.email === userEmail);

                        return (
                            <tr key={game.id}>
                                <td>{game.title}</td>
                                <td>{game.description}</td>
                                <td>{new Date(game.gameDateTime).toLocaleDateString()}</td>
                                <td>
                                    {new Date(game.gameDateTime).toLocaleTimeString([], {
                                        hour: "2-digit",
                                        minute: "2-digit",
                                    })}
                                </td>
                                <td>{game.location}</td>
                                <td>
                                    {game.blackTeam.map((user) => (
                                        <div key={user.id}>
                                            {user.firstName} {user.lastName}
                                        </div>
                                    ))}
                                </td>
                                <td>
                                    {game.whiteTeam.map((user) => (
                                        <div key={user.id}>
                                            {user.firstName} {user.lastName}
                                        </div>
                                    ))}
                                </td>
                                <td>
                                    {game.currentParticipants} / {game.maxParticipants}
                                </td>
                                <td>
                                    <button
                                        className={`btn btn-sm ${isUserInGame ? "btn-danger" : "btn-primary"}`}
                                        onClick={() => handleJoinLeaveGame(game.id, isUserInGame ? "leave" : "join")}
                                    >
                                        {isUserInGame ? "Leave" : "Join"}
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default MainPage;
