package graphic_system.model;

public class RegionCode {
	
	int top, bottom, right, left, all;
	
	RegionCode() {
		top = 0;
		bottom = 0;
		right = 0;
		left = 0;
		all = 0;
	}
	
	
	public boolean isInsideWindow(){
		return all == 0;
	}
}
