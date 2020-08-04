package jclassdesignerapp.data;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This is a draggable rectangle for our pose application.
 * 
 * @author McKillaGorilla
 * @version 1.0
 */
public class DraggableRectangle extends Rectangle implements Draggable {
    double startX;
    double startY;
    Text text;
    Pane pane;
    Text text2;
    
    public DraggableRectangle() {
	setX(0.0);
	setY(0.0);
	setWidth(0.0);
	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
        text = new Text("");
        text2 = new Text("");
        pane = new Pane();
    }
    
    @Override
    public PoseMakerState getStartingState() {
	return PoseMakerState.STARTING_RECTANGLE;
    }
    
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	setX(x);
	setY(y);
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setX(x);
        text.setY(y);
      
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - (getX() + (getWidth()/2));
	double diffY = y - (getY() + (getHeight()/2));
        text.setLayoutX(diffX);
        text.setLayoutY(diffY);
        text.setX(getX() + 120);
        text.setY(getY() + 30);
        text2.setX(getX() + 120);
        text2.setY(getY() + 70);
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - getX();
	widthProperty().set(width);
	double height = y - getY();
	heightProperty().set(height);	
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
	widthProperty().set(initWidth);
	heightProperty().set(initHeight);
    }
    
    @Override
    public String getShapeType() {
	return RECTANGLE;
    }
    
    public double getStartX() {
        return startX;
    }
    public double getStartY() {
        return startY;
    }
    public void setName(String n) {
        this.text.setText(n);
        pane.getChildren().add(text);
    }
    
    public void setName2(String n) {
        this.text2.setText(n);
        pane.getChildren().add(text2);
    }
    public Text getName() {
        return text;
    }
    
    public Text getName2() {
        return text2;
    }
    
}
