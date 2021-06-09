import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.sun.org.apache.bcel.internal.generic.DUP2;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;


class DataPoint {
    public double temp;
	public double humidity;
	public double pressure;
	
	//public List<Hourly> hourly = new ArrayList<Hourly>();
	

	public double lat;
	public double lon;

	DataPoint( double t, double h, double p, double lat, double lon) {
		this.temp = t;
	    this.humidity = h;
	    this.pressure = p;
		this.lat = lat;
		this.lon = lon;
        
	}
	
	

	public String toString() {
		return String.format("%f %f %f %f %f", lat, lon, temp, humidity, pressure);
	}
}

 class Hourly

{
    public double temp; 

    public double wind_speed;

    public double pressure; 
}


public class JSON {
    public static void main(String args[]){
      
    	// WINDOW APP
           JFrame frame = new JFrame("Backpack");
           frame.setSize(900,700);
           JTextArea textArea = new JTextArea();
           textArea.setEditable(false);
           frame.getContentPane().add(BorderLayout.CENTER, textArea);
           JPanel panel = new JPanel();
           JLabel label = new JLabel("Live - Please enter the city:");
           JPanel panel2 = new JPanel();
           JLabel label2 = new JLabel("Historical - Please enter the coordinates and unix time:");
           JTextField textfield = new JTextField(10);
           JTextField textfield2 = new JTextField(10);
           JTextField textfield3 = new JTextField(10);
           JTextField textfield4 = new JTextField(10);
           JButton calculate = new JButton("Get Weather");
           JButton clear = new JButton("Clear");
           JButton calculate2 = new JButton("Get Weather");
           JButton clear2 = new JButton("Clear");
           panel.add(label);
           panel.add(textfield);
           panel.add(calculate);
           panel.add(clear);
           panel2.add(label2);
           panel2.add(textfield2);
           panel2.add(textfield3);
           panel2.add(textfield4);
           panel2.add(calculate2);
           panel2.add(clear2);
           calculate.addActionListener(new ActionListener() {
           	@Override
           	public void actionPerformed(ActionEvent e) {
           		textArea.append(getWeather(textfield.getText()));
           	}
           });
           clear.addActionListener(new ActionListener() {
              	@Override
              	public void actionPerformed(ActionEvent e) {
              		textArea.setText("");
              	}
              });
           double[] Array = new double[6];
           
           calculate2.addActionListener(new ActionListener() {
              	@Override
              	public void actionPerformed(ActionEvent e) {
                   double[] tempArray =  getWeather2(textfield2.getText(),textfield3.getText(),textfield4.getText()).clone();
              		for(int i=0; i<6; i++)
              		{
              			Array[i] = tempArray[i] -273;   // Przlicznik
              		}
                   String Text = "First measurement: " + String.valueOf(tempArray[0]) + " [K]" +  "\n" + "Second measurement: " + String.valueOf(tempArray[1]) + " [K]" +  "\n" + "Third measurement: " + String.valueOf(tempArray[2]) + " [K]" +  "\n" + "Fourth measurement: " + String.valueOf(tempArray[3]) + " [K]" +  "\n" + "Fifth measurement: " + String.valueOf(tempArray[4]) + " [K]" +  "\n" + "Sixth measurement: " + String.valueOf(tempArray[5]) + " [K]" +  "\n";
              		textArea.append(Text );
              		
              		///////////////////////
              		
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                     	   
                            JFrame frame = new JFrame("Day time temperature");
                            
                            frame.setSize(600, 400);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.setVisible(true);

                           // XYDataset ds = createDataset(Array);
                            DefaultXYDataset ds = new DefaultXYDataset();

                           // double[][] data = { {1, 2, 3}, {Array[0],Array[1], Array[2]} };
                            double val1 = Array[0];
                            System.out.print("TEEEEST" + val1);
                            double[][] data = { {1, 2, 3,4,5,6}, {Array[0], Array[1], Array[2],Array[3],Array[4],Array[5]} };

                            ds.addSeries("series1", data);
                            JFreeChart chart = ChartFactory.createXYLineChart("Day time temperature",
                                    "Measurements", "Temperature [°C]", ds, PlotOrientation.VERTICAL, true, true,
                                    false);

                            ChartPanel cp = new ChartPanel(chart);

                            frame.getContentPane().add(cp);
                        }
                    });
              		
              		/////
              	}
              });
              clear2.addActionListener(new ActionListener() {
                 	@Override
                 	public void actionPerformed(ActionEvent e) {
                 		textArea.setText("");
                 	}
                 });
           
           frame.getContentPane().add(BorderLayout.SOUTH, panel);
           frame.setVisible(true);
           frame.getContentPane().add(BorderLayout.NORTH, panel2);
           frame.setVisible(true);
           
           ///////////////////////////////////////////////////////////////////////////////////////
  /*  
           SwingUtilities.invokeLater(new Runnable() {
               public void run() {
            	   
                   JFrame frame = new JFrame("Charts");
                   
                   frame.setSize(600, 400);
                   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   frame.setVisible(true);

                  // XYDataset ds = createDataset(Array);
                   DefaultXYDataset ds = new DefaultXYDataset();

                  // double[][] data = { {1, 2, 3}, {Array[0],Array[1], Array[2]} };
                   double val1 = Array[0];
                   System.out.print("TEEEEST" + val1);
                   double[][] data = { {1, 2, 3}, {val1, 250, 300} };

                   ds.addSeries("series1", data);
                   JFreeChart chart = ChartFactory.createXYLineChart("Test Chart",
                           "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                           false);

                   ChartPanel cp = new ChartPanel(chart);

                   frame.getContentPane().add(cp);
               }
           });
           */

       }		// koniec maina
