import JSEncrypt from "jsencrypt";

const publicKey =
  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEUQfE8Wq9kfGprn6WCZARFwnBUtv0YoT92+" +
  "jpkVosDXD1bU58UAYWW3oOVcC1LYV9SjJUfBQs+Wo/wAn01Q0O59mL+3XIwFCoZ2ge8SoGvi7uph7tZNulZ4ZSHjC4" +
  "lsacBya+I724N0G9hF4vqKTItBQ0YyF0hof/f4PSmbfjBQIDAQAB";

export default {
  encrypt(str) {
    const encryptStr = new JSEncrypt();
    encryptStr.setPublicKey(publicKey);
    return encryptStr.encrypt(str);
  },
};
