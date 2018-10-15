
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
	final int  RIGHE = 13;
	final int colonne = 2;
	int[][] asseXY;
	/*int [][] asseXY ;*/
	int [] asseX;
	int [] asseY;

	public Grafico(String s,int [][] asseXY) {
		super(s);
		this.asseXY = asseXY;
		final ChartPanel chartPanel = createDemoPanel();
		this.add(chartPanel, BorderLayout.CENTER);
		JPanel control = new JPanel();
		/*control.add(new JButton(new AbstractAction("Add") {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < N; i++) {
                    added.add(rand.nextDouble(), rand.nextDouble());
                }
            }
        }));*/
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
		int maxX = 0;
		for (int i = 0; i < asseXY.length; i++){ 
			if (asseX[i] >= maxX)
				maxX = asseX[i];
		}
		domain.setRange(0.00, maxX + 0.5);
		domain.setTickUnit(new NumberTickUnit(0.5));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
		int maxY = 0;
		for (int i = 0; i < asseY.length; i++){
			if (asseY[i] >= maxY)
				maxY = asseY[i];
		}
		range.setRange(0.0, maxY + 0.5);
		range.setTickUnit(new NumberTickUnit(1));
		return new ChartPanel(jfreechart);
	}

	private XYDataset createSampleData() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Tuple");
		if (asseX.length == asseY.length){
			for (int i = 0; i < asseX.length; i++)
				series.add(asseX[i], asseY[i]);
		}
		//deve prendere i valori che gli passa serveroneclient
		/*
        	series.add(0.17, 1);
            series.add(0.24,2);
            series.add(0.24, 3);
            series.add(0.24,2 );
            series.add(0.48,3 );
            series.add(0.75,2 );
            series.add(1,3);
            series.add(1.17, 1);
            series.add(1.33,3 );
            series.add(1.33, 3);
            series.add(1.33, 3);
            series.add(1.98, 3);
            series.add(1.98, 3);
            series.add(2.24, 2);

		 */

		xySeriesCollection.addSeries(series);

		return xySeriesCollection;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				int [] asseX= {1, 2, 3, 4,6};
				int [] asseY = {2, 3, 5, 1,6};
				ScatterAdd demo = new ScatterAdd(title, asseX, asseY);
				demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				demo.pack();
				demo.setLocationRelativeTo(null);
				demo.setVisible(true);
			}
		});
	}
