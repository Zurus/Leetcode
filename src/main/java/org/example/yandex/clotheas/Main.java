package org.example.yandex.clotheas;

//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

import static org.example.utils.ArrayUtils.parse;

/**
 * Задача: Глеб хочет выбрать одну майку и одни штаны так, чтобы разница их цветов была минимальна.
 * Входные данные:
 * - N (1 <= N <= 100000) — количество маек.
 * - N целых чисел в возрастающем порядке (уникальные) — цвета маек.
 * - M (1 <= M <= 100000) — количество штанов.
 * - M целых чисел в возрастающем порядке (уникальные) — цвета штанов.
 * Цвета: от 1 до 10 000 000.
 * Выход: два числа — цвет майки и цвет штанов, дающие минимальную разницу.
 * Если таких пар несколько, вывести любую.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int top = Integer.parseInt(reader.readLine());
        int[] colorTop = parse(reader.readLine());
        Arrays.sort(colorTop);

        int bot = Integer.parseInt(reader.readLine());
        int[] colorBot = parse(reader.readLine());
        Arrays.sort(colorBot);

        int i = 0, j = 0;
        int temp;
        int minDiff = Integer.MAX_VALUE;
        int minI = 0, minJ = 0;
        while (i < top && j < bot) {
            temp = Math.abs(colorTop[i] - colorBot[j]);
            if (temp < minDiff) {
                minDiff = temp;
                minI = i;
                minJ = j;
            }

            if (temp == 0) {
                break;
            } else if (colorTop[i] < colorBot[j]) {
                i++;
            } else {
                j++;
            }
        }

        writer.write(colorTop[minI] + " " + colorBot[minJ]);
        reader.close();
        writer.close();

        // Показать визуализацию
//        showVisualization(colorTop, colorBot, minI, minJ, minDiff);
    }

    /**
     * Отображает два графика в Swing-окне:
     * 1. Положение всех точек на числовой оси (майки и штаны) с выделением найденной пары.
     * 2. График "галочки" – зависимость разности от j для фиксированного i = minI.
     */
//    public static void showVisualization(int[] tops, int[] bots,
//                                         int bestTopIdx, int bestBotIdx, int bestDiff) {
//        // Наборы данных для первого графика
//        XYSeries topSeries = new XYSeries("Майки (top)");
//        for (int i = 0; i < tops.length; i++) {
//            topSeries.add(tops[i], 0); // Y = 0
//        }
//        XYSeries botSeries = new XYSeries("Штаны (bottom)");
//        for (int j = 0; j < bots.length; j++) {
//            botSeries.add(bots[j], 1); // Y = 1
//        }
//
//        // Выделенная пара (соединим линией)
//        XYSeries pairSeries = new XYSeries("Выбранная пара (разница)");
//        pairSeries.add(tops[bestTopIdx], 0);
//        pairSeries.add(tops[bestTopIdx], 1); // вертикальная линия – разница
//        pairSeries.add(bots[bestBotIdx], 1); // горизонталь до штанов
//        // Можно добавить точку штанов на уровне 1
//
//        XYSeriesCollection collection1 = new XYSeriesCollection();
//        collection1.addSeries(topSeries);
//        collection1.addSeries(botSeries);
//        collection1.addSeries(pairSeries);
//
//        JFreeChart chart1 = ChartFactory.createScatterPlot(
//                "Положение цветов и выбранная пара",
//                "Цвет", "",
//                collection1,
//                PlotOrientation.VERTICAL, false, true, false);
//
//        XYPlot plot1 = chart1.getXYPlot();
//        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
//        renderer1.setSeriesLinesVisible(0, false);
//        renderer1.setSeriesShapesVisible(0, true);
//        renderer1.setSeriesLinesVisible(1, false);
//        renderer1.setSeriesShapesVisible(1, true);
//        renderer1.setSeriesLinesVisible(2, true);
//        renderer1.setSeriesShapesVisible(2, false);
//        renderer1.setSeriesPaint(2, Color.RED);
//        renderer1.setSeriesStroke(2, new BasicStroke(2.0f));
//        plot1.setRenderer(renderer1);
//
//        // Второй график: галочка для фиксированного i = bestTopIdx
//        XYSeries diffSeries = new XYSeries("|top[i] - bot[j]| при i=" + tops[bestTopIdx]);
//        int fixedTop = tops[bestTopIdx];
//        for (int j = 0; j < bots.length; j++) {
//            diffSeries.add(j, Math.abs(fixedTop - bots[j]));
//        }
//        // Отметим точку найденного минимума
//        XYSeries minPoint = new XYSeries("Минимум (j=" + bestBotIdx + ")");
//        minPoint.add(bestBotIdx, Math.abs(fixedTop - bots[bestBotIdx]));
//
//        XYSeriesCollection collection2 = new XYSeriesCollection();
//        collection2.addSeries(diffSeries);
//        collection2.addSeries(minPoint);
//
//        JFreeChart chart2 = ChartFactory.createXYLineChart(
//                "График 'галочка' разности",
//                "Индекс j", "Разность",
//                collection2,
//                PlotOrientation.VERTICAL, false, true, false);
//
//        XYPlot plot2 = chart2.getXYPlot();
//        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
//        renderer2.setSeriesShapesVisible(0, false);
//        renderer2.setSeriesLinesVisible(0, true);
//        renderer2.setSeriesShapesVisible(1, true);
//        renderer2.setSeriesLinesVisible(1, false);
//        renderer2.setSeriesPaint(1, Color.RED);
//        plot2.setRenderer(renderer2);
//
//        // Размещаем оба графика в одном окне вертикально
//        JPanel panel = new JPanel(new GridLayout(2, 1));
//        panel.add(new ChartPanel(chart1));
//        panel.add(new ChartPanel(chart2));
//
//        JFrame frame = new JFrame("Визуализация задачи о майках и штанах");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(panel);
//        frame.pack();
//        frame.setSize(800, 600);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
