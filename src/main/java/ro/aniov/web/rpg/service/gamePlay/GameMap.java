/**
 * Created by Marius on 12/20/2016.
 */
package ro.aniov.web.rpg.service.gamePlay;

import ro.aniov.web.rpg.model.characters.Villain.VillainType;

import java.awt.*;
import java.util.Random;

public class GameMap {

    private int level;
    private int size;
    private String[][] theMap;
    private Point heroPosition;
    private Point nextHeroPosition;
    private Long heroId;
    private boolean gameWin;

   // public static Set<Long> hId = new HashSet<>();
   // public static Set<GameMap> gameMapsInstances = new HashSet<>();

    final public static String empty = "Empty";
    final public static String hero = "Hero";
    final public static String passed = "Passed";

    public GameMap(int heroLevel, Long heroId) {
        this.level = heroLevel;
        this.heroId = heroId;
        generateMap();

     //   hId.add(heroId);
     //   gameMapsInstances.add(this);
    }

    private void generateMap(){
        size = 3 + 2 * level;
        initializeMap();
        putHeroOnMap();
        putVillainsOnMap();
    }

    private void initializeMap(){
        theMap = new String[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.theMap[i][j] = empty;
    }
    /** Put Hero in center of the map*/
    private void putHeroOnMap(){
        theMap[size / 2][size / 2] = "Hero";
        heroPosition = new Point(size / 2, size / 2);
    }

    private void putVillainsOnMap(){
        Random random = new Random();

        int nrOfVillains = random.nextInt(size / 2);
        nrOfVillains += size;

        /** Create a Point with X, Y coordinates*/
        Point point = new Point();

        while (nrOfVillains > 0){
            point.setLocation(random.nextInt(size), random.nextInt(size));

            if (theMap[point.x][point.y].equals(empty)) {
                int villainType = random.nextInt(VillainType.values().length);
                theMap[point.x][point.y] = VillainType.values()[villainType].name();
                nrOfVillains--;
            }
        }
    }

    public void setOnMap(Point point, String str){
        theMap[point.x][point.y] = str;
    }

    public String getFromMap(Point point){
        return theMap[point.x][point.y];
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[][] getTheMap() {
        return theMap;
    }

    public void setTheMap(String[][] theMap) {
        this.theMap = theMap;
    }

    public static String getEmpty() {
        return empty;
    }

    public Long getHeroId() {
        return heroId;
    }

    public Point getHeroPosition() {
        return heroPosition;
    }

    public void setHeroPosition(Point heroPosition) {
        this.heroPosition = heroPosition;
    }

    public Point getNextHeroPosition() {
        return nextHeroPosition;
    }

    public void setNextHeroPosition(Point nextHeroPosition) {
        this.nextHeroPosition = nextHeroPosition;
    }

    public boolean isGameWin() {
        return gameWin;
    }

    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }
}
