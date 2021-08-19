package systems.tat.ramta.client.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.packets.out.PacketOutRegisterAccount;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

@Service
@Data
public class ClientInputService {

    private final SocketClientHandlerService socketClientHandlerService;

    public void sendRegister(Client client) {
        new PacketOutRegisterAccount(socketClientHandlerService, client);
    }

}
