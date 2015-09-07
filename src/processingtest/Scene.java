/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.util.HashMap;
import processing.core.PImage;

/**
 *
 * @author harsha
 */
public class Scene {

    HashMap<Integer, PImage[]> frames;

    public Scene() {
        frames = new HashMap<>();
    }

    public HashMap<Integer, PImage[]> getFrames() {
        return frames;
    }

    public void setFrames(HashMap<Integer, PImage[]> frames) {
        this.frames = frames;
    }
}
