package Module.GenerateTree;

import java.io.Serializable;
import java.util.Arrays;

enum SequenceType {
    AUTO, DNA, AA, CODON, NT2AA, BIN, MORPH
}

enum BootstrapAnalysis {
    NONE, ULTRAFAST, STANDARD
}

enum StateFrequency {
    Empirical, MlOptimized, CodonF1x4, CodonF3x4, AAModel
}

enum PartitionType {
    Linked, Unlinked
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class GenerateTreeRequestEntity implements Serializable {
    public InputData inputData = new InputData();
    public BranchSupportAnalysis branchSupportAnalysis = new BranchSupportAnalysis();
    public SubstitutionOption substitutionOption = new SubstitutionOption();
    public SearchParameters searchParameters = new SearchParameters();

    public GenerateTreeRequestEntity() {
    }
}

class SingleBranchTest implements Serializable {
    public boolean sHaLRTTest = false;
    public Integer replicates = 1000;
    public boolean approximateBayes = false;

}
class SearchParameters implements Serializable{
    public double perturbationStrength = 0.5;
    public Integer stoppingRule = 100;
}


class InputData implements Serializable {
    public Integer genericCode = 0;
    public boolean useExampleAlignmentFile = false;
    public SequenceType sequenceType = SequenceType.AUTO;
    public PartitionType partitionType = PartitionType.Linked;
}

class BranchSupportAnalysis implements Serializable {
    public BootstrapAnalysis bootstrapAnalysis = BootstrapAnalysis.NONE;
    public Integer numberBootstrap = 100;
    public boolean createUfBootFile = false;
    public Integer maxIteration = 1000;
    public double minCorrelation = 0.9;
    public SingleBranchTest singleBranchTest = new SingleBranchTest();
}

class SubstitutionOption implements Serializable {
    //    public static List<String> BinaryOptions = new ArrayList<String>([]);
    public static String[] BinaryOptions = {"JC2", "GTR2"};
    public static String[] DnaOptions = {
            "JC", "F81", "K80", "HKY",
            "TNe", "TN", "K81", "K81u",
            "TPM2", "TPM2u", "TPM3", "TPM3u",
            "TIMe", "TIM", "TIM2e", "TIM2",
            "TIM3e", "TIM3", "TVMe", "TVM", "SYM", "GTR"};
    public static String[] ProteinOptions = {
            "Blosum62", "cpREV", "Dayhoff", "DCMut",
            "FLU", "HIVb", "HIVw", "JIT",
            "JTTDCMut", "LG", "mtART", "mtMAM",
            "mtREV", "mtZOA", "PMB", "rtREV",
            "VT", "WAG"
    };

    public static String[] MixtureOptions = {
            "LG4M", "LG4X", "JTT+CF4", "C10",
            "C20", "EX2", "EX3", "EH0",
            "UL2", "UL3", "EX_EH0"
    };
    public static String[] CodonOptions = {
            "GY", "MG", "MGK", "GY0K",
            "GY1KTS", "GY1KTV", "GY2K", "MG1KTS",
            "MG1KTV", "MG2K", "KOSI07", "SCHN05"
    };
    public static String[] MorphologyOptions = {
            "GY", "MG", "MGK", "GY0K",
            "GY1KTS", "GY1KTV", "GY2K", "MG1KTS",
            "MG1KTV", "MG2K", "KOSI07", "SCHN05"
    };
    public String substitutionModel = "Auto";
    public StateFrequency stateFrequency = StateFrequency.AAModel;
    public boolean ascertainmentCorrection = false;
    public String[] rateHeterogeneityOptions = {};
    public boolean freeRateHeterogeneity = false;
    public Integer rateCategory = 0;

    public boolean correctSubstitutionModel(SequenceType sequenceType) {
        if (substitutionModel.toLowerCase().equals("auto")) return true;
        switch (sequenceType) {
            case AA:
                this.substitutionModel = Arrays.stream(ProteinOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case BIN:
                this.substitutionModel = Arrays.stream(BinaryOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case DNA:
                this.substitutionModel = Arrays.stream(DnaOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case CODON:
                this.substitutionModel = Arrays.stream(CodonOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case MORPH:
                this.substitutionModel = Arrays.stream(MorphologyOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case NT2AA:
                this.substitutionModel = Arrays.stream(MixtureOptions).filter(dna -> dna.toLowerCase().equals(this.substitutionModel.toLowerCase())).findAny().orElse("Auto");
                break;
            case AUTO:
                this.substitutionModel = "Auto";
                return true;
        }
        return !this.substitutionModel.equals("Auto");
    }
}