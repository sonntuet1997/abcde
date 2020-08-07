package Module.File;


import javax.ws.rs.core.StreamingOutput;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class EncryptFileEntity implements Serializable {
    public String key;
    public StreamingOutput data;
    public String hash;
    public EncryptFileEntity() {
    }
}
