import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Window {
    private JFrame frame;
    private Random rand = new Random();
    private int delay = 250; // delay in ms
    private byte barWidth = -2; // bar width
    private DefaultCategoryDataset dataset;
    
    private int[] counts = new int[9]; // init counts array with length 9

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window window = new Window();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Window() {
        initialize();
    }

    private void initialize() {
        FlatMacLightLaf.setup();

        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setTitle("Java Random Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize dataset and chart
        dataset = new DefaultCategoryDataset();
        JFreeChart barChart = createChart(dataset);

        // Create a ChartPanel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.getContentPane().add(chartPanel);

        frame.pack();
        
        startRandomness();
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
            "Analysis of Randomness",
            "Numbers",
            "Amount",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(barWidth); // Adjust the bar size if needed

        return barChart;
    }

    private void startRandomness() {
        Timer timer = new Timer(delay, new ActionListener() { // Java swing timer instead of thread sleep for the swing window to keep updating
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDataset();
            }
        });
        timer.setRepeats(true); // Repeat set to true
        timer.start(); // Start the timer
    }

    private void updateDataset() {
    	for (int i = 1; i <= counts.length; i++) {
    		int randomNum = rand.nextInt(9) + 1;
    		
    		counts[randomNum-1]++;
    		
        	dataset.addValue(counts[i-1], Integer.toString(i), Integer.toString(i));
    	}
    }
}