/*
       private static XYDataset createDataset(double[] arr) {

           DefaultXYDataset ds = new DefaultXYDataset();

           double[][] data = { {1, 2, 3}, {arr[0],arr[1], arr[2]} };

           ds.addSeries("series1", data);

           return ds;
           
           
       }
    */       
           
///////////////////////////////////////////////////////////////////////////////////////
           
       
    
              
    public static String getWeather(String City) {
    	String FullText = "";
   
    
    	try {
    		String Call1 = "http://api.openweathermap.org/data/2.5/weather?q=";
     	   	String Call = Call1 + City;
     	   	String finalCall = Call + "&appid=ac7c75b9937a495021393024d0a90c44";
     	   	URL url = new URL(finalCall);
    		//URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=3081368&appid=ac7c75b9937a495021393024d0a90c44");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.connect();
 		   StringBuilder str = new StringBuilder();
            try(Scanner in = new Scanner(con.getInputStream()))
            {
              while(in.hasNextLine())
              {
                str.append(in.nextLine());
              }
            }
    	
 	   	
 	   JSONObject jo = new JSONObject(str.toString());
	   JSONObject params = jo.getJSONObject("main");
	   JSONObject geo = jo.getJSONObject("coord");
       System.out.println(params.toString());
       System.out.println(jo.toString());
       System.out.println(params.get("temp"));
       
       DataPoint dp = new DataPoint(Double.valueOf(params.get("temp").toString()),
               Double.valueOf(params.get("humidity").toString()),
				Double.valueOf(params.get("pressure").toString()),
               Double.valueOf(geo.get("lat").toString()),
               Double.valueOf(geo.get("lon").toString()));
       
       System.out.println(dp);
       //FullText = "City: " + City +  "\n" + "Temperature: " + dp.temp +  "\n";
       FullText = "Current weather " + "\n" + "City: " + City +  "\n" + "Temperature: " + dp.temp + "\n" + "Humidity: " +dp.humidity + "\n" + "Pressure: " +dp.pressure + "\n" + "Latitude: " +dp.lat + "\n" + "Longitude: " +dp.lon + "\n";

   }
   catch(Exception e)
   {
     e.printStackTrace();
   }

    	return FullText;
    }
    
    

    
    /////////////////////////
    public static double[] getWeather2(String Lat, String Lon, String Time) {
    	String FullText = "dsdsdsds";
        double[] array = new double[6];

    	try {
    		//long unixTime = Instant.now().getEpochSecond();
    		//LocalDate now = LocalDate.now();
    		//System.out.println(unixTime);
    		//System.out.println(now);
    		//String str = "Jun 13 2003 23:11:52.454 UTC";
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		//long Time2 = Instant.Time.getEpochSecond();
    		Date date = df.parse(Time);
    		long epoch = date.getTime();
    		long epoch2 = epoch/1000;
    		String epoch3 = String.valueOf(epoch2);
    		//////////////////////////
    		String skladnik1 = "http://api.openweathermap.org/data/2.5/onecall/timemachine?lat=";
     	   	String skladnik2 = Lat ;
     	   	String skladnik3 = Lon ;
     	   	String skladnik4 = epoch3; //Time; //+ "&appid=ac7c75b9937a495021393024d0a90c44";
     	   	String koniec = skladnik1 + skladnik2+ "&lon=" + skladnik3+ "&dt=" + skladnik4 + "&appid=ac7c75b9937a495021393024d0a90c44";
     	   	URL url2 = new URL(koniec);
    		//URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=3081368&appid=ac7c75b9937a495021393024d0a90c44");
            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
            con2.setRequestMethod("GET");
            con2.setRequestProperty("Accept", "application/json");
            con2.connect();
 		   StringBuilder str2 = new StringBuilder();
            try(Scanner in = new Scanner(con2.getInputStream()))
            {
              while(in.hasNextLine())
              {
                str2.append(in.nextLine());
              }
            }
    	
 	   	
 	   JSONObject jo = new JSONObject(str2.toString());
 	   
 	   //JSONObject jo = new JSONObject(str2.toString());
	   JSONObject params = jo.getJSONObject("current");
	   //JSONObject params2 = jo.getJSONObject("hourly");
	   JSONArray hist_data = jo.getJSONArray("hourly");
	   JSONObject object = hist_data.getJSONObject(0);
	   JSONObject object2 = hist_data.getJSONObject(1);
	   JSONObject object3 = hist_data.getJSONObject(2);
	   JSONObject object4 = hist_data.getJSONObject(3);
	   JSONObject object5 = hist_data.getJSONObject(4);
	   JSONObject object6 = hist_data.getJSONObject(5);
	   
	   
	   
	   
	   //JSONObject geo = jo.getJSONObject("coord");
       System.out.println(params.toString());
       System.out.println("sasasasasasasaas");
       System.out.println(object.toString());
       System.out.println("nananananaanans");
       
       //Temp_hist.Text = pogoda2.hourly[0].temp.ToString() + "\r\n" + 
       //System.out.println(jo.toString());
       //System.out.println(params.get("temp"));
       
       DataPoint dp2 = new DataPoint(Double.valueOf(object.get("temp").toString()),
               Double.valueOf(object.get("temp").toString()),
               Double.valueOf(object.get("temp").toString()),
               Double.valueOf(object.get("temp").toString()),
               Double.valueOf(object.get("temp").toString()));
      
       
       DataPoint dp3 = new DataPoint(Double.valueOf(object2.get("temp").toString()),
               Double.valueOf(object2.get("temp").toString()),
               Double.valueOf(object2.get("pressure").toString()),
               Double.valueOf(object2.get("humidity").toString()),
               Double.valueOf(object2.get("pressure").toString()));
       
       DataPoint dp4 = new DataPoint(Double.valueOf(object3.get("temp").toString()),
               Double.valueOf(object3.get("temp").toString()),
               Double.valueOf(object3.get("pressure").toString()),
               Double.valueOf(object3.get("humidity").toString()),
               Double.valueOf(object3.get("pressure").toString()));
       
       DataPoint dp5 = new DataPoint(Double.valueOf(object4.get("temp").toString()),
               Double.valueOf(object4.get("temp").toString()),
               Double.valueOf(object4.get("pressure").toString()),
               Double.valueOf(object4.get("humidity").toString()),
               Double.valueOf(object4.get("pressure").toString()));
       
       DataPoint dp6 = new DataPoint(Double.valueOf(object5.get("temp").toString()),
               Double.valueOf(object5.get("temp").toString()),
               Double.valueOf(object5.get("pressure").toString()),
               Double.valueOf(object5.get("humidity").toString()),
               Double.valueOf(object5.get("pressure").toString()));
       
       DataPoint dp7 = new DataPoint(Double.valueOf(object6.get("temp").toString()),
               Double.valueOf(object6.get("temp").toString()),
               Double.valueOf(object6.get("pressure").toString()),
               Double.valueOf(object6.get("humidity").toString()),
               Double.valueOf(object6.get("pressure").toString()));
 	   
	   
     
      System.out.println(jo.toString());
      System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh");
      System.out.println(dp2.temp);
      System.out.println(dp3.temp);
      System.out.println(dp4.temp);
      System.out.println(dp5.temp);
      System.out.println(dp6.temp);
      System.out.println(dp7.temp);
      
      FullText = "Historical weather " + "\n" +"Temp1: " + dp2.temp + "K" +  "\n" + "Temp2: " + dp3.temp + "\n" + "Temp3: " +dp4.temp + "\n" + "Temp4: " +dp5.temp + "\n"+ "Temp5: " +dp6.temp + "\n"+ "Temp6: " +dp7.temp + "\n";
  		//array[] = {dp2.temp, dp3.temp, dp4.temp, dp5.temp, dp6.temp, dp7.temp};
      	array[0] = dp2.temp;
      	array[1] = dp3.temp;
      	array[2] = dp4.temp;
      	array[3] = dp5.temp;
      	array[4] = dp6.temp;
      	array[5] = dp7.temp;

      
      
   }
   catch(Exception e)
   {
     e.printStackTrace();
   }

    	
    	//return FullText;
    	return array;
    	
    	
    }
    /////////////////////////
    
    
    
    
    
    
}
 	   	



 	   	
    	

