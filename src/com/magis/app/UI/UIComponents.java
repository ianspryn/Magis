package com.magis.app.UI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXScrollPane;
import com.magis.app.Main;
import com.magis.app.home.HomePage;
import com.magis.app.icons.MaterialIcons;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class UIComponents {

    public static class GenericPage {

        private StackPane master;

        private ScrollPane scrollPane;
        private VBox mastervBox;
        private JFXButton backButton;
        private Label pageTitle;
        public GenericPage() {
            /*
            Master
             */
            master = new StackPane();
            master.getStyleClass().add("background");
            scrollPane = new ScrollPane();
            UIComponents.fadeOnAndTranslate(scrollPane, 0.2, 0.2, 0, 0, -10, 0);
            scrollPane.getStyleClass().add("master-scrollpane");
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setFitToWidth(true);



            /*
            Middle
             */
            mastervBox = new VBox();
            mastervBox.setPadding(new Insets(25, 25, 25, 25));
            mastervBox.setMaxWidth(1500);
            //Center the content in the scrollpane
            StackPane contentHolder = new StackPane(mastervBox);
            contentHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                    scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

            scrollPane.setContent(contentHolder);
            JFXScrollPane.smoothScrolling(scrollPane);

            /*
            Back button and Page title
             */

            HBox homeBox = createCenterColorHomeBox();
            homeBox.setOnMouseClicked(e -> HomePage.goHome(scrollPane));

            StackPane top = new StackPane();
            AnchorPane corners = new AnchorPane();
            corners.setPadding(new Insets(0, 0, 50, 0));

            //back button
            backButton = new JFXButton("Back");
            backButton.setDisableVisualFocus(true); //fix button appear to be highlighted (not selected, just highlighted)
            backButton.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            backButton.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            backButton.getStyleClass().addAll("jfx-button-flat", "jfx-button-flat-color");

            //page title
            pageTitle = new Label("Generic Title");
            pageTitle.getStyleClass().add("section-title");
            pageTitle.setTextAlignment(TextAlignment.RIGHT);
            pageTitle.setWrapText(true);
            pageTitle.setMaxWidth(Main.window.getWidth() / 2.0 - 150);

            Main.window.widthProperty().addListener((obs, oldVal, newVal) -> {
                pageTitle.setMaxWidth(newVal.intValue() / 2.0 - 150);
            });

            corners.getChildren().addAll(backButton, pageTitle);
            AnchorPane.setLeftAnchor(backButton, 0.0);
            AnchorPane.setRightAnchor(pageTitle, 0.0);

            top.getChildren().addAll(corners, homeBox);
            StackPane.setAlignment(homeBox, Pos.CENTER);
            mastervBox.getChildren().addAll(top);

            master.getChildren().add(scrollPane);
            StackPane.setAlignment(scrollPane, Pos.CENTER);
        }

        public StackPane getMaster() {
            return master;
        }

        public ScrollPane getScrollPane() {
            return scrollPane;
        }

        public VBox getMastervBox() {
            return mastervBox;
        }

        public JFXButton getBackButton() {
            return backButton;
        }

        public Label getPageTitle() {
            return pageTitle;
        }

    }
    public static HBox CreateTitleBar() {
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(5);
        hBox.getStyleClass().add("title-bar");

        Button minimize = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.MINIMIZE, 12);
        minimize.getStyleClass().add("material-icons-light");
        minimize.setOnAction(e -> Main.window.setIconified(true));
        Button maximize = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.MAXIMIZE, 12);
        maximize.getStyleClass().add("material-icons-light");
        maximize.setOnAction(e -> Main.window.setFullScreen(!Main.window.isFullScreen()));
        Button close = UIComponents.CreateSVGIconButton(com.magis.app.icons.MaterialIcons.CLOSE, 12);
        close.getStyleClass().add("material-icons-light");

        hBox.getChildren().addAll(minimize, maximize, close);

        return hBox;
    }

    public static GridPane createPasswordVerifierHelper() {
        GridPane passwordVerifierGridPane = new GridPane();
        passwordVerifierGridPane.setAlignment(Pos.CENTER);
        passwordVerifierGridPane.setHgap(10);
        passwordVerifierGridPane.setVgap(25);

        JFXCheckBox longEnoughCheck = generatePasswordAssistantCheckBox();
        Label longEnoughLabel = bindWithCheckBox("Greater than 8 characters", longEnoughCheck);
        JFXCheckBox containsUpperCaseCheck = generatePasswordAssistantCheckBox();
        Label containsUpperCaseLabel = bindWithCheckBox("Contains at least one uppercase letter", containsUpperCaseCheck);
        JFXCheckBox containsLowerCaseCheck = generatePasswordAssistantCheckBox();
        Label containsLowerCaseLabel = bindWithCheckBox("Contains at least one lowercase letter", containsLowerCaseCheck);
        JFXCheckBox containsNumberCheck = generatePasswordAssistantCheckBox();
        Label containsNumberLabel = bindWithCheckBox("Contains at least one number", containsNumberCheck);

        Label notSatisfied = new Label("Please meet these standards!");
        notSatisfied.setStyle("-fx-text-fill: #FF1744"); //Red A400
        notSatisfied.setVisible(false);

        passwordVerifierGridPane.add(longEnoughCheck,0,1);
        passwordVerifierGridPane.add(longEnoughLabel,1,1);
        passwordVerifierGridPane.add(containsUpperCaseCheck,0,2);
        passwordVerifierGridPane.add(containsUpperCaseLabel,1,2);
        passwordVerifierGridPane.add(containsLowerCaseCheck,0,3);
        passwordVerifierGridPane.add(containsLowerCaseLabel,1,3);
        passwordVerifierGridPane.add(containsNumberCheck,0,4);
        passwordVerifierGridPane.add(containsNumberLabel,1,4);
        passwordVerifierGridPane.add(notSatisfied,1,5);

        return passwordVerifierGridPane;
    }

    /**
     * Create a JFXCheckBox that is used in the password verifier assistant
     * @return a disabled JFXCheckBox that is colored red for unchecked, and green for checked
     */
    private static JFXCheckBox generatePasswordAssistantCheckBox() {
        JFXCheckBox checkBox = new JFXCheckBox();
        checkBox.getStyleClass().add("password-verifier-checkbox");
        checkBox.setUnCheckedColor(Color.valueOf("#FF1744")); //Red A400
        checkBox.setCheckedColor(Color.valueOf("#00C853")); //Green A700
        checkBox.setDisable(true);
        return checkBox;
    }

    /**
     * Create a Label that is used in the password verifier assistant
     * @param requirement the text that presents one of the requirements for the password
     * @param checkBox the checkbox associated with the label. The state of the checkbox controls the color of the label
     * @return a label
     */
    private static Label bindWithCheckBox(String requirement, JFXCheckBox checkBox) {
        Label label = new Label(requirement);
        label.setStyle("-fx-text-fill: #FF1744"); //Red A400 (default)
        label.setAlignment(Pos.CENTER_RIGHT);
        checkBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) label.setStyle("-fx-text-fill: #00C853"); //Green A700
            else label.setStyle("-fx-text-fill: #FF1744"); //Red A400
        });
        return label;
    }

    /**
     * create a JFXPasswordField that is used in the sign-in and sign-up gridpane
     * @param promptText the text inside the text field to indicate to the user the purpose of the text field
     * @return a JFXPasswordField
     */
    public static JFXPasswordField createJFXPasswordField(String promptText) {
        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setPromptText(promptText);
        passwordField.getStyleClass().addAll("jfx-sign-in-field", "sign-in-field");
        return passwordField;
    }

    public static StackPane createNavigationButton(String chevron) {
        StackPane button = new StackPane();
        Circle leftCircle = new Circle();
        leftCircle.getStyleClass().addAll("navigation-button","drop-shadow");
        leftCircle.setRadius((35));
        Text leftChevron = new Text(chevron);
        leftChevron.getStyleClass().add("navigation-text");
        button.getChildren().addAll(leftCircle, leftChevron);
        button.setAlignment(Pos.CENTER);
        button.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        button.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        StackPane.setMargin(leftChevron, new Insets(0,0,8,0)); //center "<"

        return button;
    }

    public static Button CreateSVGIconButton(String svg, float scale) {
        SVGPath path = new SVGPath();
        path.setContent(svg);
        path.getStyleClass().add("button-icon");

        // scale to size 20x20 (max)
        Bounds bounds = path.getBoundsInLocal();
        double scaleFactor = scale / Math.max(bounds.getWidth(), bounds.getHeight());
        path.setScaleX(scaleFactor);
        path.setScaleY(scaleFactor);

        Button button = new Button();
        button.setPickOnBounds(true);
        button.setGraphic(path);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("icon-button");

        return button;
    }

    public static Button getHomeButton() {
        Button button = CreateSVGIconButton(MaterialIcons.HOME, 50);
        button.getStyleClass().add("material-icons-light-solid");
        button.setDisable(true);
        button.setStyle("-fx-opacity: 1.0");
        return button;
    }

    public static Rectangle createRectangle(double width, double height) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.getStyleClass().add("rectangle-color");
        return rectangle;
    }

    /**
     * build the home box that goes in the top left corner
     * @return the HBox object that holds the home box
     */
    public static HBox createCornerWhiteHomeBox() {
        HBox home = new HBox();
        home.setSpacing(20);
        home.setMinWidth(300);
        home.setPadding(new Insets(15,0,0,20));

        //Home icon and text
        VBox homeVBox = new VBox();

        Button homeButton = UIComponents.getHomeButton();

        Label text = new Label("Home");
        text.getStyleClass().add("home-text");

        homeVBox.getChildren().addAll(homeButton, text);


        //Magis logo
        ImageView magisLogo = new ImageView("com/magis/app/resources/magis-white-small.png");
        magisLogo.setPreserveRatio(true);
        magisLogo.setFitWidth(175);

        //listeners
        home.setOnMouseEntered(e -> Main.scene.setCursor(javafx.scene.Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));


        home.getChildren().addAll(homeVBox, magisLogo);
        return home;
    }

    /**
     * build the home box that goes in the top left corner
     * @return the HBox object that holds the home box
     */
    public static HBox createCenterColorHomeBox() {
        HBox home = new HBox();
        home.setPadding(new Insets(0,0,0,45));
        home.setMaxWidth(HBox.USE_PREF_SIZE);
        home.setSpacing(20);
        home.setMinWidth(300);

        //Home icon and text
        VBox homeVBox = new VBox();

        HBox settingsIconContainer = new HBox();
        settingsIconContainer.setPadding(new Insets(7,0,0,0));
        SVGPath homeIcon = new SVGPath();
        homeIcon.getStyleClass().add("icon-no-color");
        homeIcon.setContent(MaterialIcons.HOME);
        Bounds settingsBounds = homeIcon.getBoundsInLocal();
        double settingsScaleFactor = 50 / Math.max(settingsBounds.getWidth(), settingsBounds.getHeight());
        homeIcon.setScaleX(settingsScaleFactor);
        homeIcon.setScaleY(settingsScaleFactor);

        settingsIconContainer.getChildren().add(homeIcon);

//        Label text = new Label("Home");
//        text.getStyleClass().add("home-text");

        homeVBox.getChildren().add(settingsIconContainer);


        //Magis logo
        ImageView magisLogo = new ImageView("com/magis/app/resources/magis-color-small.png");
        magisLogo.setPreserveRatio(true);
        magisLogo.setFitWidth(175);

        //listeners
        home.setOnMouseEntered(e -> Main.scene.setCursor(javafx.scene.Cursor.HAND));
        home.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));

        home.getChildren().addAll(homeVBox, magisLogo);
        return home;
    }

    /**
     * Pop up box confirming the user's action
     * @return the user's choice, yes/true = continue, no/false = stay
     */
    public static boolean confirmMessage(String title, String content) {
        AtomicBoolean result = new AtomicBoolean(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(cancelButton, okButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getText().equals("Yes")) result.set(true);
        });
        return result.get();
    }

    /**
     * Fade in the side panel labels
     * @param node the current label to fade in
     * @param index the current index of the label. Used to add incremental delay of fade in
     * @param delay how long before the animation begins
     * @param duration how long to fadeOnAndTranslate
     * @param fromX where the node should start in its animation for the x-axis
     * @param toX where the node should end in its animation for the x-axis
     * @param fromY where the node should start in its animation for the y-axis
     * @param toY where the node should end in its animation for the y-axis
     */
    public static void fadeOnAndTranslate(Node node, int index, double delay, double duration, float fromX, float toX, float fromY, float toY) {
        if (!Main.useAnimations) return;
        //fade in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);
        fadeTransition.setInterpolator(Interpolator.EASE_OUT);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        //"hack" to hide all the chapters
        fadeTransition.play();
        fadeTransition.pause();

        //move down
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setInterpolator(Interpolator.EASE_OUT);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        //wait a certain delay, then fadeOnAndTranslate in
        SequentialTransition sequentialTransition = new SequentialTransition(new PauseTransition(Duration.seconds(delay + ((float)index / 30))), parallelTransition);
        sequentialTransition.play();
    }

    /**
     * Animate a node
     * @param node the node to fadeOnAndTranslate
     * @param delay how long before the animation begins
     * @param duration how long to fadeOnAndTranslate
     * @param fromX where the node should start in its animation for the x-axis
     * @param toX where the node should end in its animation for the x-axis
     * @param fromY where the node should start in its animation for the y-axis
     * @param toY where the node should end in its animation for the y-axis
     */
    public static void fadeOnAndTranslate(Node node, double delay, double duration, float fromX, float toX, float fromY, float toY) {
        if (!Main.useAnimations) return;
        //fade in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);
        fadeTransition.setInterpolator(Interpolator.EASE_OUT);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        //"hack" to initially hide the component
        fadeTransition.play();
        fadeTransition.pause();

        //move down
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setInterpolator(Interpolator.EASE_OUT);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);

        //move down and fade in at the same time
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransition, translateTransition);

        //wait a certain delay, then fadeOnAndTranslate in
        SequentialTransition sequentialTransition = new SequentialTransition(new PauseTransition(Duration.seconds(delay)), parallelTransition);
        sequentialTransition.play();
    }

    public static void translate(Node node, double duration, float fromX, float toX, float fromY, float toY) {
        if (!Main.useAnimations) return;
        if (duration == 0) {
            System.err.println("Duration is set to 0 seconds. In order to animate, it must be greater than 0");
        }
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);

        translateTransition.play();
    }

    public static void scale(Node node, double duration, double scaleToX, double scaleToY) {
        if (!Main.useAnimations) return;
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), node);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(node.getScaleX());
        scaleTransition.setToX(scaleToX);
        scaleTransition.setFromY(node.getScaleY());
        scaleTransition.setToY(scaleToY);

        scaleTransition.play();
    }

    public static ParallelTransition transitionPage(Node node) {
        ParallelTransition parallelTransition = new ParallelTransition(getFadeTransition(node), getTranslateTransition(node));
        parallelTransition.play();
        return parallelTransition;
    }

    public static FadeTransition getFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        return fadeTransition;
    }

    public static TranslateTransition getTranslateTransition(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10); //move up

        return translateTransition;
    }
}
