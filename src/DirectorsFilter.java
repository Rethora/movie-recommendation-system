public class DirectorsFilter implements Filter {
    private String[] myDirectors;

    public DirectorsFilter(String directors) {
        if (directors.contains(",")) {
            myDirectors = directors.split(",".trim());
        } else {
            myDirectors = new String[1];
            myDirectors[0] = directors;
        }
    }

    @Override
    public boolean satisfies(String id) {
        String directors = MovieDatabase.getDirector(id);
        for (String director : myDirectors) {
            if (directors.contains(director)) {
                return true;
            }
        }
        return false;
    }
}
