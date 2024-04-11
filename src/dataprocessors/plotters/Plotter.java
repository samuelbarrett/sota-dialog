package dataprocessors.plotters;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import dataprocessors.DataProcessor;
import datatypes.Data;
import datatypes.DoubleData;
import eventsystem.EventGenerator;

public class Plotter extends DataProcessor implements Runnable {

    private XYSeries series = new XYSeries("series");;

    private int xValue = 0;
    private double maxX;

    public Plotter(String title, double minX, double maxX, double minY, double maxY) {
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

        XYPlot plot = chart.getXYPlot();
        plot.getRangeAxis().setRange(minY, maxY);

        //create frame
        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {    
        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;

        for(double d : data) {
            series.add(xValue, d, false);
            xValue++;
        }

        int remove = (int)(series.getItemCount() - this.maxX);
        if(remove > 0) {
            series.delete(0, remove);
        } 
        series.fireSeriesChanged();


        return null;
    }    


    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }   
}
