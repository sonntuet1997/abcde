package Module.GenerateTree;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class GenerateTreeRequestEntity implements Serializable {
    public InputData inputData;
    public GenerateTreeRequestEntity() {
    }
}

enum SequenceType {
    AUTO,DNA,AA,CODON,NT2AA,BIN,MORPH
}

class InputData implements Serializable {
    public String genericCode;
    public boolean useExampleAlignmentFile = false;
    public SequenceType sequenceType = SequenceType.AUTO;

}
