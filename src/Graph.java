import java.awt.Color;
   
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph {
    
    public XYSeries series;
    ChartFrame frame;
    
    public void graph() {
        series = new XYSeries("J(theta)");
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series);
        
        JFreeChart chart = ChartFactory.createXYLineChart("Gradient Descent", "J", "Steps", xyDataset, PlotOrientation.HORIZONTAL, true, true, true);
        chart.setBackgroundPaint(Color.YELLOW);
        frame = new ChartFrame("Gradient Descent", chart);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
    
    public void add(double x, double y ) {
        series.add(y, x);
        frame.repaint();
    }
}