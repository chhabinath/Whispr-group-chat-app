# Whispr - Real-time Chat Application

<div align="center">

![Whispr Logo](https://img.shields.io/badge/Whispr-Chat%20App-97a87a?style=for-the-badge&logo=chat&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logo=websocket&logoColor=white)
![STOMP](https://img.shields.io/badge/STOMP-Protocol-00D8FF?style=for-the-badge)

*A modern, real-time chat application with elegant earthy aesthetics and seamless user experience*

</div>

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Quick Start](#-quick-start)
- [API Documentation](#-api-documentation)
- [Development Guide](#-development-guide)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

## 🚀 Overview

Whispr is a professional-grade real-time chat application built with Spring Boot and WebSocket technology. It features a beautiful, responsive UI with earthy color schemes and glass-morphism design elements, providing users with a seamless and engaging chat experience.

### Key Highlights

- **Real-time Messaging**: Instant message delivery using WebSocket protocol
- **Modern UI/UX**: Elegant design with frosted glass effects and smooth animations
- **Scalable Architecture**: Built on Spring Boot with STOMP over WebSocket
- **Responsive Design**: Works flawlessly across all device sizes
- **Production Ready**: Includes error handling, reconnection logic, and status monitoring

## ✨ Features

### Core Features
- 💬 Real-time message broadcasting
- 👥 Multi-user chat rooms
- 🎨 Themable interface (light/dark modes)
- 📱 Fully responsive design
- ⚡ Instant message delivery
- 🔄 Auto-reconnection on disconnect

### User Experience
- ✨ Smooth animations and transitions
- 🎯 Intuitive message bubbles
- 📊 Connection status indicators
- ⌨️ Keyboard shortcuts (Enter to send)
- 🔄 Auto-scroll to latest messages
- 💫 Typing indicators (placeholder)

### Technical Features
- 🔒 STOMP protocol over WebSocket
- 🏗️ RESTful backend architecture
- 📦 SockJS fallback support
- 🎭 CORS configuration
- 🔧 Easy configuration management

## 🏗️ Architecture

### System Architecture
```
Client Layer (HTML/JS) 
        ↓
WebSocket Layer (SockJS + STOMP)
        ↓
Spring WebSocket Handler
        ↓
Message Broker (Simple Broker)
        ↓
Business Logic Layer
        ↓
Data Model Layer
```

### Message Flow
1. **Client Connection**: Client connects via SockJS to `/chat` endpoint
2. **Subscription**: Client subscribes to `/topic/messages` for incoming messages
3. **Message Send**: Client sends messages to `/app/sendMessage`
4. **Message Broadcast**: Server broadcasts to all subscribed clients via `/topic/messages`
5. **Message Display**: Clients render messages with appropriate styling

## 🛠️ Tech Stack

### Backend
- **Java 17+** - Runtime environment
- **Spring Boot 3.x** - Application framework
- **Spring WebSocket** - Real-time communication
- **STOMP** - Messaging protocol
- **Maven** - Dependency management
- **Lombok** - Boilerplate reduction

### Frontend
- **HTML5** - Markup structure
- **CSS3** - Styling with custom properties and animations
- **Vanilla JavaScript** - Client-side logic
- **SockJS Client** - WebSocket fallback support
- **STOMP.js** - STOMP protocol implementation
- **Bootstrap 5** - Responsive layout framework
- **Font Awesome** - Icon library

### Development Tools
- **Spring Boot DevTools** - Development efficiency
- **Maven** - Build automation
- **Git** - Version control

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Modern web browser with JavaScript enabled

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/chhabinath/Whispr-group-chat-app.git
   cd whispr-chat
   ```

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

### Docker Support (Optional)

```bash
# Build Docker image
docker build -t whispr-chat .

# Run container
docker run -p 8080:8080 whispr-chat
```

## 📚 API Documentation

### WebSocket Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/chat` | WEBSOCKET | WebSocket connection endpoint with SockJS fallback |
| `/app/sendMessage` | SEND | Client sends messages to this destination |
| `/topic/messages` | SUBSCRIBE | Clients subscribe to receive broadcast messages |

### REST Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /` | GET | Serves the chat interface |

### Message Format

```json
{
  "id": 12345,
  "sender": "John Doe",
  "content": "Hello, world!",
  "timestamp": "2023-10-01T12:00:00Z"
}
```

## 🛠️ Development Guide

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/chhabinath/chat/whispr/
│   │       ├── controller/
│   │       │   └── ChatController.java
│   │       ├── model/
│   │       │   └── ChatMessage.java
│   │       └── config/
│   │           └── WebSocketConfig.java
│   └── resources/
│       └── static/
│           └── chat.html
└── test/
    └── java/
        └── com/chhabinath/chat/whispr/
```

### Adding New Features

1. **New Message Types**
   ```java
   // Extend ChatMessage model
   public class ExtendedChatMessage extends ChatMessage {
       private MessageType type;
       private String recipient;
   }
   ```

2. **Additional Endpoints**
   ```java
   @MessageMapping("/privateMessage")
   @SendToUser("/queue/private")
   public ChatMessage sendPrivateMessage(ChatMessage message) {
       // Implementation
   }
   ```

### Configuration

#### WebSocket Configuration
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
```

## 🚀 Deployment

### Production Build
```bash
mvn clean package -DskipTests
java -jar target/whispr-chat-1.0.0.jar
```

### Environment Variables
```bash
export SERVER_PORT=8080
export ALLOWED_ORIGINS=https://yourdomain.com
```

### Cloud Deployment

#### Docker Compose
```yaml
version: '3.8'
services:
  whispr-chat:
    image: whispr-chat:latest
    ports:
      - "8080:8080"
    environment:
      - SERVER_PORT=8080
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Workflow
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards
- Follow Java naming conventions
- Use meaningful commit messages
- Include tests for new features
- Update documentation accordingly

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

- 📧 **Email**: chhabinath1519@gmail.com
- 🐛 **Issues**: [GitHub Issues](https://github.com/chhabinath/Whispr-group-chat-app/issues)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/chhabinath/Whispr-group-chat-app/discussions)

## 🙏 Acknowledgments

- Spring Framework team for excellent WebSocket support
- Bootstrap team for responsive design framework
- STOMP.js and SockJS communities for reliable client libraries

---

<div align="center">

**Built with ❤️ using Spring Boot and WebSocket**

[Report Bug](https://github.com/chhabinath/Whispr-group-chat-app/issues) · [Request Feature](https://github.com/chhabinath/Whispr-group-chat-app/issues)

</div>
