package it.polimi.ingsw.Communication.server.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ShieldResource;

public class DashboardMessage implements Message{

    /*private Resource resource;

    public DashboardMessage(){
        resource = new ShieldResource();
    }*/
    /*private String jsonDashboard;

    public String getJsonDashboard() {
        return jsonDashboard;
    }

    public DashboardMessage(Dashboard dashboard) {
        Gson dashboardGson = new GsonBuilder().setPrettyPrinting().create();
        String dashboardJson = dashboardGson.toJson(dashboard);
        this.jsonDashboard = dashboardJson;
    }*/
}
