package dataprocessors.plotters;

import java.util.HashMap;

import javax.swing.JFrame;
import java.awt.FlowLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import dataprocessors.DataProcessor;
import datatypes.Data;
import datatypes.DoubleData;
import eventsystem.EventGenerator;

public class MultiPlotter extends DataProcessor {

    private class SinglePlot {
        public XYSeries series;
        public int xValue = 0;

        private SinglePlot(XYSeries series) {
            this.series = series;
        }
    }

    private HashMap<EventGenerator, SinglePlot> senderToPlot = new HashMap<>();
    private JFrame frame;

    public MultiPlotter() {
        this.frame = new JFrame();

        frame.setLayout(new FlowLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {    
        SinglePlot plot = senderToPlot.computeIfAbsent(sender, this::createPlot);

        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;

        for(double d : data) {
            plot.series.add(plot.xValue, d, false);
            plot.xValue++;
        }
        plot.series.fireSeriesChanged();

        return null;
    }    

    private SinglePlot createPlot(EventGenerator sender) {
        System.out.println("here");
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                sender.getClass().getSimpleName(), 
                null, 
                null, 
                dataset, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false 
        );
        ChartPanel panel = new ChartPanel(chart);
        frame.add(panel);

        frame.revalidate();
        frame.repaint();
        frame.pack();
    
        return new SinglePlot(series);
    }

}
