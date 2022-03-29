public class App {
    public static void main(String[] args) throws Exception {
        String movieFile = "ratedmoviesfull.csv";
        String ratingsFile = "ratings.csv";

        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingsFile);

        RecommendationRunner runner = new RecommendationRunner();
        runner.printRecommendationsFor("1");
        // runner.tester("800");
    }
}
