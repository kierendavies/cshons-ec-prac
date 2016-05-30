import java.util.*;

class Chromosome implements Comparable<Chromosome> {
    private Random random = new Random();

    /**
     * The list of cities, which are the genes of this chromosome.
     */
    protected int[] cityList;
    protected int length;

    /**
     * The cost of following the cityList order of this chromosome.
     */
    protected double cost;

    /**
     * @param cities The order that this chromosome would visit the cities.
     */
    Chromosome(City[] cities) {
        length = cities.length;
        cityList = new int[length];
        //cities are visited based on the order of an integer representation [o,n] of each of the n cities.
        for (int x = 0; x < cities.length; x++) {
            cityList[x] = x;
        }

        //shuffle the order so we have a random initial order
        for (int y = 0; y < length; y++) {
            int temp = cityList[y];
            int randomNum = random.nextInt(length);
            cityList[y] = cityList[randomNum];
            cityList[randomNum] = temp;
        }

        calculateCost(cities);
    }

    Chromosome(Chromosome original) {
        cityList = Arrays.copyOf(original.cityList, original.length);
        length = original.length;
        cost = original.cost;
    }

    /**
     * Calculate the cost of the specified list of cities.
     *
     * @param cities A list of cities.
     */
    void calculateCost(City[] cities) {
        cost = 0;
        for (int i = 0; i < length - 1; i++) {
            double dist = cities[cityList[i]].proximity(cities[cityList[i + 1]]);
            cost += dist;
        }

        cost += cities[cityList[0]].proximity(cities[cityList[length - 1]]); //Adding return home
    }

    /**
     * Get the cost for this chromosome. This is the amount of distance that
     * must be traveled.
     */
    double getCost() {
        return cost;
    }

    /**
     * @param i The city you want.
     * @return The ith city.
     */
    int getCity(int i) {
        return cityList[i];
    }

    /**
     * Set the order of cities that this chromosome would visit.
     *
     * @param list A list of cities.
     */
    void setCities(int[] list) {
        for (int i = 0; i < length; i++) {
            cityList[i] = list[i];
        }
    }

    /**
     * Set the index'th city in the city list.
     *
     * @param index The city index to change
     * @param value The city number to place into the index.
     */
    void setCity(int index, int value) {
        cityList[index] = value;
    }

    @Override
    public int compareTo(Chromosome that) {
        return Double.compare(this.getCost(), that.getCost());
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        for (int i : cityList) {
            sj.add(Integer.toString(i));
        }
        return sj.toString();
    }

    public void randomise() {
        for (int y = 0; y < length; y++) {
            int temp = cityList[y];
            int randomNum = random.nextInt(length);
            cityList[y] = cityList[randomNum];
            cityList[randomNum] = temp;
        }
    }

    public Chromosome swapPair() {
        Chromosome child = new Chromosome(this);
        Pair<Integer, Integer> pair = Util.randomDistinctPair(length);
        int i = pair.first;
        int j = pair.second;
        child.cityList[i] = cityList[j];
        child.cityList[j] = cityList[i];

        child.validate();
        return child;
    }

    public Chromosome pmx(Chromosome that) {
        Chromosome child = new Chromosome(that);

        Pair<Integer, Integer> pair = Util.randomDistinctPair(length + 1);
        int p1 = pair.first;
        int p2 = pair.second;

        BitSet used = new BitSet(length);
        for (int i = p1; i < p2; i++) {
            child.cityList[i] = this.cityList[i];
            used.set(this.cityList[i]);
        }

        for (int i = p1; i < p2; i++) {
            if (!used.get(that.cityList[i])) {
                int mapThat = that.cityList[i];
                int mapThis;
                int index = i;
                do {
                    mapThis = this.cityList[index];
                    index = Util.linearSearch(that.cityList, mapThis);
                } while (p1 <= index && index < p2);
                child.cityList[index] = mapThat;
            }
        }

        child.validate();
        return child;
    }

    public Chromosome ox1(Chromosome that) {
        Chromosome child = new Chromosome(this);

        Pair<Integer, Integer> pair = Util.randomDistinctPair(length + 1);
        int p1 = pair.first;
        int p2 = pair.second;

        BitSet used = new BitSet(length);
        for (int i = p1; i < p2; i++) {
            child.cityList[i] = this.cityList[i];
            used.set(this.cityList[i]);
        }

        Queue<Integer> unused = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            if (!used.get(that.cityList[i])) {
                unused.add(that.cityList[i]);
            }
        }

        for (int i = 0; i < p1; i++) {
            child.cityList[i] = unused.remove();
        }
        for (int i = p2; i < length; i++) {
            child.cityList[i] = unused.remove();
        }

        child.validate();
        return child;
    }

    private void validate() throws AssertionError {
        BitSet used = new BitSet(length);
        for (int i : cityList) {
            used.set(i);
        }
        assert used.nextClearBit(0) == length;
    }
}
