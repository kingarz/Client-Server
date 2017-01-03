import static java.lang.Math.PI;
import static java.lang.Math.toRadians;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;

public class Point implements Comparable<Point>,Serializable {

	@Override
	public int compareTo(Point other) {
		if (isDijkstry) {
			if (this.equals(other)) {
				return 0;
			} else if (this.length > other.length) {
				return 1;
			} else {
				return -1;
			}
		}
		else
		{
			if (this.equals(other)) {
				return 0;
			} else if (this.length + this.calculateLength(this, ending) > other.length+other.calculateLength(this, ending)) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	Double deg2rad(Double deg) {
		return deg * (PI / 180);
	}

	public Double calculateLength(Point beg, Point end) {
		int R = 6378137;
		Double dLat = deg2rad(end.getLat() - beg.getLat()); // deg2rad
															// below
		Double dLon = deg2rad(end.getLon() - beg.getLon());
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRadians((beg.getLat())))
				* Math.cos(toRadians(end.getLat())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = R * c; // Distance in km
		length = distance;
		return length;
	}

	// private static final long serialVersionUID = 5462223600l;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
 
	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}
 
	public void setLon(Double lon) {
		this.lon = lon;
	}

	
	private double lat;
	private double lon;
	private double length = 0.0;
	private int id;
	
	static boolean isDijkstry = true;
	static Point ending;
	transient Point prev = null;
	transient ArrayList<Connection> CList = new ArrayList<Connection>();
	

}
