package mining;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che permette la creazione di un grafico sui cluster trovati alle varie distanze
 */
public class ClusterPlot implements Serializable{

    private XYSeries series;
    private XYSeriesCollection data;
    private JFreeChart chart;
    private ChartPanel panel;
    private int width;
    private int height;
    private Map<Double,Integer> values;

    /**
     * Inizializza il grafico e i oggetti che lo compongono
     * @param chartname nome del grafico
     * @param xaxis   nome sull'asse delle ascisse
     * @param yaxis   nome sull'asse delle ordinate
     * @param width   larghezza massima del grafico
     * @param height  altezza massima del grafico
     */
    public ClusterPlot(String chartname, String xaxis, String yaxis, int width, int height){
        this.width = width;
        this.height = height;

        values = new HashMap<>();

        series = new XYSeries("#Cluster");

        data = new XYSeriesCollection(series);

        chart = ChartFactory.createScatterPlot( chartname, xaxis, yaxis, data, PlotOrientation.VERTICAL, false, true, false);
        
        XYPlot xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setRangeCrosshairVisible(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setTickUnit(new NumberTickUnit(1));
        
        panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(width,height));
    }

    /**
     * Aggiunde i valori al grafico
     * @param x valore sulle ascisse
     * @param y valore sulle ordinate
     */
    public void AddData(Double x, Integer y){
        if(!values.containsKey(x))
            values.put(x,++y);
        series.add(x,values.get(x));
    }

    /**
     * Invia il grafico al client come immagine attraverso un socket
     * @param s socket per stabilire connessione con il client.
     */
    public void WritePlot(Socket s){
        try {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            BufferedImage img = chart.createBufferedImage(width,height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img,"PNG",baos);
            byte[] buffer = baos.toByteArray();
            baos.close();
            out.writeObject(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
