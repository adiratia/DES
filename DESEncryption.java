public class DESEncryption {

    public static void main(String[] args) {
        DES des = new DES();
        try {
            String key = "nonsense";
            String plainText = "thoughts";
            System.out.println("Input: " + plainText);
            System.out.println("Key: " + key);
            String cypherText = des.encrypt(key, plainText);
            System.out.println("Encryption: " + cypherText);
            System.out.println("Decryption: " + des.decrypt(key, cypherText));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}