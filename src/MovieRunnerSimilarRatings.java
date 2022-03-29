import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

    private String movieFile;
    private String ratingsFile;

    private FourthRatings fr;

    public MovieRunnerSimilarRatings() {
        // movieFile = "ratedmovies_short.csv";
        // ratingsFile = "ratings_short.csv";

        movieFile = "ratedmoviesfull.csv";
        ratingsFile = "ratings.csv";

        fr = new FourthRatings();

        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);

        System.out.println("\nNumber of raters in file: " + RaterDatabase.size());
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        System.out.println("##############################\n");
    }

    public void printAverageRatings() {
        int minimalRaters = 35;
        ArrayList<Rating> ratings = fr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings: " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        int minimalRaters = 8;
        String genres = "Drama";
        int year = 1990;
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter(genres));
        filters.addFilter(new YearAfterFilter(year));
        ArrayList<Rating> ratings = fr.getAverageRatingsByFilter(minimalRaters, filters);
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings with genres " + genres
                + " after the year " + year + ": " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " "
                        + MovieDatabase.getTitle(rating.getItem()));
                System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
            }
        }
    }

    public void printSimilarRatings() {
        String raterId = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList<Rating> ratings = fr.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);
        System.out.println(MovieDatabase.getTitle(ratings.get(0).getItem()));
    }

    public void printSimilarRatingsByGenre() {
        String raterId = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        String genre = "Mystery";
        Filter filter = new GenreFilter(genre);
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, filter);
        System.out.println(MovieDatabase.getTitle(ratings.get(0).getItem()));
    }

    public void printSimilarRatingsByDirector() {
        String raterId = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        Filter filter = new DirectorsFilter(directors);
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, filter);
        System.out.println(MovieDatabase.getTitle(ratings.get(0).getItem()));
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        String raterId = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        int minMin = 80;
        int maxMin = 160;
        String genre = "Drama";
        AllFilters filter = new AllFilters();
        filter.addFilter(new GenreFilter(genre));
        filter.addFilter(new MinutesFilter(minMin, maxMin));
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, filter);
        System.out.println(MovieDatabase.getTitle(ratings.get(0).getItem()));
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        String raterId = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        int minMin = 70;
        int maxMin = 200;
        int yearAfter = 1975;
        AllFilters filter = new AllFilters();
        filter.addFilter(new YearAfterFilter(yearAfter));
        filter.addFilter(new MinutesFilter(minMin, maxMin));
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, filter);
        System.out.println(MovieDatabase.getTitle(ratings.get(0).getItem()));
    }

    public static void main(String[] args) {
        MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();
        // runner.printAverageRatings();
        // System.out.println("\n=========================================================\n");
        // runner.printAverageRatingsByYearAfterAndGenre();
        // runner.printSimilarRatings();
        // runner.printSimilarRatingsByGenre();
        // runner.printSimilarRatingsByDirector();
        // runner.printSimilarRatingsByGenreAndMinutes();
        runner.printSimilarRatingsByYearAfterAndMinutes();
    }
}