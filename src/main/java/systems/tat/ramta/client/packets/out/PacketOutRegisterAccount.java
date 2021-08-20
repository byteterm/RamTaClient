package systems.tat.ramta.client.packets.out;

import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketOutRegisterAccount {

    private final Client client;

    public PacketOutRegisterAccount(SocketClientHandlerService clientHandlerService, Client client) {
        this.client = client;

        sendMessage(clientHandlerService);
    }

    private void sendMessage(SocketClientHandlerService clientHandlerService) {
        clientHandlerService.sendMessage(Message.create()
                .setType("RegisterAccount")
                .setTarget("CLOUD")
                .set("Username", client.getUsername())
                .set("Password", client.getPassword())
                .set("Email", client.getEmail())
                .set("AcceptAGB", client.getAcceptAGB()));
    }
}
