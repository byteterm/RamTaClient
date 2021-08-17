package systems.tat.ramta.client.packets.out;

import systems.tat.ramta.client.enums.ClientStatus;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketOutHandshake {

    private final SocketClientHandlerService socketClientHandlerService;

    /*
        Status: LoginIn, Online, AFK
     */
    public PacketOutHandshake(SocketClientHandlerService socketClientHandlerService, ClientStatus status) {
        this.socketClientHandlerService = socketClientHandlerService;

        sendPacket(status);
    }

    private void sendPacket(ClientStatus status) {
        socketClientHandlerService.sendMessage(Message.create()
                .setType("Handshake")
                .setTarget("CLOUD")
                .set("Status", "asd"));
    }
}
