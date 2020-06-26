package wiki.zex.cloud.example.enums;

public enum CaptchaType {

    LOGIN("1001"),;


    String templateCode;

    CaptchaType(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }
}
