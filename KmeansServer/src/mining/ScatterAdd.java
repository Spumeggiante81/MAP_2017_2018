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
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 * @see http://stackoverflow.com/questions/7231824
 * @see http://stackoverflow.com/questions/7205742
 * @see http://stackoverflow.com/questions/7208657
 * @see http://stackoverflow.com/questions/7071057
 */
public class ScatterAdd extends JFrame {

	private static final String title = "Kmeans";
	/*int [] asseX;
	int [] asseY;*/
	double asseXY[][] ;
	double numMaxY=0,numMaxX=0;

	public ScatterAdd(String s,Class<mining.asseXY[][]> class1 ) {
		super(s);
		this.asseXY = class1;
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
		
		for (int i = 0; i < asseXY.length; i++){ 
			if (asseXY[i][0] >= numMaxX)
				numMaxX = asseXY[i][0];
		}
		domain.setRange(0.00, numMaxX + 0.5);
		domain.setTickUnit(new NumberTickUnit(0.5));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
		int maxY = 0;
		for(int i=0;i<=asseXY.length-1;i++)
			if (asseXY[i][1]>numMaxY)
				numMaxY =   asseXY[i][1];
		range.setRange(0.0, numMaxY + 0.5);
		range.setTickUnit(new NumberTickUnit(1));
		return new ChartPanel(jfreechart);
	}

	private XYDataset createSampleData() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Tuple");
		for(int i=0; i<=asseXY.length-1;i++)
			for (int j = 0; j < asseXY[i].length-1; j++)
				series.add(asseXY[i][j]);
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

				double [][] asseXY= {{1,0.17},{2,0.24},{5,2.5}};
				//int [] asseY = {2, 3, 5, 1,6};
				ScatterAdd demo = new ScatterAdd(title, asseXY[][]);
				demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				demo.pack();
				demo.setLocationRelativeTo(null);
				demo.setVisible(true);
			}
		});
	}
}