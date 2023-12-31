package server;

import entities.ConnectedClient;
import entities.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * Server configuration class that extends AbstractServer and handles client
 * connections and messages.
 */
public class ServerConfiguration extends AbstractServer {

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	// This holds the list of the connected clients to the server and their status
	private static ObservableList<ConnectedClient> clientList = FXCollections.observableArrayList();

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public ServerConfiguration(int port) {
		super(port);
	}

	/**
	 * Gets the list of connected clients.
	 *
	 * @return The list of connected clients.
	 */
	public static ObservableList<ConnectedClient> getClientList() {
		return clientList;
	}

	/**
	 * Sets the list of connected clients.
	 *
	 * @param clientList The list of connected clients.
	 */
	public static void setClientList(ObservableList<ConnectedClient> clientList) {
		ServerConfiguration.clientList = clientList;
	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ServerMessageHandler.getMessageHandlerInstance().handleMessages(msg, client);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		try {
			DatabaseConnector.getDatabaseConnectorInstance().getConnection();
		} catch (Exception ex) {
			System.out.println("Error! DataBase Connection Failed");
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

}
