/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author harsha
 */
public class ProcessingTest extends PApplet {

    CityData currentAmbientState;
    ArrayList<CityData> currentDataSet;
    CitySense citySense;
    int CURRENT_HOUR = 10;
    int timeCompression = 6; //seconds
    Timer mainClockLoop;
    ArrayList<Scene> filmReel;

    int numFrames = 12;
    int numFrames2 = 10;
    int currentFrame = 0;
    PImage[] images = new PImage[numFrames];
    PImage[] images2 = new PImage[numFrames2];

    @Override
    public void setup() {
        citySense = new CitySense();
        currentAmbientState = new CityData();
        currentDataSet = new ArrayList<>();
        filmReel = new ArrayList<>();
        updateDataSet();
        adjustDataSet();
        updateAmbientState();

        //Set up the clock timer.
        mainClockLoop = new Timer();
        mainClockLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeUpdate();
                updateAmbientState();
            }
        }, 0, timeCompression * 1000);

        size(640, 480);
        frameRate(24);
        composeFilmReel();
    }

    @Override
    public void draw() {
        background(150);
        //int offset = 0;
        //image(images[(currentFrame + offset) % numFrames], this.displayWidth, -20);
        //        for (int x = -100; x < width; x += images[0].width) {
        //            image(images[(currentFrame + offset) % numFrames], 0, 0);
        //            image(images2[(currentFrame + offset) % numFrames2], 0, 0);
        //            //offset += 2;
        //            //image(images[(currentFrame + offset) % numFrames], x, height / 2);
        //            //offset += 2;
        //        }

        PImage[] currentFrameToDraw = filmReel.get(0).frames.get(CURRENT_HOUR % 2);
        currentFrame = (currentFrame + 1) % currentFrameToDraw.length;

//        for (int i = 0; i < currentFrameToDraw.length; i++) {
//            System.out.println(currentFrameToDraw.length);
        image(currentFrameToDraw[currentFrame], 0, 0);
        //}

        drawRefresh();
    }

    public void updateDataSet() {
        try {
            currentDataSet = citySense.getDayDataByLocation();
            Collections.sort(currentDataSet);
            System.out.println("Updated Data Set: " + currentDataSet.size());
        } catch (IOException ex) {
            Logger.getLogger(ProcessingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void timeUpdate() {
        CURRENT_HOUR = (CURRENT_HOUR + 1) % 24;
        if (CURRENT_HOUR == 0) {
            updateDataSet();
            adjustDataSet();
        }
        System.out.println("CURRENT HOUR: " + CURRENT_HOUR);
    }

    public void updateAmbientState() {
        currentAmbientState = currentDataSet.get(CURRENT_HOUR);
    }

    public void adjustDataSet() {
        int diffDatapoints = currentDataSet.size() - 24;

        if (diffDatapoints == 0) { //PERFECT COndition
            return;
        }
        if (diffDatapoints > 0) {
            //MORE DATA POINTS
            if (diffDatapoints % 2 == 0) {
                //EVEN NUMBER
                for (int i = 0; i < diffDatapoints / 2; i++) {
                    currentDataSet.remove(i);
                }
                for (int top = currentDataSet.size() - 1, i = 0; i < diffDatapoints / 2; i++) {
                    currentDataSet.remove(top);
                    top = currentDataSet.size() - 1;
                }
            } else {
                //ODD NUMBER
                for (int top = currentDataSet.size() - 1, i = 0; i < diffDatapoints; i++) {
                    currentDataSet.remove(top);
                    top = currentDataSet.size() - 1;
                }
            }
        } else {
            //LESS DATA POINTS
            if (diffDatapoints % 2 == 0) {
                //EVEN NUMBER
                CityData headData = currentDataSet.get(0);
                CityData tailData = currentDataSet.get(currentDataSet.size() - 1);

                for (int i = 0; i < diffDatapoints / 2; i++) {
                    currentDataSet.add(0, headData);
                }
                for (int i = 0; i < diffDatapoints / 2; i++) {
                    currentDataSet.add(currentDataSet.size() - 1, tailData);
                }
            } else {
                //ODD NUMBER
                CityData tailData = currentDataSet.get(currentDataSet.size() - 1);
                for (int i = 0; i < diffDatapoints; i++) {
                    currentDataSet.add(currentDataSet.size() - 1, tailData);
                }

            }
        }
        System.out.println("Adjusted Data Set: " + currentDataSet.size());
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{ProcessingTest.class.getName()});
    }

    private void composeFilmReel() {
        Scene temp = new Scene();
        //images[0] = loadImage("/home/harsha/sketchbook/AnimationTest/gitTest/giphy.gif");
        // If you don't want to load each image separately
        // and you know how many frames you have, you
        // can create the filenames as the program runs.
        // The nf() command does number formatting, which will
        // ensure that the number is (in this case) 4 digits.

        for (int i = 0; i < numFrames; i++) {
            String imageName = "images/target-" + nf(i, 1) + ".png";
            images[i] = loadImage(imageName);
        }
        temp.frames.put(0, images);
        for (int i = 0; i < numFrames2; i++) {
            String imageName = "images2/target-" + nf(i, 1) + ".png";
            images2[i] = loadImage(imageName);
        }
        temp.frames.put(1, images2);
        filmReel.add(temp);
    }

    private void drawRefresh() {
    }
}
