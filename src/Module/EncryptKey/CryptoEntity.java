package Module.EncryptKey;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class CryptoEntity implements Serializable {
    public String certificate;
    public String privateKey;
    public String data;
    public String sign;
    public CryptoEntity() {
    }
}
