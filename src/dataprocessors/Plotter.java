package dataprocessors;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import datatypes.Data;
import datatypes.DoubleData;

public class Plotter extends DataProcessor {

    private XYSeries series = new XYSeries("series");;

    private int xValue = 0;
    private int maxX;

    public Plotter(String title, int minX, int maxX) {
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, 
                null, // X-Axis Label
                null, // Y-Axis Label
                dataset, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false 
        );

        this.maxX = maxX;

        //create frame
        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected Data process(Data input) {    
        //TODO handle ClassCastException
        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;

        for(double d : data) {
            series.add(xValue, d, false);
            xValue++;
        }

        int remove = series.getItemCount() - this.maxX;
        if(remove > 0) {
            series.delete(0, remove);
        } 
        series.fireSeriesChanged();

        return null;
    }    
}
