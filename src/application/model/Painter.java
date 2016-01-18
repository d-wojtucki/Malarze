package application.model;

import java.util.ArrayList;
import java.util.List;

public class Painter {
	private String name;
	private List<String> paintings;
	private List<String> descriptions;
	private int currentIndex;

	public Painter(){
		paintings = new ArrayList<String>();
		descriptions = new ArrayList<String>();
		currentIndex = 0;
	}

	public Painter(String name){
		this.name = name;
		currentIndex = 0;
		descriptions = new ArrayList<String>();
		paintings = new ArrayList<String>();
	}

	public void addPainting(String imageUrl){
		paintings.add(imageUrl);
	}

	public void addDescription(String description){
		descriptions.add(description);
	}

	public String getNextPainting(){
		int size = paintings.size();
		if(currentIndex<size-1){
			currentIndex+=1;
		}
		else{
			currentIndex = 0;
		}
		return paintings.get(currentIndex);
	}

	public String getPreviousPainting(){
		int size = paintings.size();
		if(currentIndex == 0){
			currentIndex = size-1;
		}
		else{
			currentIndex -= 1;
		}
		return paintings.get(currentIndex);
	}

	public String getCurrentPainting(){
		return paintings.get(currentIndex);
	}

	public String getNextDescription(){
		int size = descriptions.size();
		if(currentIndex<size-1){
			currentIndex+=1;
		}
		else{
			currentIndex = 0;
		}
		return descriptions.get(currentIndex);
	}

	public String getPreviousDescription(){
		int size = descriptions.size();
		if(currentIndex == 0){
			currentIndex = size-1;
		}
		else{
			currentIndex -= 1;
		}
		return descriptions.get(currentIndex);
	}

	public String getCurrentDescription(){
		return descriptions.get(currentIndex);
	}

	public String getName(){
		return this.name;
	}
}
