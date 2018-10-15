import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class ScatterAdd extends JFrame {

	 private double[] asseX;
	private double[] asseY;

	
	 
	private static final String title = "Kmeans";
   
    public ScatterAdd(String s) {
        super(s);
       final ChartPanel chartPanel = createDemoPanel();
        this.add(chartPanel, BorderLayout.CENTER);
        JPanel control = new JPanel();
/*        control.add(new JButton(new AbstractAction("Add") {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < N; i++) {
                    added.add(rand.nextDouble(), rand.nextDouble());
                }
            }
        }));*/
        this.add(control, BorderLayout.SOUTH);
    }


	public ScatterAdd(String s,double [] asseX, double [] asseY) {
		super(s);
		this.asseX = asseX;
		this.asseY = asseY;
		final ChartPanel chartPanel = createDemoPanel();
		this.add(chartPanel, BorderLayout.CENTER);
		JPanel control = new JPanel();
		/*control.add(new JButton(new AbstractAction("Add") {

            @Override
            public void actionPerformed(ActionEvent e) {
@ -42,33 +51,48 @@ public class ScatterAdd extends JFrame {
                }
            }
        }));*/
        this.add(control, BorderLayout.SOUTH);
    }

   
    private XYDataset createSampleData1() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Tuple");
       
       
		Component control = null;
		this.add(control, BorderLayout.SOUTH);
		return xySeriesCollection;
	}

	private ChartPanel createDemoPanel() {
		JFreeChart jfreechart = ChartFactory.createScatterPlot(
				title, "Distanze", "Cluster", createSampleData1(),
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		XYItemRenderer renderer = xyPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.blue);
		NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
		domain.setRange(0.00, 3.5);
		double maxX = 0;
		for (int i = 0; i < asseX.length; i++){ 
			if (asseX[i] >= maxX)
				maxX = asseX[i];
		}
		domain.setRange(0.00, maxX + 0.5);
		domain.setTickUnit(new NumberTickUnit(0.5));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
		double maxY = 0;
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
            series.add(1.98, 3);
            series.add(1.98, 3);
            series.add(2.24, 2);
            
       
        xySeriesCollection.addSeries(series);
     
        return xySeriesCollection;
   */ }

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				double [] asseX= {1, 2, 3, 4,8};
				double [] asseY = {2, 3, 5, 1,6};
				ScatterAdd demo = new ScatterAdd(title,asseX,asseY);
				demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				demo.pack();
				demo.setLocationRelativeTo(null);
				demo.setVisible(true);
			}
		});
	}
}