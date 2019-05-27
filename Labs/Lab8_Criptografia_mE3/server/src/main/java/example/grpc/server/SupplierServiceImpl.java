package example.grpc.server;

/* helper to print binary in hexadecimal */
import static javax.xml.bind.DatatypeConverter.printHexBinary;

/* predefined types */
import com.google.protobuf.ByteString;
import com.google.type.Money;

/* these classes are generated from protobuf definitions */
import example.grpc.*;

/* grpc library */
import io.grpc.stub.StreamObserver;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SupplierServiceImpl extends SupplierGrpc.SupplierImplBase {

	@Override
	public void listProducts(ProductsRequest request, StreamObserver<SignedResponse> responseObserver) {

		System.out.println("listProducts called");
		System.out.println("Received request:");
		System.out.println("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		System.out.println(printHexBinary(requestBinary));
		System.out.printf("%d bytes%n", requestBinary.length);

		// build response
		SignedResponse.Builder signedResponseBuilder = SignedResponse.newBuilder();
		ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();
		responseBuilder.setSupplierIdentifier("Tagus Sports Store");
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("A1");
			productBuilder.setDescription("Soccer ball");
			productBuilder.setQuantity(22);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(10);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("B2");
			productBuilder.setDescription("Basketball");
			productBuilder.setQuantity(100);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(12);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("C3");
			productBuilder.setDescription("Volley ball");
			productBuilder.setQuantity(7);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(8);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}

		ProductsResponse response = responseBuilder.build();
		byte[] responseBinary = response.toByteArray();

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(responseBinary);
			byte[] digest = messageDigest.digest();

			SecretKeySpec secretKeySpec = new SecretKeySpec(SupplierServer.readKey("secret.key").getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] cipherBytes = cipher.doFinal(digest);

			Signature.Builder signatureBuilder = Signature.newBuilder();
			signatureBuilder.setSignerId("X");
			signatureBuilder.setValue(ByteString.copyFrom(cipherBytes));
			Signature signature = signatureBuilder.build();

			//signedResponseBuilder.setResponse(response);
			ProductsResponse.Builder modifiedResponseBuilder = response.toBuilder();
			modifiedResponseBuilder.setSupplierIdentifier("Ze");
			ProductsResponse modifiedResponse = modifiedResponseBuilder.build();
			signedResponseBuilder.setResponse(modifiedResponse);
			signedResponseBuilder.setSignature(signature);
			SignedResponse signedResponse = signedResponseBuilder.build();

			responseObserver.onNext(signedResponse);

			System.out.println(signedResponse.getResponse().getSupplierIdentifier());

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*System.out.println("Response to send:");
		System.out.println(response);
		System.out.println("in binary hexadecimals:");
		byte[] responseBinary = response.toByteArray();
		System.out.println(printHexBinary(responseBinary));
		System.out.printf("%d bytes%n", responseBinary.length);*/



		// send single response back
		//responseObserver.onNext(response);
		// complete call
		responseObserver.onCompleted();
	}

}
