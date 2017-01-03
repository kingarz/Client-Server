
//import java.awt.font.GraphicAttribute;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
import java.util.Vector;

import org.hibernate.type.descriptor.sql.DecimalTypeDescriptor;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Queue;

//import javax.swing.SpringLayout.Constraints;

public class ReadGraph {
	public static final List<Point> WList = new ArrayList<Point>();

	public static void main(String[] args) throws FileNotFoundException {

		String lat = "";
		String lon = "";
		String text = "";
		String ind = "";
		String len = "";
		String clen = "";
		String cind = "";

		try {

			Scanner readFile = new Scanner(new File("C:/Users/Kinga/Desktop/crossings1.txt"));
			// Lista punktów
			// List<Point> WList = new ArrayList<Point>();
			// lista polaczen
			List<Connection> conList = new ArrayList<Connection>();

			while (readFile.hasNextLine()) {
				text = readFile.nextLine();
				if (!text.startsWith("/") && !text.isEmpty()) {
					ind = ind + text;
					text = readFile.nextLine();
					lat = lat + text;
					text = readFile.nextLine();
					lon = lon + text;
					text = readFile.nextLine();
					Point temp = new Point();
					temp.setId(Integer.parseInt(ind));
					temp.setLat(Double.parseDouble(lat));
					temp.setLon(Double.parseDouble(lon));
					WList.add(temp);
					int i = 0;
					while (!text.equals("/")) {
						Connection ctemp = new Connection();
						i++;
						cind = "";
						clen = "";
						clen = clen + text;
						text = readFile.nextLine();
						cind = cind + text;
						ctemp.setWayId(Integer.parseInt(cind));
						ctemp.setCrosId(Integer.parseInt(ind));
						ctemp.setLength(Double.parseDouble(clen));
						conList.add(i - 1, ctemp);

						text = readFile.nextLine();
					}

					lat = "";
					lon = "";
					ind = "";
					clen = "";
					cind = "";

				}

			}

			readFile.close();
			// ustawianie pocz i konca polaczen
			// conList-lista polaczen
			for (int i = 0; i < conList.size(); i++) {
				Point temp = null;
				Point temp2 = null;
				for (int j = 0; j < WList.size(); j++) {
					if (conList.get(i).getCrosId() == WList.get(j).getId()) {
						temp = WList.get(j);
						conList.get(i).setBegin(temp);
					}

					if (conList.get(i).getWayId() == WList.get(j).getId()) {
						temp2 = WList.get(j);
						conList.get(i).setEnd(temp2);
					}
				}

				if (conList.get(i).getEnd() != null) {
					temp.CList.add(conList.get(i));
				}

			}

			// Dijstry Priority Queue
			Point min = null;
			Point end = null;
			Queue<Point> Q = new PriorityQueue<>();
			Queue<Point> S = new PriorityQueue<>();
			// Queue<Point> newQ= new PriorityQueue<>();
			for (Point j : WList) {
				if (j.getId() == 415) {
					end = j;
				}
				if (j.getId() == 84) {
					min = j;
				}
			}
			Q.add(min);
			while (min != end) {
				if (Q.isEmpty()) {
					break;
				} // comparator
				min = Q.peek();
				// Iterator<Point> it = Q.iterator();
				Q.remove(min);
				S.add(min);
				// przegladam sasiadow min
				// Iterator<Point> it = Q.iterator();
				for (Connection i : min.CList) {
					if (!contains(S, i.getEnd()) && !contains(Q, i.getBegin())) {
						i.getEnd().setLength(i.getBegin().getLength() + i.getLength());
						i.getEnd().prev = i.getBegin();
						// dodaje sasiada o najm drodze do Q
						Q.add(i.getEnd());

					}
				}
			}
			if (end.prev == null) {
				System.out.println("Nie ma sciezki");
			} else {
				ArrayList<Point> path = new ArrayList<Point>();
				Point tmp = end;
				while (tmp != null) {
					System.out.println("id " + tmp.getId() + " ");
					System.out.println("lat " + tmp.getLat() + " ");
					System.out.println("lon " + tmp.getLon() + " ");
					tmp = tmp.prev;
					path.add(tmp);
				}
			}

			System.out.println("ALGORYTM A*");
			// A*
			Point.isDijkstry = false;
			ArrayList<Point> OL = new ArrayList<Point>();
			ArrayList<Point> CL = new ArrayList<Point>();

			for (Point j : WList) {
				if (j.getId() == 415) {
					end = j;
				}
				if (j.getId() == 84) {
					min = j;
				}
			}
			Point.ending = end;

			OL.add(min);
			while (min != end) {
				if (OL.isEmpty()) {
					break;
				}
				min = OL.get(0);
				for (int i = 0; i < OL.size(); i++) {
					if (OL.get(i).getLength() <= min.getLength()) {
						min = OL.get(i);

					}
				}
				OL.remove(min);
				CL.add(min);
				for (Connection i : min.CList) {
					if (!contains(CL, i.getEnd()) && !contains(OL, i.getBegin())) {
						i.getEnd().setLength(i.getBegin().getLength() + i.getLength());
						i.getEnd().prev = i.getBegin();
						OL.add(i.getEnd());

					}
				}
			}
			if (end.prev == null) {
				System.out.println("Nie ma sciezki");
			} else {
				ArrayList<Point> path = new ArrayList<Point>();
				Point tmp = end;
				while (tmp != null) {
					System.out.println("id " + tmp.getId() + " ");
					System.out.println("lat " + tmp.getLat() + " ");
					System.out.println("lon " + tmp.getLon() + " ");
					tmp = tmp.prev;
					path.add(tmp);
				}

			}
			// Serialization objects to file *.ser
			int count = 0;

			try {

				FileOutputStream fileOut = new FileOutputStream("points.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				
				/*while (count < WList.size()) {
					Point e = WList.get(count);
					out.writeObject(e);
					count++;
				}*/
				out.writeObject(WList);
				out.close();
				fileOut.close();

			} catch (IOException i) {
				i.printStackTrace();
			}

			System.out.printf("Serialized data is saved in points.ser");
			// Deserialization objects from .ser
			
			List<Point> pt = new ArrayList<Point>();

			try {
		         FileInputStream fileIn = new FileInputStream("points.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		        pt = (List<Point>)in.readObject();
		         in.close();
		         fileIn.close();
		         System.out.print(" size:"+pt.size());
		      }catch(IOException i) {
		         i.printStackTrace();
		         return;
		      }catch(ClassNotFoundException c) {
		         System.out.println("Employee class not found");
		         c.printStackTrace();
		         return;
		      }
		      
			
			// Creating an xml which includes Points
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("Point", Point.class);
			xstream.alias("connection", Connection.class);
			xstream.aliasField("lat", Double.class, lat);
			xstream.aliasField("lon", Double.class, lon);
			xstream.aliasField("id", Integer.class, lon);
			PrintWriter file = null;
			System.out.println("-------------");
			try {
				file = new PrintWriter("xmlList.xml");
				String xml = xstream.toXML(WList);

				file.println(xml);
				/*
				 * for(int i=0; i<WList.size();i++) {
				 * 
				 * Point p = WList.get(i); String xml = xstream.toXML(p); //xml
				 * - skaldnik xmla dodany do pliku //rekonstrukcja z xmla
				 * //Point newP = (Point)xstream.fromXML(xml);
				 * file.println(xml);
				 * 
				 * }
				 */
				file.flush();
			} finally {
				if (file != null) {

					file.close();
				}
			}
			List<Point> deserializedL = new ArrayList<Point>();
			//
			try {
				file = new PrintWriter("xml.xml");
				for (int i = 0; i < WList.size(); i++) {

					Point p = WList.get(i);
					String xml = xstream.toXML(p);
					Point newPo = (Point) xstream.fromXML(xml);
					deserializedL.add(newPo);
					file.println(xml);

				}
				file.flush();
			}

			finally {
				if (file != null) {

					file.close();
				}
			}
			// odczyt listy z deserializowanymi punktami
			System.out.println("Deserializacja : ----------------------");
			for (int i = 0; i < deserializedL.size(); i++) {
				System.out.println("id " + deserializedL.get(i).getId());
				System.out.println("lat " + deserializedL.get(i).getLat());
				System.out.println("lon " + deserializedL.get(i).getLon());
				System.out.println("length " + deserializedL.get(i).getLength());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file to read!");
		}

	}

	static private boolean contains(Queue<Point> CL, Point p) {
		for (Point i : CL) {
			if (p.getId() == i.getId()) {
				return true;
			}
		}
		return false;
	}

	static private boolean contains(ArrayList<Point> CL, Point p) {
		for (Point i : CL) {
			if (p.getId() == i.getId()) {
				return true;
			}
		}
		return false;
	}
}
