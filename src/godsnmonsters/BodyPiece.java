/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package godsnmonsters;

import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author Dan Olner
 */
public class BodyPiece {

    GodsNMonsters p;
    PImage img;

    Joint originJoint;
    Joint referenceJoint;

    PVector imageOrigin;
    PVector imageReference;

//    float imageAngle;
    PVector imageRelativeVector;

    PVector twoJointVector = new PVector();

    boolean flipImage = false;
    boolean noRotate = false;

    public BodyPiece(GodsNMonsters p, String filename, Joint originJoint, Joint referenceJoint, PVector imageOrigin, PVector imageReference, boolean flipImage, boolean noRotate) {

        this.p = p;

        this.originJoint = originJoint;
        this.referenceJoint = referenceJoint;
        this.imageOrigin = imageOrigin;
        this.imageReference = imageReference;
        this.flipImage = flipImage;
        this.noRotate = noRotate;

        img = p.loadImage(filename);
        
        //if using a mirror image, flip the x axis. 
        //(Doesn't matter which axis gets flipped as long as scale in draw flips the same one.)
        if(flipImage){
            imageOrigin.x = -imageOrigin.x;
            imageReference.x = -imageReference.x;
        }
        
        imageRelativeVector = imageReference.sub(imageOrigin); 
        
        System.out.println("image relative vector: " + imageRelativeVector.x + "," + imageRelativeVector.y);
        
        
    }

    public boolean isFlipImage() {
        return flipImage;
    }

    public void setFlipImage(boolean flipImage) {
        this.flipImage = flipImage;
    }

    public boolean isNoRotate() {
        return noRotate;
    }

    public void setNoRotate(boolean noRotate) {
        this.noRotate = noRotate;
    }

    public void draw() {

        p.pushMatrix();

        //translate to origin joint position
        p.translate(originJoint.getX(), originJoint.getY(), originJoint.getZ());

        //translate so that image origin is at the correct point
        //translate out then back in for rotation
//        p.translate(imageOrigin.x, imageOrigin.y);
//        p.translate(-imageOrigin.x, -imageOrigin.y);

        //relative vector of joints
        twoJointVector.set(referenceJoint.getX() - originJoint.getX(), referenceJoint.getY() - originJoint.getY());
        
//        System.out.print("joints rel vector: " + twoJointVector.x + "," + twoJointVector.y + " angle: " + p.degrees(twoJointVector.heading()));
//        System.out.println(", angle between: " + p.degrees(PVector.angleBetween(twoJointVector, imageRelativeVector)));
        
        //Scale based on the difference of the relative vectors' magnitude
        p.scale(twoJointVector.mag()/imageRelativeVector.mag());
        
        //angleBetween doesn't account for whole circle
        //p.rotate(PVector.angleBetween(twoJointVector, imageRelativeVector));
        p.rotate(twoJointVector.heading() - imageRelativeVector.heading());
        
        if(flipImage){
            p.scale(-1,1);
            p.image(img, imageOrigin.x, -imageOrigin.y);
        } else {
            p.image(img, -imageOrigin.x, -imageOrigin.y);
        }
        
        
        p.popMatrix();

    }

}
