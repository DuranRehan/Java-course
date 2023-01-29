package g56055.atlg.stibride.models;

import g56055.atlg.stibride.models.data.dto.FavoriteDto;
import g56055.atlg.stibride.models.data.dto.LinesDto;
import g56055.atlg.stibride.models.data.dto.StationsDto;
import g56055.atlg.stibride.models.data.dto.StopsDto;
import g56055.atlg.stibride.models.data.exception.RepositoryException;
import g56055.atlg.stibride.models.data.repository.FavoriteRepo;
import g56055.atlg.stibride.models.data.repository.StationsRepo;
import g56055.atlg.stibride.models.data.repository.StationsRepoNL;
import g56055.atlg.stibride.models.data.repository.StopsRepo;
import g56055.atlg.stibride.models.utils.graph.Dijkstra;
import g56055.atlg.stibride.models.utils.graph.Graph;
import g56055.atlg.stibride.models.utils.graph.Node;
import g56055.atlg.stibride.models.utils.observers.Observable;
import g56055.atlg.stibride.models.utils.observers.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Observable {
    private Graph graph;
    private final List<StationsDto> allStations;
    private final Map<Integer, Node> allStationNode;
    private List<StationsDto> allRawStation;
    private StopsRepo stopsRepo;
    private StationsRepo stationsRepo;
    private FavoriteRepo favoriteRepo;
    private final List<StationsDto> shortestPath;
    private final List<FavoriteDto> allFavorite;
    private final List<Observer> observers;

    public Model() {
        allStations = new ArrayList<>();
        allRawStation = new ArrayList<>();
        allStationNode = new HashMap<>();
        observers = new ArrayList<>();
        allFavorite = new ArrayList<>();
        shortestPath = new ArrayList<>();
    }

    public void initialize() {
        try {
            stopsRepo = new StopsRepo();
            stationsRepo = new StationsRepo();
            favoriteRepo = new FavoriteRepo();
            allRawStation.addAll(stationsRepo.getAll());
            allFavorite.addAll(favoriteRepo.getAll());

            for (StopsDto dto : stopsRepo.getAll()) {
                StationsDto station = stationsRepo.get(dto.getStations());
                allStations.add(station);
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void createGraph() {
        graph = new Graph();
        for (StationsDto dto : allStations) {
            try {
                List<LinesDto> allLines = stopsRepo.getAllLines(dto.getKey());
                dto.setLines(allLines);
                allStationNode.put(dto.getKey(), new Node(dto));
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }

        for (Node node : allStationNode.values()) {
            try {
                for (StopsDto stop : stopsRepo.getAdjacent(node.getStation().getKey())) {
                    node.addDestination(allStationNode.get(stop.getStations()), 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Node node : allStationNode.values()) {
            graph.addNode(node);
        }
    }

    public void search(StationsDto source, StationsDto destination) {
        System.out.println(Thread.activeCount());
        Thread t = new Thread(() -> {
            createGraph();
            shortestPath.clear();
            Graph shortestGraph = Dijkstra.calculateShortestPathFromSource(graph, allStationNode.get(source.getKey()));
            for (Node node : shortestGraph.getNodes()) {
                if (node.getStation().getKey().equals(destination.getKey())) {
                    for (Node shortNode : node.getShortestPath()) {
                        shortestPath.add(shortNode.getStation());
                    }
                }
            }
            try {
                destination.setLines(stopsRepo.getAllLines(destination.getKey()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            shortestPath.add(destination);
            notifyObserver("SEARCH");
            System.out.println(Thread.activeCount());
        });
        t.start();
        System.out.println(Thread.activeCount());
    }

    public void addFavorite(FavoriteDto item) {
        try {
            favoriteRepo.add(item);
            allFavorite.clear();
            allFavorite.addAll(favoriteRepo.getAll());
            notifyObserver("ADD_FAV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFavorite(String key) {
        try {
            favoriteRepo.remove(key);
            allFavorite.clear();
            allFavorite.addAll(favoriteRepo.getAll());

            notifyObserver("DELETE_FAV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNl(){
        try{
            StationsRepoNL rep = new StationsRepoNL();
            allRawStation = rep.getAll();
            notifyObserver("NL_MODE");
            allStations.clear();
            for (StopsDto dto : stopsRepo.getAll()) {
                StationsDto station = rep.get(dto.getStations());
                allStations.add(station);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<StationsDto> getAllStations() {
        return allRawStation;
    }

    public List<StationsDto> getShortestPath() {
        return shortestPath;
    }

    public List<FavoriteDto> getAllFavorite() {
        return allFavorite;
    }

    @Override
    public void subscribe(Observer v) {
        observers.add(v);
    }

    @Override
    public void unsubscribe(Observer v) {
        observers.remove(v);
    }

    @Override
    public void notifyObserver(String toUpdate) {
        for (Observer observer : observers) {
            observer.update(toUpdate);
        }
    }


}
