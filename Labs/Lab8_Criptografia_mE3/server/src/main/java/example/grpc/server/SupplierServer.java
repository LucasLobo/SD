package example.grpc.server;

/* grpc library */
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import java.io.InputStream;


public class SupplierServer {

	public static Key readKey(String resourcePath) throws Exception {
		System.out.println("Reading key from resource " + resourcePath + " ...");

		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		System.out.printf("%d bytes%n", encoded.length);
		SecretKeySpec keySpec = new SecretKeySpec(encoded, "AES");

		return keySpec;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(SupplierServer.class.getSimpleName() + " starting...");

		// receive and print arguments
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s port%n", Server.class.getName());
			return;
		}

		final int port = Integer.parseInt(args[0]);
		final BindableService impl = new SupplierServiceImpl();

		SupplierServer.readKey("secret.key");

		// Create a new server to listen on port
		Server server = ServerBuilder.forPort(port).addService(impl).build();

		// Start the server
		server.start();

		// Server threads are running in the background.
		System.out.println("Server started");
		System.out.println();

		// Do not exit the main thread. Wait until server is terminated.
		server.awaitTermination();
	}

}
