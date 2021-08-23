package systems.tat.ramta.client.packets.out;

import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketOutLogin {

    public PacketOutLogin(SocketClientHandlerService clientHandlerService, Client client) {
        sendMessage(clientHandlerService, client);
    }

    private void sendMessage(SocketClientHandlerService socketClientHandlerService, Client client) {
        socketClientHandlerService.sendMessage(Message.create()
                .setType("Login")
                .setTarget("CLOUD")
                .set("Email", client.getEmail())
                .set("Password", client.getPassword()));
    }
}
