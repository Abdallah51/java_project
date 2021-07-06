/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mywazzuf;

/**
 *
 * @author dell
 */
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.spark.sql.SparkSession;
import smile.data.DataFrame;
import smile.data.Tuple;
import org.apache.commons.csv.CSVFormat;
import smile.io.Read;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;



/**
 *
 * @author dell
 */
public class DAO {
     public static void main(String[] args) {
        // Reading from city.csv and creating list of cities
 
    SparkSession spark = SparkSession
                .builder()
                .appName("SparkSample")
                .master("local[*]")
                .getOrCreate();
                DAO Dobj =new DAO();
                DataFrame df;
         try {
             df = Dobj.readCSV("C:/Users/dell/Downloads/Wuzzuf_Jobs.csv");
             List<POJO> df3 = Dobj.getWazafList(df);
             Map<String, List<POJO>> popular_companies = df3.stream().collect(Collectors.groupingBy(p -> p.getCompany()));
             Dobj.drawPieChart(popular_companies);
             Map<String, List<POJO>> popular_jobs = df3.stream().collect(Collectors.groupingBy(p -> p.getTitle()));
             Dobj.drawPieChart(popular_jobs);
             Map<String, List<POJO>> popular_areas = df3.stream().collect(Collectors.groupingBy(p -> p.getLocation()));
             Dobj.drawPieChart(popular_areas);
             System.out.println("Skils:-");

//        System.out.println(df3.size());
            List<String> Allskils = new ArrayList<>();

        for (POJO p : df3) {
//                        System.out.println(p.getTitle());

            String[] skils = p.getSkills();
            for (int i = 0; i < skils.length; i++) {
                Allskils.add(skils[i]);
//                System.out.println(p.getTitle() + " : " + skils[i]);
            }
//             System.out.println(p.getTitle() + " : " + skils);

        }
             System.out.println(Allskils);
             
             Map<String, Integer> counts = new HashMap<String, Integer>(); 
 
        for (String str : Allskils) { 
            if (counts.containsKey(str)) { 
                counts.put(str, counts.get(str) + 1); 
            } else { 
                counts.put(str, 1); 
            } 
        } 
 
            for (Map.Entry<String, Integer> entry : counts.entrySet()) { 
//                System.out.println(entry.getKey() + " = " + entry.getValue()); 
            } 
          counts.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).distinct().forEach(System.out::println);

         } catch (IOException ex) {
             Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
         }
                

     }
         //Read file
//         File file = new File("C:\\Users\\dell\\Downloads\\Wuzzuf_Jobs.csv");
//          try {
     private DataFrame WazafDatafram;

     public DataFrame readCSV(String path) throws IOException {
         DataFrame df1=null;
         try {
             CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
             DAO D = new DAO();
              df1 = Read.csv(path, format);
             WazafDatafram = df1;
             List<POJO> df3 = D.getWazafList(WazafDatafram);
         } catch (URISyntaxException ex) {
             Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return df1;
     }
     public DataFrame getWazafDatafram() {
        return WazafDatafram;
    }

     
     public static List<POJO> getWazafList(DataFrame WazafDatafram) {
        // assert WazafDatafram != null;
        List<POJO> Wzaflist = new ArrayList<>();
        ListIterator<Tuple> iterator = WazafDatafram.stream().collect(Collectors.toList()).listIterator();

        while (iterator.hasNext()) {
            Tuple t = iterator.next();
            POJO p = new POJO();
            p.setTitle((String) t.get("Title"));
            p.setCompany((String) t.get("Company"));
            p.setLocation((String) t.get("Location"));
            p.setType((String) t.get("Type"));
            p.setLevel((String) t.get("Level"));
            p.setExp((String) t.get("YearsExp"));
            p.setCountry((String) t.get("Country"));
            p.setSkills(t.get("Skills").toString().split(","));

            Wzaflist.add(p);
        }
        return Wzaflist;
     }
 public void drawPieChart(final Map<String, List<POJO>> companiesJobsMap) {
        try {
            /*JFrame frame = new JFrame();
            frame.getContentPane().add(new MyComponent(JobTitlesMap));
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);*/
            // Create Chart
            PieChart chart = new PieChartBuilder().width(1200).height(700).title("My Pie Chart")
                    .theme(Styler.ChartTheme.GGPlot2).build();

            // Customize Chart
            chart.getStyler().setLegendVisible(false);
            chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndValue);
            chart.getStyler().setAnnotationDistance(1.3);
            chart.getStyler().setPlotContentSize(.8);
            chart.getStyler().setStartAngleInDegrees(90);

            for (Map.Entry<String, List<POJO>> entry : companiesJobsMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue().size();
                chart.addSeries(key, value);
            }

            // Show it
            new SwingWrapper(chart).displayChart();

            // Save it
            BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

            // or save it in high-res
            BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
 public void drawBarChart(Map<String, List<POJO>> categoryMap, List<Object> topCategoty, String title, String xAxis) {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(1200).height(600)
                .title(title).xAxisTitle(xAxis).yAxisTitle("Count").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        //chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        /*List<Double> jobTitleCount = new ArrayList<>();
        List<String> JobTitles=new ArrayList<>();
        for (Map.Entry<String, List<Job>> entry : categoryMap.entrySet()) {
            String key = entry.getKey();
            JobTitles.add(key);
            Integer value = entry.getValue().size();
            jobTitleCount.add((double) value);
        }*/
        List<Double> categoryCount = new ArrayList<>();
        for (Object key : topCategoty) {
            Integer value = categoryMap.get((String) key).size();
            categoryCount.add((double) value);
        }

        // Series
        chart.addSeries(title, topCategoty, categoryCount);
        new SwingWrapper<>(chart).displayChart();
    }
}