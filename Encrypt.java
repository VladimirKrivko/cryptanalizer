
public class Encrypt extends Cipher implements BiasKey {
    final private String fileOutputName;
    public Encrypt(String fileInputName, int encryptionKey) {
        super(fileInputName, encryptionKey);
        this.fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".")) +
                "_ENCRYPT" + fileInputName.substring(fileInputName.lastIndexOf("."));
    }

    public static int getEncryptKey(String key) {
        char[] chars = key.toCharArray();
        int realEncryptKey = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                realEncryptKey += chars[i];
            } else {
                realEncryptKey -= chars[i];
            }
        }
        return realEncryptKey;
    }

    public String getFileOutputName() {
        return fileOutputName;
    }

}