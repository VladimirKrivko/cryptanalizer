public interface BiasKey {

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
}
