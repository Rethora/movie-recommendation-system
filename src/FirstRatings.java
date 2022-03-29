import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

class FirstRatings {

    public ArrayList<Movie> loadMovies(String filename) throws IOException {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord r : records) {
            Movie movie = new Movie(r.get("id"), r.get("title"), r.get("year"), r.get("genre"), r.get("director"),
                    r.get("country"), r.get("poster"), Integer.parseInt(r.get("minutes")));
            movies.add(movie);
        }
        return movies;
    }

    public ArrayList<EfficientRater> loadRaters(String filename) throws IOException {
        ArrayList<EfficientRater> raters = new ArrayList<EfficientRater>();
        ArrayList<String> ids = new ArrayList<String>();
        Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord r : records) {
            String recRaterId = r.get("rater_id");
            if (!ids.contains(recRaterId)) {
                EfficientRater newRater = new EfficientRater(recRaterId);
                newRater.addRating(r.get("movie_id"), Double.parseDouble(r.get("rating")));
                raters.add(newRater);
                ids.add(recRaterId);
            } else {
                for (EfficientRater rater : raters) {
                    if (rater.getID().equals(recRaterId)) {
                        rater.addRating(r.get("movie_id"), Double.parseDouble(r.get("rating")));
                    }
                }
            }
        }
        return raters;
    }

    public int getNumOfMoviesByGenre(ArrayList<Movie> movies, String category) {
        int count = 0;
        for (Movie movie : movies) {
            String currGenres = movie.getGenres();
            if (currGenres.toLowerCase().contains(category.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public int getNumMoviesLongerThan(ArrayList<Movie> movies, int minutes) {
        int count = 0;
        for (Movie movie : movies) {
            int currLength = movie.getMinutes();
            if (minutes < currLength) {
                count++;
            }
        }
        return count;
    }

    public HashMap<Integer, ArrayList<String>> getMostMoviesDirected(ArrayList<Movie> movies) {
        // store number of movies directed by each director
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Movie movie : movies) {
            String directors = movie.getDirector();
            String[] directorsArr;
            if (directors.contains(",")) {
                directorsArr = directors.split(",");
            } else {
                directorsArr = new String[1];
                directorsArr[0] = directors;
            }
            for (String director : directorsArr) {
                if (!map.containsKey(director)) {
                    map.put(director, 1);
                } else {
                    map.replace(director, map.get(director) + 1);
                }
            }
        }
        // count number of movies by each director
        int largestNumOfMovies = countMap(map);
        ArrayList<String> directorNames = new ArrayList<String>();
        for (String director : map.keySet()) {
            if (map.get(director) == largestNumOfMovies) {
                directorNames.add(director);
            }
        }
        HashMap<Integer, ArrayList<String>> res = new HashMap<Integer, ArrayList<String>>();
        res.put(largestNumOfMovies, directorNames);
        return res;
    }

    public int getNumRatingsById(ArrayList<EfficientRater> raters, String id) {
        for (EfficientRater rater : raters) {
            String currId = rater.getID();
            if (id.equals(currId)) {
                return rater.numRatings();
            }
        }
        return -1;
    }

    public HashMap<Integer, ArrayList<EfficientRater>> getMostRatings(ArrayList<EfficientRater> raters) {
        int largest = 0;
        for (EfficientRater rater : raters) {
            int currNumRatings = rater.numRatings();
            if (currNumRatings > largest) {
                largest = currNumRatings;
            }
        }

        ArrayList<EfficientRater> ratersWithMost = new ArrayList<EfficientRater>();
        for (EfficientRater rater : raters) {
            if (rater.numRatings() == largest) {
                ratersWithMost.add(rater);
            }
        }

        HashMap<Integer, ArrayList<EfficientRater>> map = new HashMap<Integer, ArrayList<EfficientRater>>();
        map.put(largest, ratersWithMost);
        return map;
    }

    public int getNumRatingsOfMovie(ArrayList<EfficientRater> raters, String movieId) {
        int count = 0;
        for (EfficientRater rater : raters) {
            if (rater.hasRating(movieId)) {
                count++;
            }
        }
        return count;
    }

    public int getNumOfMovies(ArrayList<EfficientRater> raters) {
        ArrayList<String> movies = new ArrayList<String>();
        for (EfficientRater rater : raters) {
            ArrayList<String> list = rater.getItemsRated();
            for (String itm : list) {
                if (!movies.contains(itm)) {
                    movies.add(itm);
                }
            }
        }
        return movies.size();
    }

    // helpers
    private int countMap(HashMap<String, Integer> map) {
        int largest = 0;
        for (int total : map.values()) {
            if (total > largest) {
                largest = total;
            }
        }
        return largest;
    }

    // tests
    // private void testLoadMovies() {
    // try {
    // // String filename = "data/ratedmovies_short.csv";
    // String filename = "data/ratedmoviesfull.csv";
    // ArrayList<Movie> movies = loadMovies(filename);
    // System.out.println("There were " + movies.size() + " movies in the file.");
    // if (movies.size() <= 10) {
    // for (Movie movie : movies) {
    // System.out.println(movie.toString());
    // }
    // }

    // String genre = "comedy";
    // System.out.println(getNumOfMoviesByGenre(movies, genre) + " movies contained
    // the genre " + genre);

    // int minutes = 150;
    // System.out.println(getNumMoviesLongerThan(movies, minutes) + " movies longer
    // than " + minutes + " minutes");

    // HashMap<Integer, ArrayList<String>> directedMovies =
    // getMostMoviesDirected(movies);
    // for (Integer num : directedMovies.keySet()) {
    // System.out.print(num
    // + " was the largest number of movies directed by a single director. Those
    // directors were: ");
    // ArrayList<String> directors = directedMovies.get(num);
    // for (int i = 0; i < directors.size(); i++) {
    // if (i == directors.size() - 1) {
    // System.out.print(directors.get(i) + ".\n");
    // } else {
    // System.out.print(directors.get(i) + ", ");
    // }
    // }
    // }
    // } catch (IOException e) {
    // System.out.println("IOException: Invalid file Name");
    // }
    // }

    // private void testLoadRaters() {
    // // String filename = "data/ratings_short.csv";
    // String filename = "data/ratings.csv";
    // try {
    // ArrayList<EfficientRater> raters = loadRaters(filename);
    // System.out.println("total number of raters: " + raters.size());

    // String raterId = "193";
    // System.out.println(
    // "EfficientRater with id of \"" + raterId + "\" has \"" +
    // getNumRatingsById(raters,
    // raterId) + "\" ratings.");

    // HashMap<Integer, ArrayList<EfficientRater>> map = getMostRatings(raters);
    // for (Integer count : map.keySet()) {
    // System.out.print(count + " was the most ratings a single rater had and those
    // raters had ids of: ");
    // ArrayList<EfficientRater> list = map.get(count);
    // for (int i = 0; i < list.size(); i++) {
    // if (i == list.size() - 1) {
    // System.out.print(list.get(i).getID() + ".\n");
    // } else {
    // System.out.print(list.get(i).getID() + ", ");
    // }
    // }
    // }

    // String movieId = "1798709";
    // System.out.println("Movie with id of " + movieId + " was rated by " +
    // getNumRatingsOfMovie(raters, movieId)
    // + " raters.");

    // System.out.println("The total number of unique movies is " +
    // getNumOfMovies(raters));
    // } catch (IOException e) {
    // System.out.println(e);
    // }
    // }

    public static void main(String[] args) throws Exception {
        // FirstRatings tester = new FirstRatings();
        // tester.testLoadMovies();
        // tester.testLoadRaters();
    }
}