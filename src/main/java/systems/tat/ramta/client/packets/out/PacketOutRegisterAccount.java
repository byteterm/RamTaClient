package systems.tat.ramta.client.packets.out;

import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketOutRegisterAccount {

    private final SocketClientHandlerService socketClientHandlerService;

    public PacketOutRegisterAccount(SocketClientHandlerService socketClientHandlerService, Client client) {
        this.socketClientHandlerService = socketClientHandlerService;

        sendMessage(client);
    }

    private void sendMessage(Client client) {
        socketClientHandlerService.sendMessage(Message.create()
                .setType("RegisterAccount")
                .setTarget("CLOUD")
                .set("Username", client.getUsername())
                .set("Password", client.getPassword())
                .set("Email", client.getEmail())
                .set("AcceptAGB", client.getAcceptAGB()));
    }
}
