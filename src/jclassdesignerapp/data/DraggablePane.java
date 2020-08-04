package jclassdesignerapp.data;

import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

/**
 * This is a draggable ellipse for our pose application.
 * 
 * @author McKillaGorilla
 * @verion 1.0
 */
public class DraggablePane extends Ellipse implements Draggable {
    double startCenterX;
    double startCenterY;
    Text text;
    
    public DraggablePane() {
	setCenterX(0.0);
	setCenterY(0.0);
	setRadiusX(0.0);
	setRadiusY(0.0);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        text = new Text("");
    }
    
    @Override
    public PoseMakerState getStartingState() {
	return PoseMakerState.STARTING_ELLIPSE;
    }
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	setCenterX(newX);
	setCenterY(newY);
	startCenterX = x;
	startCenterY = y;
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - startCenterX;
	double height = y - startCenterY;
	double centerX = startCenterX + (width / 2);
	double centerY = startCenterY + (height / 2);
	setCenterX(centerX);
	setCenterY(centerY);
	setRadiusX(width / 2);
	setRadiusY(height / 2);	
	
    }
        
    @Override
    public double getX() {
	return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
	return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
	return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
	return getRadiusY() * 2;
    }
        
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));
	setRadiusX(initWidth/2);
	setRadiusY(initHeight/2);
    }
    
    @Override
    public String getShapeType() {
	return ELLIPSE;
    }
    
     public double getStartX() {
        return startCenterX;
    }
    public double getStartY() {
        return startCenterY;
    }
    public void setName(String n) {
        this.text.setText(n);
        text.setLayoutX(100);
        text.setLayoutY(500);
    }
    public Text getName() {
        return text;
    }
}
