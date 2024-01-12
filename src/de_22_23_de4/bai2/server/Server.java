package de_22_23_de4.bai2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server {
    private ServerSocket server;
    public Server() throws IOException, SQLException {
        this.server = new ServerSocket(54321);
        while(true){
            ThreadServer client = new ThreadServer(server.accept());
            client.run();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        new Server();
    }
}
