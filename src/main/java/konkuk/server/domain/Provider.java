package konkuk.server.domain;

public enum Provider {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    NAVER("naver"),
    KAKAO("kakao");

    private String registrationId;

    Provider(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public static Provider from(String registrationId) {
        for (Provider provider : Provider.values()) {
            if (provider.getRegistrationId().equalsIgnoreCase(registrationId)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown registration ID: " + registrationId);
    }
}
