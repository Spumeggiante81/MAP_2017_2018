package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * @see http://stackoverflow.com/questions/7231824
 * @see http://stackoverflow.com/questions/7205742
 * @see http://stackoverflow.com/questions/7208657
 * @see http://stackoverflow.com/questions/7071057
 */
public class Grafico extends JFrame {
	private static final String title = "Kmeans";
	
	double[][] asseXY;
	
	public Grafico(double[][] matrice) {

		double [][] asseXY = matrice;
		
		
		Grafico demo = new Grafico(title, asseXY);
		demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		demo.pack();
		demo.setLocationRelativeTo(null);
		demo.setVisible(true);
	}

	
	public Grafico(String s,double [][] asseXY) {
		super(s);
		this.asseXY = asseXY;
		final ChartPanel chartPanel = createDemoPanel();
		this.add(chartPanel, BorderLayout.CENTER);
		JPanel control = new JPanel();
		
		this.add(control, BorderLayout.SOUTH);
	}

	private ChartPanel createDemoPanel() {
		JFreeChart jfreechart = ChartFactory.createScatterPlot(
				title, "Distanze", "Cluster", createSampleData(),
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		XYItemRenderer renderer = xyPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.blue);
		NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
		domain.setRange(0.00, 3.5);
		double maxX = 0;
		for (int i = 0; i < asseXY.length; i++){ 
			if (asseXY[i][0] >= maxX)
				maxX = asseXY[i][0];
		}
		domain.setRange(0.00, maxX + 0.5);
		domain.setTickUnit(new NumberTickUnit(0.5));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
		double maxY = 0;
		for (int i = 0; i < asseXY.length; i++){
			if (asseXY[i][1] >= maxY)
				maxY = asseXY[i][1];
		}
		range.setRange(0.0, maxY + 0.5);
		range.setTickUnit(new NumberTickUnit(1));
		return new ChartPanel(jfreechart);
	}

	private XYDataset createSampleData() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Tuple");
		
		
		for (int i = 0; i < asseXY.length; i++)
		{
			series.add(asseXY[i][0], asseXY[i][1]);
		}

		xySeriesCollection.addSeries(series);

		return xySeriesCollection;
	}
}			
