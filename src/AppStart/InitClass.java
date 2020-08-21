package AppStart;

import Module.Log.LogService;
import Module.GenerateTree.Message;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Enumeration;

import static java.sql.DriverManager.deregisterDriver;
import static java.sql.DriverManager.getDrivers;

public class InitClass extends ResourceConfig implements ServletContextListener {

    public InitClass() {
        register(new ApplicationBinder());
        packages(true);
    }

    public void contextInitialized(ServletContextEvent arg0) {
//        DatabaseEntity.setFileDir("database.txt");
//        List<DatabaseModel> databaseModel = Lists.newArrayList(new DatabaseModel(0,0,"localhost","btl","root","root"));
//        DatabaseEntity.databaseModels = databaseModel;
//        DatabaseEntity.loadData();
//        File file = new File(Tools.FullPath + GetPDF.LOCAL_URL);
//        System.out.println(file.getPath());
//        System.out.println(file.getAbsolutePath());
//        DiemThi.makeData(file);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        try {
//            DatabaseEntity.saveData();
            Enumeration<Driver> drivers = getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                try {
                    deregisterDriver(driver);
                } catch (SQLException e) {
                }
            }
            LogService.LogServices.forEach((a,service)->{
                Message message = new Message();
                message.status = "Canceled";
                try {
                    service.close(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            });

//            for (WatchService watchService : FileWatcher.getWatchServices()){
//                try {
//                    watchService.close();
//                } catch (IOException e) {
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}