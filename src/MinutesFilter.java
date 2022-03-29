public class MinutesFilter implements Filter {
    private int min;
    private int max;

    public MinutesFilter(int minimum, int maximum) {
        min = minimum;
        max = maximum;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= min && MovieDatabase.getMinutes(id) <= max;
    }
}
