package ninyo.zchat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

@Slf4j
public class ClientHandler implements Runnable {

    private Socket socket;
    private List<String> messages;

    public ClientHandler(Socket socket, List<String> messages) {
        this.socket = socket;
        this.messages = messages;
    }

    @Override
    public void run() {
        try {
            messages.add(getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMessage() throws IOException {
        BufferedReader reader = getSocketReader(socket);
        return reader.readLine();
    }

    private BufferedReader getSocketReader(Socket socket) throws IOException {
        return getReader(socket.getInputStream());
    }

    private BufferedReader getReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
