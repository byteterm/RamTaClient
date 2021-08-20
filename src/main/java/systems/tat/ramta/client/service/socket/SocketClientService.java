package systems.tat.ramta.client.service.socket;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.thread.SocketClientThread;

@Service
public class SocketClientService {

    private final TaskExecutor taskExecutor;
    private final ApplicationContext applicationContext;

    public SocketClientService(TaskExecutor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
    }

    public void connect() {
        SocketClientThread socketClient = applicationContext.getBean(SocketClientThread.class);
        taskExecutor.execute(socketClient);
    }
}
