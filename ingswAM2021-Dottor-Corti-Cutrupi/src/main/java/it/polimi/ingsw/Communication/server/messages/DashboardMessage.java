package it.polimi.ingsw.Communication.server.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;

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
    /*private Warehouse warehouse;
    private Strongbox strongbox;
    private LeaderCardZone leaderCardZone;
    private ArrayList<DevelopmentCardZone> developmentCardZones;
    private PapalPath papalPath;
    private ArrayList<ExtraDepot> extraDepots;
    private ArrayList<Resource> whiteToColorResources;
    private ArrayList<Resource> discountedResources;
    //resources that represent the extra productions brought by the Leader Power
    private ArrayList<Resource> resourcesForExtraProd;
    //resources produced in this turn, at the end of the turn they will be moved in the strongbox
    private ArrayList <Resource> resourcesProduced;
    //number of resources consumed by the standard prod
    private int numOfStandardProdRequirements;
    //number of resources produced by the standard prod
    private int numOfStandardProdResults;

    public DashboardMessage(Dashboard dashboard) {
        this.warehouse = dashboard.getWarehouse();
        this.strongbox = dashboard.getStrongbox();
        this.leaderCardZone = dashboard.getLeaderCardZone();
        this.developmentCardZones = dashboard.getDevelopmentCardZones();
        this.papalPath = dashboard.getPapalPath();
        this.extraDepots = dashboard.getExtraDepots();
        this.whiteToColorResources = dashboard.getWhiteToColorResources();
        this.discountedResources = dashboard.getDiscountedResources();
        this.resourcesForExtraProd = dashboard.getResourcesForExtraProd();
        this.resourcesProduced = dashboard.getResourcesProduced();
        this.numOfStandardProdRequirements = dashboard.getNumOfStandardProdRequirements();
        this.numOfStandardProdResults = dashboard.getNumOfStandardProdResults();
    }*/


}
