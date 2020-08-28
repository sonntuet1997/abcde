package Module.GenerateTree;

import Module.Exception.CustomException;
import Module.Log.LogEntity;
import Module.Log.LogService;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.util.Hex;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static Module.IqtreePathEntity.*;

/**
 * Created by Son on 6/15/2017.
 */
public class GenerateTreeService {

    public GenerateTreeService() {
        try {
            Files.createDirectories(Paths.get(IQTREE_DATAFOLDER));
            Files.createDirectories(Paths.get(IQTREE_RESULTFOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getRandomKey() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);
        return array;
    }

    public String getLogContent(String filename) throws IOException {
        return FileUtils.readFileToString(new File(IQTREE_RESULTFOLDER, filename + ".log"), StandardCharsets.UTF_8);
    }

    public String saveDataFile(InputStream input, FormDataContentDisposition info) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        DigestInputStream dis = new DigestInputStream(input, md);
        File file = new File(info.getFileName());
        FileUtils.copyInputStreamToFile(dis, file);
        String digest = Hex.getString(md.digest());
        String fileName = digest + "-" + info.getFileName();
        if (file.renameTo(new File(IQTREE_DATAFOLDER, fileName))) {
            file.delete();
        }
        return fileName;
    }

    public LogEntity startGenerate(
            GenerateTreeRequestEntity generateTreeRequestEntity,
            FormDataBodyPart content,
            FormDataContentDisposition alignmentFileInfo,
            InputStream alignmentFileInput,
            FormDataContentDisposition partitionFileInfo,
            InputStream partitionFileInput) {
        try {
            String alignmentFileName;
            List<String> command = new ArrayList<>();
            File alignmentFile;
            LogEntity logEntity = new LogEntity();
            if (generateTreeRequestEntity.inputData.useExampleAlignmentFile) {
                alignmentFile = new File(IQTREE_EXAMPLEALIGNMENT);
                alignmentFileName = alignmentFile.getName();
                logEntity.processedFile = alignmentFileName;
            } else {
                if (alignmentFileInput == null) {
                    throw new CustomException(400, "AlignmentFile must not null");
                }
                alignmentFileName = saveDataFile(alignmentFileInput, alignmentFileInfo);
                alignmentFile = new File(IQTREE_DATAFOLDER, alignmentFileName);
                logEntity.processedFile = alignmentFileInfo.getFileName();
            }
            logEntity.isProcessing = LogService.checkLog(logEntity);
            logEntity.url = alignmentFileName;

            //

            //TODO: linux  Compatibility
            String systemName = System.getProperty("os.name")
                    .toLowerCase();
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File(IQTREE_BIN));
            File resultFolder = new File(IQTREE_RESULTFOLDER, alignmentFileName);
            if (systemName.startsWith("windows")) {
                command.addAll(Arrays.asList("cmd.exe", "/c", "iqtree", "-pre", resultFolder.getAbsolutePath(), "-s", alignmentFile.getAbsolutePath(), "-redo"));
            } else if (systemName.startsWith("mac os x")) {
                command.addAll(Arrays.asList("./iqtree", "-pre", resultFolder.getAbsolutePath(), "-s", alignmentFile.getAbsolutePath(), "-redo"));
            }
            if (partitionFileInput != null) {
                String partitionFileName = saveDataFile(partitionFileInput, partitionFileInfo);
                if (partitionFileName.split("-")[0].equals(alignmentFileName.split("-")[0])) {
                    return null;
                    //TODO: Notify error
                }
                File partitionFile = new File(IQTREE_DATAFOLDER, partitionFileName);
                if (generateTreeRequestEntity.inputData.partitionType == PartitionType.Linked) {
                    command.addAll(Arrays.asList("-spp", partitionFile.getAbsolutePath()));
                } else {
                    command.addAll(Arrays.asList("-sp", partitionFile.getAbsolutePath()));
                }
            }

            //TODO: TreeFile ???


            if (generateTreeRequestEntity.inputData.sequenceType != SequenceType.AUTO) {
                String sequenceType = generateTreeRequestEntity.inputData.sequenceType.name();
                if (generateTreeRequestEntity.inputData.sequenceType != SequenceType.CODON && generateTreeRequestEntity.inputData.sequenceType != SequenceType.NT2AA) {
                    if (generateTreeRequestEntity.inputData.genericCode > 0 && generateTreeRequestEntity.inputData.genericCode <= 25) {
                        sequenceType += generateTreeRequestEntity.inputData.genericCode.toString();
                    }
                }
                command.addAll(Arrays.asList("-st", sequenceType));
            }

            //TODO: Criterion ???
            String mCommand = "";
            generateTreeRequestEntity.substitutionOption.correctSubstitutionModel(generateTreeRequestEntity.inputData.sequenceType);
            if (generateTreeRequestEntity.substitutionOption.freeRateHeterogeneity) {
                mCommand += ("+R" + (generateTreeRequestEntity.substitutionOption.rateCategory != 0 ? generateTreeRequestEntity.substitutionOption.rateCategory.toString() : ""));
            } else {
                if (generateTreeRequestEntity.substitutionOption.substitutionModel.equals("Auto")) {
                    mCommand += "TEST";
                } else {
                    mCommand += generateTreeRequestEntity.substitutionOption.substitutionModel;
                    mCommand += Arrays.stream(generateTreeRequestEntity.substitutionOption.rateHeterogeneityOptions).map(option -> {
                        switch (option.toLowerCase()) {
                            case "gamma":
                                return "+G";
                            case "invar":
                                return "+I";
                            default:
                                return null;
                        }
                    }).distinct().reduce("", (sub, t) -> sub + t);
                    switch (generateTreeRequestEntity.substitutionOption.stateFrequency) {
                        case AAModel:
                            break;
                        case CodonF1x4:
                            mCommand += "+F1x4";
                            break;
                        case CodonF3x4:
                            mCommand += "+F3x4";
                            break;
                        case Empirical:
                            mCommand += "+F";
                        case MlOptimized:
                            mCommand += "+F0";
                    }
                    if (Arrays.stream(generateTreeRequestEntity.substitutionOption.rateHeterogeneityOptions).noneMatch(option -> option.toLowerCase().equals("invar"))) {
                        if (generateTreeRequestEntity.substitutionOption.ascertainmentCorrection) {
                            mCommand += "+ASC";
                        }
                    }
                }
            }
            command.addAll(Arrays.asList("-m", mCommand));
            if (generateTreeRequestEntity.branchSupportAnalysis.bootstrapAnalysis == BootstrapAnalysis.ULTRAFAST) {
                command.addAll(Arrays.asList("-bb", generateTreeRequestEntity.branchSupportAnalysis.numberBootstrap.toString()));
            } else if (generateTreeRequestEntity.branchSupportAnalysis.bootstrapAnalysis == BootstrapAnalysis.STANDARD) {
                //TODO: Check if greater 100???
                //TODO: Check value
                if (generateTreeRequestEntity.branchSupportAnalysis.numberBootstrap != 100) {
                    return null;
                }
                command.addAll(Arrays.asList("-b", generateTreeRequestEntity.branchSupportAnalysis.numberBootstrap.toString()));
            }
            if (generateTreeRequestEntity.branchSupportAnalysis.createUfBootFile) {
                command.add("-wbt");
            }
            if (generateTreeRequestEntity.branchSupportAnalysis.maxIteration >= 1000) {
                command.addAll(Arrays.asList("-nm", generateTreeRequestEntity.branchSupportAnalysis.maxIteration.toString()));
            }
            if (generateTreeRequestEntity.branchSupportAnalysis.minCorrelation >= 0.9 && generateTreeRequestEntity.branchSupportAnalysis.minCorrelation <= 1) {
                command.addAll(Arrays.asList("-bcor", String.valueOf(generateTreeRequestEntity.branchSupportAnalysis.minCorrelation)));
            }
            if (generateTreeRequestEntity.branchSupportAnalysis.singleBranchTest.sHaLRTTest) {
                command.addAll(Arrays.asList("-alrt", generateTreeRequestEntity.branchSupportAnalysis.singleBranchTest.replicates.toString()));
            }
            if (generateTreeRequestEntity.branchSupportAnalysis.singleBranchTest.approximateBayes) {
                command.add("-abayes");
            }
            if (generateTreeRequestEntity.searchParameters.perturbationStrength >= 0 && generateTreeRequestEntity.searchParameters.perturbationStrength <= 1) {
                command.addAll(Arrays.asList("-pers", String.valueOf(generateTreeRequestEntity.searchParameters.perturbationStrength)));
            }
            if (generateTreeRequestEntity.searchParameters.stoppingRule >= 100) {
                command.addAll(Arrays.asList("-numstop", String.valueOf(generateTreeRequestEntity.searchParameters.stoppingRule)));
            }
            builder.command(command);
            System.out.println(builder.command());
            LogService logService = new LogService(logEntity, builder);
            logService.start();
            return logEntity;
//            int exitCode = process.waitFor();
//            assert exitCode == 0;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
