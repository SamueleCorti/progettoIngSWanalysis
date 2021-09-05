package it.polimi.ingsw.adapters;

public class DirHandler {

    public String getWorkingDirectory(){
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();

        if (OS.contains("WIN"))
            workingDirectory = System.getenv("AppData");
        else
            workingDirectory = System.getProperty("user.home");

        return workingDirectory + "/MasterOfRenaissance";
    }

}
