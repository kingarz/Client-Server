import java.util.ArrayList;
public class Connection {
	public Point getBegin() {
		return begin;
	}
	public void setBegin(Point begin) {
		this.begin = begin;
	}
	public Point getEnd() {
		return end;
	}
	public void setEnd(Point end) {
		this.end = end;
	}
	public Integer getCrosId() {
		return crosId;
	}
	public void setCrosId(Integer crosId) {
		this.crosId = crosId;
	}
	public Integer getWayId() {
		return wayId;
	}
	public void setWayId(Integer wayId) {
		this.wayId = wayId;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	private Integer crosId;
	private Integer wayId;
	private Double length;
	private Point begin;
	private Point end;
	
	
	

}
