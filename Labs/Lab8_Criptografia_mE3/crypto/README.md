# Java Cryptography examples

This is a Java module that contains Java Cryptography API tests and example classes.

Demonstration:

- The default example shows a demonstration of the Java Cryptography API


Utilities:

- ListAlgorithms presents the (long) list of available security providers and 
the cryptographic algorithms that they implement.

- The SymKey and AsymKeys examples show how to read and write cryptographic keys 
to and from files.

- SecureRandomNumber generates random numbers that are unpredictable 
(contrary to pseudo-random number generators).
The numbers are printed as hexadecimal values.


Tests:

- SymCrypto generates a key and uses it to cipher and decipher data.
 
- AsymCrypto generates a key pair and uses the public key to cipher and
the private key to decipher data (and then the other way around).

- Digest creates a cryptographic hash.

- Message Authentication Code (MAC) shows data integrity verification with symmetric keys.

- Digital Signature shows data signing and verification with asymmetric keys.

- XMLCrypto shows how to insert and retrieve cipher text in XML documents 
using base 64 encoding to represent bytes as text.


## Instructions using Maven:

To run the default example using the execution plug-in:

```
    mvn compile exec:java
```

To list available profiles (one for each example):

```
    mvn help:all-profiles
```

To run a specific example, select the profile with -P:

```
    mvn exec:java -P list-algos
```

To compile and execute all tests:

```
    mvn test
```

To execute a specific test suite:

```
    mvn test -Dtest=AsymCrypto*
```

To execute a specific test:

```
    mvn test -Dtest=AsymCrypto*#testCipherPrivate*
```


## To configure the Maven project in Eclipse

'File', 'Import...', 'Maven'-'Existing Maven Projects'

'Select root directory' and 'Browse' to the project base folder.

Check that the desired POM is selected and 'Finish'.


----

[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)
