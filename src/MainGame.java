import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainGame extends Application
{
    AnimationTimer timer;
    Pane root = new Pane();
    List drop = new ArrayList();
    double mouseX;
    Rectangle cont;
    double speed;
    double falling;
    Label lblMissed;
    int missed;

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        lblMissed = new Label("Missed: 0");
        lblMissed.setLayoutX(10);
        lblMissed.setLayoutY(10);
        missed = 0;

        speed = 1;
        falling = 500;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(falling), event ->
        {
            speed += falling / 3000;
            drop.add(circle());
            root.getChildren().add(((Node)drop.get(drop.size() -1)));
        }));

        timeline.setCycleCount(1000);
        timeline.play();


        timer = new AnimationTimer()
        {
            @Override
            public void handle(long arg0)
            {
                gameUpdate();
            }
        };

        timer.start();

        cont = rectangle();

        root.getChildren().addAll(cont, lblMissed);

        Scene scene = new Scene(root, 400, 600);

        scene.setOnMouseMoved(e ->
        {
            mouseX = e.getX();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Circle circle()
    {
        Circle circle = new Circle();
        circle.setLayoutX(rand(0, 400));
        circle.setLayoutY(1);
        circle.setRadius(6);
        circle.setFill(Color.BLUE);
        return circle;
    }

    public Rectangle rectangle()
    {
        Rectangle rectangle = new Rectangle();
        rectangle.setLayoutX(200);
        rectangle.setLayoutY(500);
        rectangle.setHeight(50);
        rectangle.setWidth(70);
        rectangle.setFill(Color.GREEN);
        return rectangle;
    }

    public int rand(int min, int max)
    {
        return (int)(Math.random() * max + min);
    }

    public void gameUpdate()
    {
        cont.setLayoutX(mouseX);

        for(int i = 0; i < drop.size(); i++)
        {
            ((Circle) drop.get(i)).setLayoutY(((Circle) drop.get(i)).getLayoutY() + speed + ((Circle) drop.get(i)).getLayoutY() / 150);
            if((((Circle) drop.get(i)).getLayoutX() > cont.getLayoutX() && ((Circle) drop.get(i)).getLayoutX() < cont.getLayoutX() + 70) &&
            ((Circle) drop.get(i)).getLayoutY() > 550 )
            {
                root.getChildren().remove(((Circle) drop.get(i)));
                drop.remove(i);
            }

            else if(((Circle) drop.get(i)).getLayoutY() >= 590)
            {
                root.getChildren().remove(((Circle) drop.get(i)));
                drop.remove(i);
                missed += 1;
                lblMissed.setText("Missed: " + String.valueOf(missed));
            }
        }
    }
}
