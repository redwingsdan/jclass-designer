package jclassdesignerapp.controller;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jclassdesignerapp.data.DataManager;
import jclassdesignerapp.data.Draggable;
import jclassdesignerapp.data.PoseMakerState;
import static jclassdesignerapp.data.PoseMakerState.DRAGGING_NOTHING;
import static jclassdesignerapp.data.PoseMakerState.DRAGGING_SHAPE;
import static jclassdesignerapp.data.PoseMakerState.SELECTING_SHAPE;
import static jclassdesignerapp.data.PoseMakerState.SIZING_SHAPE;
import jclassdesignerapp.gui.Workspace;
import saf.AppTemplate;

/**
 * This class responds to interactions with the rendering surface.
 * 
 * @author McKillaGorilla
 * @version 1.0
 */
public class CanvasController {
    AppTemplate app;
    
    public CanvasController(AppTemplate initApp) {
	app = initApp;
    }
    
    public void processCanvasMouseExited(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(PoseMakerState.DRAGGING_SHAPE)) {
	    
	}
	else if (dataManager.isInState(PoseMakerState.SIZING_SHAPE)) {
	    
	}
    }
    
    public void processCanvasMousePress(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(SELECTING_SHAPE)) {
	    // SELECT THE TOP SHAPE
            Node shape;
            try{
	     shape = dataManager.selectTopShape(x, y);
             
            }
            catch(Exception e){
                 shape = (Text) dataManager.getShapes().get(dataManager.getShapes().size() - 1);
            }
	    Scene scene = app.getGUI().getPrimaryScene();
            app.getWorkspaceComponent().reloadWorkspace();

	    // AND START DRAGGING IT
	    if (shape != null) {
		scene.setCursor(Cursor.MOVE);
		dataManager.setState(PoseMakerState.DRAGGING_SHAPE);
		app.getGUI().updateToolbarControls(false);
	    }
	    else {
		scene.setCursor(Cursor.DEFAULT);
		dataManager.setState(DRAGGING_NOTHING);
		app.getWorkspaceComponent().reloadWorkspace();
	    }
	}
	else if (dataManager.isInState(PoseMakerState.STARTING_RECTANGLE)) {
	    dataManager.startNewRectangle(x, y);
	}
	else if (dataManager.isInState(PoseMakerState.STARTING_ELLIPSE)) {
	    dataManager.startNewEllipse(x, y);
	}
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
    }
    
    public void processCanvasMouseMoved(int x, int y) {
	//Workspace workspace = (Workspace)app.getWorkspaceComponent();
	//workspace.setDebugText("(" + x + "," + y + ")");
    }
    
    public void processCanvasMouseDragged(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(SIZING_SHAPE)) {
	    Draggable newDraggableShape = (Draggable)dataManager.getNewShape();
             newDraggableShape.setLocationAndSize(x, y, 400, 100);
             dataManager.setState(PoseMakerState.DRAGGING_SHAPE);
	    //newDraggableShape.size(x, y);
            //newDraggableShape.setLocationAndSize(x, y, 200, 200);
	}
	else if (dataManager.isInState(DRAGGING_SHAPE)) {
	    Draggable selectedDraggableShape = (Draggable)dataManager.getSelectedShape();
	    selectedDraggableShape.drag(x, y);
	    app.getGUI().updateToolbarControls(false);
	}
    }
    
    public void processCanvasMouseRelease(int x, int y) {
	DataManager dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(SIZING_SHAPE)) {
	    dataManager.selectSizedShape();
	    app.getGUI().updateToolbarControls(false);
	}
	else if (dataManager.isInState(PoseMakerState.DRAGGING_SHAPE)) {
	    dataManager.setState(SELECTING_SHAPE);
	    Scene scene = app.getGUI().getPrimaryScene();
	    scene.setCursor(Cursor.DEFAULT);
	    app.getGUI().updateToolbarControls(false);
	}
	else if (dataManager.isInState(PoseMakerState.DRAGGING_NOTHING)) {
	    dataManager.setState(SELECTING_SHAPE);
	}
    }
}
