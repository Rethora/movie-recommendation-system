import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

    private String movieFile;
    private String ratingsFile;

    public MovieRunnerWithFilters() {
        movieFile = "ratedmoviesfull.csv";
        ratingsFile = "ratings.csv";
    }

    public void printAverageRatings() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings: " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByYear() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 20;
        int year = 2000;
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, new YearAfterFilter(year));
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings after or in year " + year
                + ": " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByGenre() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 20;
        String genres = "Comedy";
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, new GenreFilter(genres));
        Collections.sort(ratings);
        System.out.println("Number of moives with a minimum of " + minimalRaters + " ratings with genres " + genres
                + ": " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
                System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByMinutes() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 5;
        int min = 105;
        int max = 135;
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, new MinutesFilter(min, max));
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings that are longer than "
                + min + " minutes and shorter than " + max + " minutes: " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " "
                        + MovieDatabase.getTitle(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByDirectors() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 4;
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, new DirectorsFilter(directors));
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings, directed by | "
                + directors + " | : " + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
                System.out.println("\t" + MovieDatabase.getDirector(rating.getItem()));
            }
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 8;
        String genres = "Drama";
        int year = 1990;
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter(genres));
        filters.addFilter(new YearAfterFilter(year));
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, filters);
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

    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        System.out.println("Number of raters in file: " + tr.getRaterSize());
        MovieDatabase.initialize(movieFile);
        System.out.println("Number of movies in file: " + MovieDatabase.size());
        int minimalRaters = 3;
        int minMinutes = 90;
        int maxMinutes = 180;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        AllFilters filters = new AllFilters();
        filters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
        filters.addFilter(new DirectorsFilter(directors));
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, filters);
        Collections.sort(ratings);
        System.out.println("Number of movies with a minimum of " + minimalRaters + " ratings with directors "
                + directors + " that were longer than " + minMinutes + " and shorter than " + maxMinutes + ": "
                + ratings.size());
        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " "
                        + MovieDatabase.getTitle(rating.getItem()));
                System.out.println("\t" + MovieDatabase.getDirector(rating.getItem()));
            }
        }
    }

    public static void main(String[] args) {
        MovieRunnerWithFilters runner = new MovieRunnerWithFilters();
        runner.printAverageRatings();
        System.out.println("==============================================================");
        // runner.printAverageRatingsByYear();
        // runner.printAverageRatingsByGenre();
        // runner.printAverageRatingsByMinutes();
        // runner.printAverageRatingsByDirectors();
        runner.printAverageRatingsByYearAfterAndGenre();
        // runner.printAverageRatingsByDirectorsAndMinutes();
    }
}
