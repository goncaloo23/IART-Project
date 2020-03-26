package iart.city_plan.solver;

import iart.city_plan.graph.Graph;
import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.Coordinate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HillClimbingStrategy extends Strategy {
    private Solution solution = new Solution();
    private Graph graph = new Graph();

    public HillClimbingStrategy(City city) {
        super(city);
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        // generate random solution
        Solution candidateSolution = generateRandomSolution(buildingProjects);

        // generate neighbour solutions
        List<Solution> solutionList = generateNeighbourSolutions(candidateSolution);

        // score neighbour solutions (check constraints)
        Scorer.score(solutionList);

        // use solution with highest score
        // back to top until no highest scored solution

        return candidateSolution;
    }

    private List<Solution> generateNeighbourSolutions(Solution candidateSolution) {
        List<Solution> solutionList = new LinkedList<>();

        return solutionList;
    }

    private Solution generateRandomSolution(List<BuildingProject> buildingProjects) {
        Solution candidateSolution = new Solution();

        Random random = new Random();
        for (BuildingProject buildingProject : buildingProjects) {
            Coordinate coordinate = getCoordinate(random);
            int counter = 0;

            while (placeBuilding(coordinate, buildingProject, candidateSolution) == null && counter++ < 10) {
                coordinate = getCoordinate(random);
            }
        }

        return candidateSolution;
    }

    private Coordinate getCoordinate(Random random) {
        int row = random.nextInt(rows);
        int col = random.nextInt(columns);
        return new Coordinate(col, row);
    }

    private Solution placeBuilding(Coordinate coordinate, BuildingProject buildingProject, Solution solution) {
        List<Coordinate> coordsToFill = new LinkedList<>();
        System.out.println("Attempting to place building with id " + buildingProject.getID() + " in coordinate " + coordinate);

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int x = coordinate.getY() + col;
                int y = coordinate.getX() + row;

                Coordinate candidateCoord = new Coordinate(x, y);
                System.out.println("\tTrying cell " + candidateCoord);
                if (x >= rows || y >= columns || !super.city.get(candidateCoord).equals(0))
                    return null;

                if (buildingProject.getPlan()[row].charAt(col) != '.') {
                    coordsToFill.add(candidateCoord);
                }
            }
        }

        solution.addBuilding(buildingProject, coordsToFill.get(0));
        for (Coordinate coordToFill : coordsToFill) {
            System.out.println("\tPlaced on cell " + coordToFill);
            city.put(coordToFill, buildingProject.getID());
        }

        return solution;
    }
}
