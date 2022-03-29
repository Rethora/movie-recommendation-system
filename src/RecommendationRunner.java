import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RecommendationRunner implements Recommender {

    private static int getRandom() {
        return new Random().nextInt(MovieDatabase.size());
    }

    private static boolean arrContains(ArrayList<Integer> arr, int in) {
        return arr.contains(in);
    }

    public ArrayList<String> getItemsToRate() {

        int howMany = 25;

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < howMany; i++) {
            int randomIdx = getRandom();
            while (arrContains(list, randomIdx)) {
                randomIdx = getRandom();
            }
            list.add(randomIdx);
        }

        Collections.sort(list);

        ArrayList<String> res = new ArrayList<String>(howMany);
        // String genres = "Comedy, Action, Drama, Adventure, Mystery, Crime, Thriller,
        // Fantasy";
        // AllFilters filters = new AllFilters();
        // filters.addFilter(new GenreFilter(genres));
        // filters.addFilter(new YearAfterFilter(1980));
        int movieCount = 0;
        int idxCount = 0;
        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            if (idxCount == howMany) {
                break;
            }
            if (movieCount == list.get(idxCount)) {
                res.add(movieId);
                idxCount++;
            }
            movieCount++;
        }
        return res;
    }

    public void printRecommendationsFor(String webRaterID) {
        System.out.println(
                "<style>table{margin: 0 auto}#head{text-align: center}th{margin: 0 5px}.message{text-align: center}</style>");
        try {
            FourthRatings fr = new FourthRatings();
            ArrayList<Rating> recommendations = fr.getSimilarRatings(webRaterID, 10, 2);
            if (recommendations.size() == 0) {
                System.out.println("<p class='message'>No movies to recommend :(</p>");
            } else {

                System.out.print("<h1 id='head'>Movies we think you'll like...</h1>");
                System.out.print("<br>" + "<table>" + "<tr>" + "<th>Title</th>" + "<th>Year</th>" + "<th>Minutes</th>"
                        + "<th>Genres</th>" + "<th>Directors</th>" + "</tr>");
                for (int i = 0; i < recommendations.size(); i++) {
                    if (i < 10) {
                        String movieId = recommendations.get(i).getItem();
                        String title = MovieDatabase.getTitle(movieId);
                        int year = MovieDatabase.getYear(movieId);
                        int minutes = MovieDatabase.getMinutes(movieId);
                        String genres = MovieDatabase.getGenres(movieId);
                        String directors = MovieDatabase.getDirector(movieId);
                        System.out.print("<tr>" + "<td>" + title + "</td>" + "<td>" + year + "</td>" + "<td>" + minutes
                                + "</td>" + "<td>" + genres + "</td>" + "<td>" + directors + "</td>" + "</tr>");
                    }
                }
                System.out.print("</table>");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                    "<p class='message'>Not enough data to make a recommendation, try again with more selections...</p>");
        }
    }

    public void tester(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> recommendations = fr.getSimilarRatings(webRaterID, 50, 3);
        for (Rating r : recommendations) {
            System.out.println(r);
        }
    }
}
