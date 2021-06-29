package it.polimi.ingsw.server.messages.showingMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

/**
 * Self explanatory name
 */
public class DashboardMessage implements Message {

    private String jsonDashboard;

    public String getJsonDashboard() {
        return jsonDashboard;
    }

    public DashboardMessage(Dashboard dashboard) {
        Gson dashboardGson = new GsonBuilder().setPrettyPrinting().create();
        String dashboardJson = dashboardGson.toJson(dashboard);
        this.jsonDashboard = dashboardJson;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) {
            System.out.println("it is a dashboard message!");
            System.out.println("the dashboard is" + jsonDashboard);
        }
    }

}
