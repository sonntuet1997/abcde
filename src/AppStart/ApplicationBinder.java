package AppStart;

import Module.EncryptFile.EncryptFileService;
import Module.EncryptKey.EncryptKeyService;
import Module.File.FileService;
import Module.GenerateTree.GenerateTreeService;
import Module.Tree.TreeService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
//        bind(new KeyShareService()).to(KeyShareService.class);
        bind(new EncryptFileService()).to(EncryptFileService.class);
        bind(new EncryptKeyService()).to(EncryptKeyService.class);
        bind(new FileService()).to(FileService.class);
        bind(new GenerateTreeService()).to(GenerateTreeService.class);
        bind(new TreeService()).to(TreeService.class);
    }
}