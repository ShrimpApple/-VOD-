import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import "../styles/chat.css";

const ChatBox = () => {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [username] = useState("Guest");

  useEffect(() => {
    const socket = new SockJS("http://localhost:8094/ws-chat");
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("Connected to WebSocket");
        client.subscribe("/topic/public", (message) => {
          const chatMessage = JSON.parse(message.body);
          setMessages((prev) => [...prev, chatMessage]);
        });
        client.publish({
          destination: "/app/chat.addUser",
          body: JSON.stringify({ type: "JOIN", sender: username, content: "" }),
        });
      },
      onStompError: (frame) => {
        console.error("Broker error: " + frame.headers["message"]);
      },
    });
    client.activate();
    setStompClient(client);

    return () => {
      client.deactivate();
    };
  }, [username]);

  const sendMessage = () => {
    if (stompClient && newMessage.trim() !== "") {
      const chatMessage = {
        type: "CHAT",
        sender: username,
        content: newMessage,
      };
      stompClient.publish({
        destination: "/app/chat.sendMessage",
        body: JSON.stringify(chatMessage),
      });
      setNewMessage("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  return (
    <div className="chat-container">
      <div className="chat-header">
        <h3>실시간 채팅</h3>
      </div>
      <div className="chat-messages">
        {messages.map((msg, index) => (
          <div key={index} className="chat-message">
            <strong>{msg.sender}: </strong> {msg.content}
          </div>
        ))}
      </div>
      <div className="chat-input">
        <input
          type="text"
          placeholder="메시지를 입력하세요..."
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyPress={handleKeyPress}
        />
        <button onClick={sendMessage}>전송</button>
      </div>
    </div>
  );
};

export default ChatBox;
