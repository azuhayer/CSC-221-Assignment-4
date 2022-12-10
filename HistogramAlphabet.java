import javafx.scene.canvas.GraphicsContext;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Random;

public class HistogramAlphabet
{
    Map<Character, Integer> frequency = new HashMap<Character, Integer>();
    Map<Character, Double> probability = new HashMap<Character, Double>();

    //Constructor with String parameter
    HistogramAlphabet(String text)
    {
        String w = text.replaceAll("[^a-zA-Z]", "");
        for (int i = 0; i < w.length(); i++)
        {
            Character key = w.charAt(i);
            incrementFrequency(frequency, key);
        }
    }

    //Constructor with Map parameter
    HistogramAlphabet(Map<Character, Integer> n)
    {
        frequency.putAll(n);
    }

    //Get functions
    public Map<Character, Integer> getFrequency()
    {
        return frequency;
    }

    public Integer getCumulativeFrequency() {
        return frequency.values().stream().reduce(0, Integer::sum);
    }

    public Map<Character, Double> getProbability()
    {
        double inverseCumulativeFrequency = 1.0 / getCumulativeFrequency();
        for (Character Key : frequency.keySet())
        {
            probability.put(Key, (double) frequency.get(Key) * inverseCumulativeFrequency);
        }
        return probability.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public Double getSumOfProbability() {
        return probability.values().stream().reduce(0.0, Double::sum);
    }

    //Sort Functions
    public Map<Character, Integer> sortUpFrequency()
    {
        return frequency.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->
                e2, LinkedHashMap::new));
    }

    public Map<Character, Integer> sortDownFrequency()
    {
        return frequency.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    //Increments Frequency Function
    public static <L> void incrementFrequency(Map<L, Integer> m, L Key)
    {
        m.putIfAbsent(Key, 0);
        m.put(Key, m.get(Key) + 1);
    }

    //String Function
    @Override
    public String toString()
    {
        String output = "The Frequency of Characters is:\n";
        for (Character K : frequency.keySet()) {
            output = output + K + ": " + frequency.get(K) + "\n";
        }
        return output;
    }


    /*****BEGINNING OF MyPieChart INNER CLASS*****/
    public class MyPieChart {
        Map<Character, Slice> slice = new HashMap<Character, Slice>();
        int n;
        MyPoint center;
        double radius;
        double rotateAngle;

        //Constructor with parameters
        MyPieChart(int n, MyPoint center, double radius, double rotateAngle) {
            this.n = n;
            this.center = center;
            this.radius = radius;
            this.rotateAngle = rotateAngle;
            probability = getProbability();
            slice = getMyPieChart();
        }

        //Get Function
        public Map<Character, Slice> getMyPieChart() {
            MyColor[] colors = MyColor.values();
            Random rand = new Random();
            int colorLength = colors.length;
            double startAngle = rotateAngle;

            for (Character Key : probability.keySet()) {
                double angle = 360 * probability.get(Key);
                slice.put(Key, new Slice(center, radius, startAngle, angle, colors[rand.nextInt(colorLength)]));
                startAngle = startAngle + angle;
            }
            return slice;
        }

        //Draw Function
        public void draw(GraphicsContext gc)
        {
            Map<Character, Integer> sortedFrequency = sortDownFrequency();
            Object Key = 0;
            int total = 0;
            int sum = 0;
            int angleSum = 0;

            for (Character i : sortedFrequency.keySet())
            {
                total = total + sortedFrequency.get(i);
            }

            Slice other = new Slice(center, radius, 0, 360, MyColor.GRAY);
            other.draw(gc);

            for (int i = 0; i < n; i++)
            {
                Key = sortedFrequency.keySet().toArray()[i];
                slice.get(Key).draw(gc);
                String text = (Character) Key + ": " + sortedFrequency.get(Key);
                System.out.println(text);

                double startAngle = slice.get(Key).getStartAngle();
                double angle = slice.get(Key).getAngle();
                double textAngle = Math.toRadians(startAngle + angle / 2);

                if (startAngle % 360 >= 90 && startAngle % 360 <= 270)
                {
                    double x1 = center.getX() + (radius + radius / 3.5) * Math.cos(textAngle);
                    double y1 = center.getY() - (radius + radius / 10) * Math.sin(textAngle);

                    gc.setFill(MyColor.BLACK.getColor());
                    gc.fillText(text, x1, y1);
                }

                else
                {
                    double x2 = center.getX() + (radius + radius / 15) * Math.cos(textAngle);
                    double y2 = center.getY() - (radius + radius / 10) * Math.sin(textAngle);

                    gc.setFill(MyColor.BLACK.getColor());
                    gc.fillText(text, x2, y2);
                }

                angleSum = (int) (angleSum + slice.get(Key).getAngle());
                sum = sum + sortedFrequency.get(Key);
            }

            if (sum < total)
            {
                double startAngle = slice.get(Key).getStartAngle() + slice.get(Key).getAngle();
                double angle = 360 - angleSum;
                double textAngle = Math.toRadians(startAngle + angle / 2);

                if (startAngle % 360 >= 90 && startAngle % 360 <= 270)
                {
                    double x3 = center.getX() + (radius + radius / 3.5) * Math.cos(textAngle);
                    double y3 = center.getY() - (radius + radius / 10) * Math.sin(textAngle);

                    gc.fillText("All Other Letters: " + (total - sum), x3, y3);
                    System.out.println("All Other Letters: " + (total - sum));
                }

                else
                {
                    double x4 = center.getX() + (radius + radius / 15) * Math.cos(textAngle);
                    double y4 = center.getY() - (radius + radius / 10) * Math.sin(textAngle);

                    gc.fillText("All Other Letters: " + (total - sum), x4, y4);
                    System.out.println("All Other Letters: " + (total - sum));
                }
            }
        }
    }
}
