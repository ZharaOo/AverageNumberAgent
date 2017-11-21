
public class AverageNumber {
    private Integer sum;
    private Integer counter = 1;

    public AverageNumber(Integer value) {
        this.sum = value;
    }

    private AverageNumber(Integer value, Integer counter) {
        this.sum = value;
        this.counter = counter;
    }

    public void add(Integer value) {
        sum += value;
        counter++;
    }

    public double calculate() {
        return (double)sum / (double)counter;
    }

    public String toString() {
        return sum + " " + counter;
    }

    public static AverageNumber parseString(String str) {
        String[] arr = str.split(" ");
        return new AverageNumber(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
    }
}
