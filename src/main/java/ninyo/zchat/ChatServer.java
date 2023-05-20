package ninyo.zchat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ChatServer {

    private static List<Socket> sockets = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final ExecutorService thread = Executors.newSingleThreadExecutor();

    public void handle() throws IOException, ExecutionException, InterruptedException {
        List<Callable<Object>> clientHandlers = new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(9999);

        // waits for new client to connect
        Socket socket = serverSocket.accept();
        sockets.add(socket);
        clientHandlers.add(Executors.callable(new ClientHandler(socket, messages)));
        try {
            executor.invokeAll(clientHandlers);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        awaitTerminationAfterShutdown(executor);

        thread.submit(() -> {
            messages.forEach(s -> {
                sockets.forEach(socket1 -> {
                    try {
                        PrintWriter writer = getSocketWriter(socket1);
                        writer.println(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            });
        }).get();
    }

    private PrintWriter getSocketWriter(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void awaitTerminationAfterShutdown(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
