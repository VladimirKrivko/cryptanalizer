
import java.io.IOException;

public class Decrypt extends Cipher implements BiasKey {
    final private String fileOutputName;
    public Decrypt(String fileInputName, int encryptionKey) {
        super(fileInputName, encryptionKey);
        this.fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".")) +
                "_ENCRYPT" + fileInputName.substring(fileInputName.lastIndexOf("."));
    }

    public void decrypt() throws IOException {
        super.encrypt();
    }

    public static int getEncryptKey(String key) {
        return -BiasKey.getEncryptKey(key);
    }

    public String getFileOutputName() {
        return fileOutputName;
    }

}