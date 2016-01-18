package application.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import application.model.Painter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
	@FXML
	Button next;
	@FXML
	Button previous;
	@FXML
	ChoiceBox<String> cb;
	@FXML
	ImageView image;
	@FXML
	Label description;

	private String url = "http://pinakoteka.zascianek.pl/Artists.htm";
	private List<Painter> painters;
	int selectedIndex;

	@FXML
	private void initialize(){
		painters = new ArrayList<Painter>();
		connect();
		selectedIndex = cb.getSelectionModel().getSelectedIndex();
		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			public void changed(ObservableValue<? extends Number> ov, Number arg1, Number arg2) {
				changePainter();
			}

		});


		Painter p = painters.get(selectedIndex);

		image.setImage(new Image(p.getCurrentPainting()));
		image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        description.setText(p.getCurrentDescription());
	}

	/*
	 * Connect with the url
	 * Loads painters to the list
	 */
	private void connect(){
	    try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			links.remove(links.size()-1);
			links.remove(links.size()-1);
			for(Element link:links){
				String painterName = link.text();
				if(painterName.equals("Matejko, Jan")){
					/*
					 * POMIJAM Z UWAGI NA ODMIENNIE SKONSTRUOWANA PODSTRONE
					 */
					continue;
				}
				cb.getItems().add(painterName);
				String suburl = link.absUrl("href");
				Document subdoc = Jsoup.connect(suburl).get();
				Element table = subdoc.select("table").first();
				Elements images = table.select("td");

				Painter p = new Painter(painterName);
				for(Element image:images){
					if(image.toString().contains("src")){
						Elements test = image.select("a");
						p.addPainting(test.attr("abs:href"));
					}else if(image.toString().contains("font") && !image.toString().contains("href")){
						System.out.println(image.text());
						p.addDescription(image.text());
					}
				}
				painters.add(p);
			}

		} catch (IOException e) {
			System.out.println("Error connecting with "+url);
			e.printStackTrace();
		}
	    cb.getSelectionModel().selectFirst();
	}


	@FXML
	private void getNextImage(){
		Painter p = painters.get(selectedIndex);
		image.setImage(new Image(p.getNextPainting()));
		image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        description.setText(p.getNextDescription());
	}

	@FXML
	private void getPreviousImage(){
		Painter p = painters.get(selectedIndex);
		image.setImage(new Image(p.getPreviousPainting()));
		image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        description.setText(p.getPreviousDescription());
	}

	private void changePainter(){
		selectedIndex = cb.getSelectionModel().getSelectedIndex();
		Painter p = painters.get(selectedIndex);
		image.setImage(new Image(p.getCurrentPainting()));
		image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        description.setText(p.getCurrentDescription());
	}

}
