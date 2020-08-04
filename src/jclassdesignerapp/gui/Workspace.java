package jclassdesignerapp.gui;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import static jclassdesignerapp.PropertyType.DIMENSIONS_ICON;
import static jclassdesignerapp.PropertyType.DIMENSIONS_TOOLTIP;
import static jclassdesignerapp.PropertyType.ELLIPSE_ICON;
import static jclassdesignerapp.PropertyType.ELLIPSE_TOOLTIP;
import static jclassdesignerapp.PropertyType.MOVE_TO_BACK_ICON;
import static jclassdesignerapp.PropertyType.MOVE_TO_BACK_TOOLTIP;
import static jclassdesignerapp.PropertyType.MOVE_TO_FRONT_ICON;
import static jclassdesignerapp.PropertyType.MOVE_TO_FRONT_TOOLTIP;
import static jclassdesignerapp.PropertyType.RECTANGLE_ICON;
import static jclassdesignerapp.PropertyType.RECTANGLE_TOOLTIP;
import static jclassdesignerapp.PropertyType.REDO_ICON;
import static jclassdesignerapp.PropertyType.REDO_TOOLTIP;
import static jclassdesignerapp.PropertyType.REMOVE_ELEMENT_TOOLTIP;
import static jclassdesignerapp.PropertyType.REMOVE_ICON;
import static jclassdesignerapp.PropertyType.REMOVE_TOOLTIP;
import static jclassdesignerapp.PropertyType.SELECTION_TOOL_ICON;
import static jclassdesignerapp.PropertyType.SELECTION_TOOL_TOOLTIP;
import static jclassdesignerapp.PropertyType.SNAPSHOT_ICON;
import static jclassdesignerapp.PropertyType.SNAPSHOT_TOOLTIP;
import static jclassdesignerapp.PropertyType.UNDO_ICON;
import static jclassdesignerapp.PropertyType.UNDO_TOOLTIP;
import static jclassdesignerapp.PropertyType.ZOOMIN_ICON;
import static jclassdesignerapp.PropertyType.ZOOMIN_TOOLTIP;
import static jclassdesignerapp.PropertyType.ZOOMOUT_ICON;
import static jclassdesignerapp.PropertyType.ZOOMOUT_TOOLTIP;
import jclassdesignerapp.controller.CanvasController;
import jclassdesignerapp.controller.PoseEditController;
import jclassdesignerapp.data.DataManager;
import static jclassdesignerapp.data.DataManager.BLACK_HEX;
import static jclassdesignerapp.data.DataManager.WHITE_HEX;
import jclassdesignerapp.data.DraggableRectangle;
import jclassdesignerapp.data.PoseMakerState;
import saf.ui.AppYesNoCancelDialogSingleton;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS Workspace'S COMPONENTS TO A STYLE SHEET THAT IT USES
    static final String CLASS_MAX_PANE = "max_pane";
    static final String CLASS_RENDER_CANVAS = "render_canvas";
    static final String CLASS_BUTTON = "button";
    static final String CLASS_EDIT_TOOLBAR = "edit_toolbar";
    static final String CLASS_EDIT_TOOLBAR_ROW = "edit_toolbar_row";
    static final String CLASS_COLOR_CHOOSER_PANE = "color_chooser_pane";
    static final String CLASS_COLOR_CHOOSER_CONTROL = "color_chooser_control";
    static final String EMPTY_TEXT = "";
    static final int BUTTON_TAG_WIDTH = 75;

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    // HAS ALL THE CONTROLS FOR EDITING
    VBox editToolbar;
    
    // FIRST ROW
    HBox row1Box;
    Button selectionToolButton;
    Button removeButton;
    Button rectButton;
    Button ellipseButton;
    
    // SECOND ROW
    HBox row2Box;
    Button moveToBackButton;
    Button moveToFrontButton;

    // THIRD ROW
    VBox row3Box;
    Label backgroundColorLabel;
    ColorPicker backgroundColorPicker;

    // FORTH ROW
    VBox row4Box;
    Label fillColorLabel;
    ColorPicker fillColorPicker;
    
    // FIFTH ROW
    VBox row5Box;
    Label outlineColorLabel;
    ColorPicker outlineColorPicker;
        
    // SIXTH ROW
    VBox row6Box;
    Label outlineThicknessLabel;
    Slider outlineThicknessSlider;
    
    // SEVENTH ROW
    HBox row7Box;
    Button snapshotButton;
    
    VBox row8Box;
    Label classNameLabel;
    TextField className;
    
    VBox row9Box;
    Label packageNameLabel;
    TextField packageName;
    Label parentNameLabel;
    ChoiceBox parentName;
    
    VBox row10Box;
    
    TableView table;
    TableView table2;
    
    // THIS IS WHERE WE'LL RENDER OUR DRAWING
    Pane canvas;
    
    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    PoseEditController poseEditController;    

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

	layoutGUI();
	setupHandlers();
    }
    
    public ColorPicker getFillColorPicker() {
	return fillColorPicker;
    }
    
    public ColorPicker getOutlineColorPicker() {
	return outlineColorPicker;
    }
    
    public ColorPicker getBackgroundColorPicker() {
	return backgroundColorPicker;
    }
    
    public Slider getOutlineThicknessSlider() {
	return outlineThicknessSlider;
    }
    
    private void layoutGUI() {
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
	
	// ROW 1
	row1Box = new HBox();
	selectionToolButton = gui.initChildButton(gui.getFileToolbar(), SELECTION_TOOL_ICON.toString(), SELECTION_TOOL_TOOLTIP.toString(), true);
	removeButton = gui.initChildButton(gui.getFileToolbar(), REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
        
	rectButton = gui.initChildButton(gui.getFileToolbar(), RECTANGLE_ICON.toString(), RECTANGLE_TOOLTIP.toString(), false);
       // Button select = gui.initChildButton(gui.getFileToolbar(),	SELECTION_TOOL_ICON.toString(),	    SELECTION_TOOL_TOOLTIP.toString(),	false);
        
       // Button addClass = gui.initChildButton(gui.getFileToolbar(),	RECTANGLE_ICON.toString(),	    RECTANGLE_TOOLTIP.toString(),	false);
        Button addInterface = gui.initChildButton(gui.getFileToolbar(),	ELLIPSE_ICON.toString(),	    ELLIPSE_TOOLTIP.toString(),	false);
        Button resize = gui.initChildButton(gui.getFileToolbar(),	DIMENSIONS_ICON.toString(),	    DIMENSIONS_TOOLTIP.toString(),	false);
       /// Button remove = gui.initChildButton(gui.getFileToolbar(),	REMOVE_ICON.toString(),	    REMOVE_ELEMENT_TOOLTIP.toString(),	false);
        Button undo = gui.initChildButton(gui.getFileToolbar(),	UNDO_ICON.toString(),	    UNDO_TOOLTIP.toString(),	false);
        Button redo = gui.initChildButton(gui.getFileToolbar(),	REDO_ICON.toString(),	    REDO_TOOLTIP.toString(),	false);
    
        Button zoomIn = gui.initChildButton(gui.getFileToolbar(),	ZOOMIN_ICON.toString(),	    ZOOMIN_TOOLTIP.toString(),	false);
        Button zoomOut = gui.initChildButton(gui.getFileToolbar(),	ZOOMOUT_ICON.toString(),	    ZOOMOUT_TOOLTIP.toString(),	false);
        //ellipseButton = gui.initChildButton(row1Box, ELLIPSE_ICON.toString(), ELLIPSE_TOOLTIP.toString(), false);

	// ROW 2
	row2Box = new HBox();
	moveToBackButton = gui.initChildButton(row2Box, MOVE_TO_BACK_ICON.toString(), MOVE_TO_BACK_TOOLTIP.toString(), true);
	moveToFrontButton = gui.initChildButton(row2Box, MOVE_TO_FRONT_ICON.toString(), MOVE_TO_FRONT_TOOLTIP.toString(), true);

	// ROW 3
	row3Box = new VBox();
	backgroundColorLabel = new Label("Background Color");
	backgroundColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
	row3Box.getChildren().add(backgroundColorLabel);
	row3Box.getChildren().add(backgroundColorPicker);

	// ROW 4
	row4Box = new VBox();
	fillColorLabel = new Label("Fill Color");
	fillColorPicker = new ColorPicker(Color.valueOf(WHITE_HEX));
	row4Box.getChildren().add(fillColorLabel);
	row4Box.getChildren().add(fillColorPicker);
	
	// ROW 5
	row5Box = new VBox();
	outlineColorLabel = new Label("Outline Color");
	outlineColorPicker = new ColorPicker(Color.valueOf(BLACK_HEX));
	row5Box.getChildren().add(outlineColorLabel);
	row5Box.getChildren().add(outlineColorPicker);
	
	// ROW 6
	row6Box = new VBox();
	outlineThicknessLabel = new Label("Outline Thickness");
	outlineThicknessSlider = new Slider(0, 10, 1);
	row6Box.getChildren().add(outlineThicknessLabel);
	row6Box.getChildren().add(outlineThicknessSlider);
	
	// ROW 7
	row7Box = new HBox();
	snapshotButton = gui.initChildButton(row7Box, SNAPSHOT_ICON.toString(), SNAPSHOT_TOOLTIP.toString(), false);
        
        row8Box = new VBox();
        classNameLabel = new Label("Class Name");
        className = new TextField();
        row8Box.getChildren().add(classNameLabel);
        row8Box.getChildren().add(className);
        
        packageNameLabel = new Label("Package Name");
        packageName = new TextField();
        
        packageName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                DataManager data3 = (DataManager)app.getDataComponent();
                DraggableRectangle y = (DraggableRectangle)data3.getSelectedShape();
                
                   y.setName2(packageName.getText());
                   //StackPane stack = new StackPane();
             //stack.getChildren().addAll(y, y.getName());
             packageName.getText();
             canvas.getChildren().add(y.getName2());
             Text z = y.getName2();
             z.setX(y.getX() + 120);
             z.setY(y.getY() + 70);
             data3.getShapes().add(z);
            }
        });
        className.setOnKeyReleased(new EventHandler<KeyEvent>() {
         public void handle(KeyEvent event) {
             DataManager data3 = (DataManager)app.getDataComponent();
             //add pane inside the rectangle
            // data3.getShapes().remove(data3.getShapes().size()-1);
      /*       try{
             DraggablePane y = (DraggablePane)data3.getSelectedShape();
             y.setName(className.getText());
            // StackPane stack = new StackPane();
            // stack.getChildren().addAll(y, y.getName());
             className.getText();
           //  data3.getShapes().add(stack);
            /// data3.getShapes().add(new Text("Hello"));
             reloadWorkspace();
             //data3.getShapes().remove(stack);
             }
            */
          //   catch(Exception e)
          //   {
                 DraggableRectangle y = (DraggableRectangle)data3.getSelectedShape();
                
                   y.setName(className.getText());
                   //StackPane stack = new StackPane();
             //stack.getChildren().addAll(y, y.getName());
             className.getText();
             canvas.getChildren().add(y.getName());
             Text z = y.getName();
             z.setX(y.getX() + 120);
             z.setY(y.getY() + 30);
             data3.getShapes().add(z);
             //canvas.getChildren().add(new Rectangle(200,200));
            // y.getName().setLayoutX(y.getLayoutX());
            // y.getName().setLayoutY(y.getLayoutY());
             //data3.getShapes().add(stack);
             //data3.getShapes().add(new Text("words"));
            
// data3.getShapes().add(y.getName());
           
            // reloadWorkspace();
           //  data3.getShapes().remove(stack);
           //  }
             
             
            // data3.getShapes().remove(stack);
          }
        });
        row8Box.getChildren().add(packageNameLabel);
        row8Box.getChildren().add(packageName);
        parentNameLabel = new Label("Parent");
        parentName = new ChoiceBox();
        parentName.getItems().addAll("P1", "P2", "P3");
        row8Box.getChildren().add(parentNameLabel);
        row8Box.getChildren().add(parentName);
        
        row9Box = new VBox();
        table = new TableView();
        table.setEditable(true);
        TableColumn nameCol = new TableColumn("VARIABLES");
        TableColumn typeCol = new TableColumn("Type");
        TableColumn accessCol = new TableColumn("Access");
        
        ObservableList<DraggableRectangle> data2; 
        data2 = FXCollections.observableArrayList(new DraggableRectangle());
        DraggableRectangle x = new DraggableRectangle();
        x.start(25, 30);
        data2.add(x);
        nameCol.setMinWidth(100);
        typeCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("startX"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("startY"));

        table.setItems(data2);
        table.getColumns().addAll(nameCol, typeCol, accessCol);
        row9Box.getChildren().add(table);
        
        
        row10Box = new VBox();
        table2 = new TableView();
        table2.setEditable(true);
        TableColumn nameCol2 = new TableColumn("METHODS");
        TableColumn typeCol2 = new TableColumn("Type2");
        TableColumn accessCol2 = new TableColumn("Access2");
        
        ObservableList<DraggableRectangle> data22; 
        data22 = FXCollections.observableArrayList(new DraggableRectangle());
        DraggableRectangle x2 = new DraggableRectangle();
        x2.start(25, 30);
        data22.add(x2);
        nameCol2.setMinWidth(100);
        typeCol2.setMinWidth(100);
        nameCol2.setCellValueFactory(new PropertyValueFactory<>("startX"));
        typeCol2.setCellValueFactory(new PropertyValueFactory<>("startY"));

        table2.setItems(data22);
        table2.getColumns().addAll(nameCol2, typeCol2, accessCol2);
        row10Box.getChildren().add(table2);
	
	// NOW ORGANIZE THE EDIT TOOLBAR
	//editToolbar.getChildren().add(row1Box);
	//editToolbar.getChildren().add(row2Box);
	//editToolbar.getChildren().add(row3Box);
	//editToolbar.getChildren().add(row4Box);
	//editToolbar.getChildren().add(row5Box);
	//editToolbar.getChildren().add(row6Box);
	//editToolbar.getChildren().add(row7Box);
        editToolbar.getChildren().add(row8Box);
        editToolbar.getChildren().add(row9Box);
        editToolbar.getChildren().add(row10Box);
	
	// WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	canvas = new Pane();
	debugText = new Text();
	canvas.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	DataManager data = (DataManager)app.getDataComponent();
	data.setShapes(canvas.getChildren());

	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setRight(editToolbar);
	((BorderPane)workspace).setCenter(canvas);
    }
    
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    
    private void setupHandlers() {
	// MAKE THE EDIT CONTROLLER
	poseEditController = new PoseEditController(app);
	
	// NOW CONNECT THE BUTTONS TO THEIR HANDLERS
	selectionToolButton.setOnAction(e->{
	    poseEditController.processSelectSelectionTool();
	});
	removeButton.setOnAction(e->{
	    poseEditController.processRemoveSelectedShape();
	});
	rectButton.setOnAction(e->{
	    poseEditController.processSelectRectangleToDraw();
	});
	//ellipseButton.setOnAction(e->{
	//    poseEditController.processSelectEllipseToDraw();
	//});
	
	moveToBackButton.setOnAction(e->{
	    poseEditController.processMoveSelectedShapeToBack();
	});
	moveToFrontButton.setOnAction(e->{
	    poseEditController.processMoveSelectedShapeToFront();
	});

	backgroundColorPicker.setOnAction(e->{
	    poseEditController.processSelectBackgroundColor();
	});
	fillColorPicker.setOnAction(e->{ 
	    poseEditController.processSelectFillColor();
	});
	outlineColorPicker.setOnAction(e->{
	    poseEditController.processSelectOutlineColor();
	});
	outlineThicknessSlider.valueProperty().addListener(e-> {
	    poseEditController.processSelectOutlineThickness();
	});
	snapshotButton.setOnAction(e->{
	    poseEditController.processSnapshot();
	});
	
	// MAKE THE CANVAS CONTROLLER	
	canvasController = new CanvasController(app);
	canvas.setOnMousePressed(e->{
	    canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseReleased(e->{
	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseDragged(e->{
	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseExited(e->{
	    canvasController.processCanvasMouseExited((int)e.getX(), (int)e.getY());
	});
	canvas.setOnMouseMoved(e->{
	    canvasController.processCanvasMouseMoved((int)e.getX(), (int)e.getY());
	});
    }
    
    public Pane getCanvas() {
	return canvas;
    }
    
    public void setImage(ButtonBase button, String fileName) {
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + fileName;
        Image buttonImage = new Image(imagePath);
	
	// SET THE IMAGE IN THE BUTTON
        button.setGraphic(new ImageView(buttonImage));	
    }

    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// COLOR PICKER STYLE
	fillColorPicker.getStyleClass().add(CLASS_BUTTON);
	outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
	backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	backgroundColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	
	row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	fillColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	outlineThicknessLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
	row7Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        
        row8Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row9Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row10Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
	DataManager dataManager = (DataManager)app.getDataComponent();
        
        DraggableRectangle y = (DraggableRectangle)dataManager.getSelectedShape();
        if(y != null)
        {
        className.setText(y.getName().getText());
        packageName.setText(y.getName2().getText());
        }
	if (dataManager.isInState(PoseMakerState.STARTING_RECTANGLE)) {
	    selectionToolButton.setDisable(false);
	    //removeButton.setDisable(true);
	    rectButton.setDisable(true);
//	    ellipseButton.setDisable(false);
	}
	else if (dataManager.isInState(PoseMakerState.STARTING_ELLIPSE)) {
	    selectionToolButton.setDisable(false);
	    //removeButton.setDisable(true);
	    rectButton.setDisable(false);
	 //   ellipseButton.setDisable(true);
	}
	else if (dataManager.isInState(PoseMakerState.SELECTING_SHAPE) 
		|| dataManager.isInState(PoseMakerState.DRAGGING_SHAPE)
		|| dataManager.isInState(PoseMakerState.DRAGGING_NOTHING)) {
	    boolean shapeIsNotSelected = dataManager.getSelectedShape() == null;
	    selectionToolButton.setDisable(true);
	    //removeButton.setDisable(shapeIsNotSelected);
	    rectButton.setDisable(false);
//	    ellipseButton.setDisable(false);
	    moveToFrontButton.setDisable(shapeIsNotSelected);
	    moveToBackButton.setDisable(shapeIsNotSelected);
	}
	
	//removeButton.setDisable(dataManager.getSelectedShape() == null);
	backgroundColorPicker.setValue(dataManager.getBackgroundColor());
    }
    
    public void loadSelectedShapeSettings(Shape shape) {
	if (shape != null) {
	    Color fillColor = (Color)shape.getFill();
	    Color strokeColor = (Color)shape.getStroke();
	    double lineThickness = shape.getStrokeWidth();
	    //fillColorPicker.setValue(fillColor);
	    //outlineColorPicker.setValue(strokeColor);
	    outlineThicknessSlider.setValue(lineThickness);	    
	}
    }
}
