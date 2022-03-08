import JSEncrypt from 'jsencrypt'

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvZ741eLnhC644TpkedgdWacFtE6jBqLGy6qJlT' +
  '6BzATqx6A0aFryBzvFv9Vl45Qzyx27GW3iBLrsVQCa5GX2N3akRUSR/ZyzAKCH0LxBIUgmJvOd8onS8Mi39+1SErZYDVI' +
  'eZBIvCIOgE37tlRt5YqEhuGC3aAPeoyH9+53T8swIDAQAB'

const privateKey = 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK9nvjV4ueELrjhOmR52B1ZpwW0T' +
  'qMGosbLqomVPoHMBOrHoDRoWvIHO8W/1WXjlDPLHbsZbeIEuuxVAJrkZfY3dqRFRJH9nLMAoIfQvEEhSCYm853yidLwyL' +
  'f37VIStlgNUh5kEi8Ig6ATfu2VG3lioSG4YLdoA96jIf37ndPyzAgMBAAECgYBSooJWuKCBaDpUppq2Yg6E5F/O3nMjgT' +
  'DuRGHrbr79TzfytortnoaCdRcDksfrD6Ww/smuQ0ubOIPEuCtGCr89+0kYkI6Sa1ezpMTZbnN5YwpinBZKoWhJiSeQhMa' +
  'TLJDdMDWp7SAscarD5wNVAUBKmQzj5ItgitugwDYStdcqQQJBAPkIQs9eOxjThDMaJWzn/wV8r1L1jTouXiXcoBgiBg1D' +
  'crr4eiLwIqGYTa9E4rFt/qge1Qjacr4/h1KDsBHCE5MCQQC0UB10Amv9Z+1GdQvlvslhvsM6FlyxF2vf6oFQcfupL5GHI' +
  'vcXlbCcvpWzqEinjaesLII4srjBfI893CSzxWZhAkAwg1J4kTPUB0iGp/5Ic/1zNTH2aE2F2wiOOaeaa+5f4RbVo5gvfD' +
  'ZS4penoDTFOddVKwR3QA8ATuHuyiafnv4vAkB60v/dIDEzYnGsRtqd2wIOhQUzMjxdnVisJG+Fvr7wliiJZvXtqNevTdt' +
  'nj8A98i4/B+qEeKmFkzV3q9eMZXDBAkEApnGOmSddZ0srbxfo/YsVTY+Xvg7MSKPLhVDthQK4PXUEzoMUQDo67kL2V/dY' +
  '/tPlCGVLOMkAWFLVK27HgfApDg=='

export default {
  encrypt (str) {
    const encryptStr = new JSEncrypt()
    encryptStr.setPublicKey(publicKey)
    return encryptStr.encrypt(str.toString())
  },

  decrypt (str) {
    const decryptStr = new JSEncrypt()
    decryptStr.setPrivateKey(privateKey)
    return decryptStr.decrypt(str.toString())
  }
}
